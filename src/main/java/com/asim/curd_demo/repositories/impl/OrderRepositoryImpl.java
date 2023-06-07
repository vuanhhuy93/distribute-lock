package com.asim.curd_demo.repositories.impl;

import com.asim.curd_demo.model.order.CreateOrderResponse;
import com.asim.curd_demo.model.order.GetOrderResponse;
import com.asim.curd_demo.model.order.OrderDTO;
import com.asim.curd_demo.model.order.UpdateOrderResponse;
import com.asim.curd_demo.model.response.OrderResponse;
import com.asim.curd_demo.repositories.OrderRepository;
import com.asim.curd_demo.utils.JsonUtils;
import com.asim.curd_demo.utils.QueryFile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepositoryImpl extends BaseRepository implements OrderRepository {
    @Override
    public CreateOrderResponse createOrder(OrderDTO entity) {

        Map<String, String> variables = new HashMap<>();
        variables.put("$product_id" , String.valueOf(entity.getProductId()));
        variables.put("$user_id" , String.valueOf(entity.getUserId()));
        variables.put("$status" , String.valueOf(entity.getStatus()));
        variables.put("$amount" , String.valueOf(entity.getAmount()));
        variables.put("$total" , String.valueOf(entity.getTotal()));
        variables.put("$transaction_id", String.valueOf(entity.getTransactionId()));

        String queryResult = this.execute(QueryFile.ORDER_CREATE, variables );
        return JsonUtils.toJson(queryResult, CreateOrderResponse.class);
    }

    @Override
    public UpdateOrderResponse updateOrder(OrderDTO entity) {
        Map<String, String> variables = new HashMap<>();

        variables.put("$id" , String.valueOf(entity.getId()));
        variables.put("$status" , String.valueOf(entity.getStatus()));
        String queryResult = this.execute(QueryFile.ORDER_UPDATE_STATUS, variables );
        return JsonUtils.toJson(queryResult, UpdateOrderResponse.class);
    }

    @Override
    public GetOrderResponse getOrderById(long orderId) {
        Map<String, String> variables = new HashMap<>();

        variables.put("$id" , String.valueOf(orderId));
        String queryResult = this.execute(QueryFile.ORDER_FINd_BY_ID, variables );
        return JsonUtils.toJson(queryResult, GetOrderResponse.class);
    }
}
