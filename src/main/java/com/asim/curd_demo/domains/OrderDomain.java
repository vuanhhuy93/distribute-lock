package com.asim.curd_demo.domains;

import com.asim.curd_demo.model.order.CreateOrderResponse;
import com.asim.curd_demo.model.order.GetOrderResponse;
import com.asim.curd_demo.model.order.OrderDTO;
import com.asim.curd_demo.model.order.UpdateOrderResponse;
import com.asim.curd_demo.repositories.OrderRepository;
import com.asim.curd_demo.utils.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class OrderDomain {
    @Autowired
    OrderRepository orderRepository;

    public long createOrder(OrderDTO orderDTO ) throws ApplicationException {

        CreateOrderResponse response =  orderRepository.createOrder(orderDTO);

        if (response == null || response.getData() == null || response.getData().getResult() == null
                || CollectionUtils.isEmpty(response.getData().getResult().getItems())){
            throw new ApplicationException(-20, "create order fail");
        }

        return response.getData().getResult().getItems().get(0).getId();
    }

    public OrderDTO getById(long id){
        GetOrderResponse response = orderRepository.getOrderById(id);

        if (response == null || response.getData() == null || CollectionUtils.isEmpty(response.getData().getItems()))
            return null;

        return response.getData().getItems().get(0);
    }

    public void updateStatusOrder(long orderId, int status){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderId);
        orderDTO.setStatus(status);
        UpdateOrderResponse response = orderRepository.updateOrder(orderDTO);

        if (response == null || response.getData() == null || response.getData().getData() == null
        || CollectionUtils.isEmpty(response.getData().getData().getItems())){
            throw new ApplicationException(-100, "update order status fail");
        }
    }
}
