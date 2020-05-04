package org.springframework.boot.demoproject.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.demoproject.application.model.order.OrderMapper;
import org.springframework.boot.demoproject.application.model.order.OrderResponse;
import org.springframework.boot.demoproject.application.model.order.PlaceOrderRequest;
import org.springframework.boot.demoproject.domain.order.Order;
import org.springframework.boot.demoproject.domain.order.OrderService;
import org.springframework.boot.demoproject.domain.order.UnknownOrderedProductException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    private List<OrderResponse> getOrders(@RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dateFrom,
                                          @RequestParam(value = "dateTo", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dateTo) {
        if (Objects.isNull(dateFrom) ^ Objects.isNull(dateTo)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date range 'dateFrom' and 'dateTo' has to be specified!");
        }
        List<Order> orders = orderService.getOrders(dateFrom, dateTo);
        return orderMapper.mapToOrderResponseList(orders);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void createOrder(@RequestBody @Valid  PlaceOrderRequest placeOrderRequest) {
        try {
            orderService.placeOrder(orderMapper.mapToOrder(placeOrderRequest));
        } catch (UnknownOrderedProductException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
