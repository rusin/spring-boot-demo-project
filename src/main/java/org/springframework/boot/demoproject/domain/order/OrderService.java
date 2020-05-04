package org.springframework.boot.demoproject.domain.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.demoproject.domain.product.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> getOrders(LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (dateFrom == null || dateTo == null) {
            return orderRepository.findAll();
        }
        return orderRepository.findByCreatedOnBetween(dateFrom, dateTo);
    }

    public Order placeOrder(Order order) {
        Map<String, BigDecimal> productPriceList = productRepository.getPriceList(order.getProducts());
        order.setTotalPrice(calculateTotalPrice(order.getProducts(), productPriceList));
        return orderRepository.save(order);
    }

    private BigDecimal calculateTotalPrice(List<String> products, Map<String, BigDecimal> productPriceList) {
        return products.stream().map(p -> getPriceOrThrowError(p, productPriceList)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getPriceOrThrowError(String productSku, Map<String, BigDecimal> productPriceList) {
        if (productPriceList.containsKey(productSku)) {
            return productPriceList.get(productSku);
        }
        throw new UnknownOrderedProductException(String.format("Unknown product SKU %s prodived", productSku));
    }
}
