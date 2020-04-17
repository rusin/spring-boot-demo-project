package org.springframework.boot.demoproject.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.demoproject.exception.UnknownProductException;
import org.springframework.boot.demoproject.model.Order;
import org.springframework.boot.demoproject.peristence.OrderRepoisitory;
import org.springframework.boot.demoproject.peristence.ProductRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Api(value="Order Product API")
public class OrderApi {
	
	@Autowired
	private OrderRepoisitory orderRepoisitory;
	@Autowired
	private ProductRepository productRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void placeOrder(@RequestBody Order order) {
		Map<String, BigDecimal> productPriceList = productRepository.getPriceList(order.getProducts());
		order.setTotalPrice(calculateTotalPrice(order.getProducts(), productPriceList));
		orderRepoisitory.save(order);
	}
	
	@GetMapping
	public List<Order> listOrders(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dateFrom,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dateTo) {
		if (dateFrom == null || dateTo == null) {
			return orderRepoisitory.findAll();
		}
		return orderRepoisitory.findBetweenDate(dateFrom, dateTo);
	}
	
	private BigDecimal calculateTotalPrice(List<String> products, Map<String, BigDecimal> productPriceList) {
		return products.stream().map(p -> getPriceOrThrowError(p, productPriceList)).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	private BigDecimal getPriceOrThrowError(String productSku, Map<String, BigDecimal> productPriceList) {
		if (productPriceList.containsKey(productSku)) {
			return productPriceList.get(productSku);
		}
		throw new UnknownProductException(String.format("Unknown product SKU %s prodived", productSku));
	}
}
