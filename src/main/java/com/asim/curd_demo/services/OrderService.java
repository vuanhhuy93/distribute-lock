package com.asim.curd_demo.services;

import com.asim.curd_demo.domains.OrderDomain;
import com.asim.curd_demo.domains.ProductDomain;
import com.asim.curd_demo.domains.UserBalanceDomain;
import com.asim.curd_demo.domains.WarehouseDomain;
import com.asim.curd_demo.model.order.OrderDTO;
import com.asim.curd_demo.model.product.ProductDTO;
import com.asim.curd_demo.model.request.OrderRequest;
import com.asim.curd_demo.model.response.BaseResponse;
import com.asim.curd_demo.model.response.OrderResponse;
import com.asim.curd_demo.model.userbalance.UserBalanceTransactionDTO;
import com.asim.curd_demo.utils.ApplicationException;
import com.asim.curd_demo.utils.CONSTANT;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class OrderService {

    @Autowired
    ProductDomain productDomain;

    @Autowired
    WarehouseDomain warehouseDomain;

    @Autowired
    UserBalanceDomain userBalanceDomain;

    @Autowired
    OrderDomain orderDomain;

    public BaseResponse<OrderResponse> confirmOrCancelOrder(long id, int action){
        BaseResponse<OrderResponse> response = new BaseResponse<>();
        OrderResponse responseData = new OrderResponse();

        OrderDTO orderDTO = orderDomain.getById(id);


        if (orderDTO == null)
            throw new ApplicationException(-100, "not found order");

        if (orderDTO.getStatus() != CONSTANT.ORDER_STATUS.CREATE){
            throw new ApplicationException(-101, "order status is invalid");
        }
        if (action == 1){
            // confirm

            // update order status = DONE

            orderDomain.updateStatusOrder(orderDTO.getId() , CONSTANT.ORDER_STATUS.SUCCESS);

            // update warehouse
            warehouseDomain.updateTotalAndReverseNumber(orderDTO.getProductId(), orderDTO.getTotal());
            // update user_balance
            // update user transaction

            userBalanceDomain.handConfirmTransaction(orderDTO.getTransactionId());

        } else {

            // reject order


            orderDomain.updateStatusOrder(orderDTO.getId() , CONSTANT.ORDER_STATUS.CANCEL);

            // update warehouse
            warehouseDomain.updateActiveNumberAnsReverseNumber(orderDTO.getProductId(), orderDTO.getTotal(),  orderDTO.getTotal() * -1);
            // update user_balance
            // update user transaction

            userBalanceDomain.rollbackTransaction(orderDTO.getTransactionId());
        }

        response.setData(responseData);
        return response;

    }
    public BaseResponse<OrderResponse> createOrder(OrderRequest request) {

        BaseResponse<OrderResponse> response = new BaseResponse<>();
        OrderResponse responseData = new OrderResponse();
        // STEP 1: VALIDATE PRODUCT
        ProductDTO productDTO = productDomain.validateProductInfo(request.getProductId());

        double amount = request.getTotal() * productDTO.getPrice();


            // step 2: Tru hang
           try {
            warehouseDomain.updateActiveNumberAnsReverseNumber(request.getProductId(), request.getTotal() * -1 , request.getTotal());
        } catch (ApplicationException ex) {
            log.error("create order, step 2, update warehouse exception ", ex);

               response.setCode(ex.getCode());
               response.setMessage(ex.getMessage());
               return response;
        } catch (Exception e) {
            log.error("create order, step 2,  exception ", e);
               response.setCode(-1);
               response.setMessage("warehouse invalid");
               return response;
        }
        UserBalanceTransactionDTO userBalanceTransactionDTO = null;


        try {
            // STEP 3: TRU TIEN

            userBalanceTransactionDTO = userBalanceDomain.createTransaction(request.getUserId(), amount);
        } catch (ApplicationException ex) {
            log.error("create order, step 3, update user balance exception ", ex);
            // rollback lai hang
            warehouseDomain.updateActiveNumberAnsReverseNumber(request.getProductId(), request.getTotal(), request.getTotal() * -1);
            response.setCode(-1);
            response.setMessage("warehouse invalid");
            return response;
        } catch (Exception e) {
            // rollback lai hang

            log.error("ee" , e);
            warehouseDomain.updateActiveNumberAnsReverseNumber(request.getProductId(), request.getTotal(),request.getTotal() * -1);

            response.setCode(-1);
            response.setMessage("warehouse invalid");

            return response;
        }
        // STEP 4: TAO ORDER`

        try {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setAmount(amount);
            orderDTO.setTotal(request.getTotal());
            orderDTO.setUserId(request.getUserId());
            orderDTO.setStatus(CONSTANT.ORDER_STATUS.CREATE);
            orderDTO.setProductId(request.getProductId());
            orderDTO.setTransactionId(userBalanceTransactionDTO.getId());


            long orderId = orderDomain.createOrder(orderDTO);

            responseData.setOrderStatus(CONSTANT.ORDER_STATUS.CREATE);
            responseData.setOrderId(orderId);
            responseData.setMessage("CREATE ORDER SUCCESS");

            response.setData(responseData);
        }catch (ApplicationException ex) {
            warehouseDomain.updateActiveNumberAnsReverseNumber(request.getProductId(), request.getTotal(),request.getTotal() * -1);
            // rollback tien
            userBalanceDomain.rollbackTransaction(userBalanceTransactionDTO);

            response.setCode(-10);
        } catch (Exception e){
            warehouseDomain.updateActiveNumberAnsReverseNumber(request.getProductId(), request.getTotal(),request.getTotal() * -1);
            userBalanceDomain.rollbackTransaction(userBalanceTransactionDTO);
            response.setCode(-5);

        }
        return response;
    }
}
