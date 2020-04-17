package org.springframework.boot.demoproject.peristence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.demoproject.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass=Order.class, idClass=Long.class)
public interface OrderRepoisitory {

	void save(Order entity);
	
	List<Order> findAll();
	
	@Query("SELECT o FROM Order o WHERE o.createdOn BETWEEN :startDate AND :endDate")
	List<Order> findBetweenDate(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);
}
