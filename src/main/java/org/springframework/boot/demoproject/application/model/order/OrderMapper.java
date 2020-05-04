package org.springframework.boot.demoproject.application.model.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.demoproject.domain.order.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order mapToOrder(PlaceOrderRequest placeOrderRequest) {
        Order order = new Order();
        order.setBuyerEmail(placeOrderRequest.getBuyerEmail());
        order.setProducts(placeOrderRequest.getProducts());
        return order;
    }

    public List<OrderResponse> mapToOrderResponseList(List<Order> orders) {
        return orders.stream()
                .map(order -> new OrderResponse(order.getId(), order.getBuyerEmail(), order.getProducts(),
                        order.getTotalPrice(), order.getCreatedOn())).collect(Collectors.toList());
    }
}
