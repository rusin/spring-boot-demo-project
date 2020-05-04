package org.springframework.boot.demoproject.domain.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass=Order.class, idClass=Long.class)
public interface OrderRepository {

    Order save(Order entity);

    List<Order> findAll();

    List<Order> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate);
}
