package com.asim.curd_demo.repositories;

import com.asim.curd_demo.model.order.CreateOrderResponse;
import com.asim.curd_demo.model.order.GetOrderResponse;
import com.asim.curd_demo.model.order.OrderDTO;
import com.asim.curd_demo.model.order.UpdateOrderResponse;
import com.asim.curd_demo.model.response.OrderResponse;

public interface OrderRepository {
    CreateOrderResponse createOrder(OrderDTO entity);

    UpdateOrderResponse updateOrder(OrderDTO entity);

    GetOrderResponse getOrderById(long orderId);
}
