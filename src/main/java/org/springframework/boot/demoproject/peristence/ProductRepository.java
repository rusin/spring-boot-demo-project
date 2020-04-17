package org.springframework.boot.demoproject.peristence;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.demoproject.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

	  @Override
	  @Query("SELECT p FROM Product p WHERE p.isDeleted = false")
	  List<Product> findAll();
	  
	  @Override
	  @Query("SELECT p FROM Product p WHERE p.sku IN ?1 AND p.isDeleted = false")
	  List<Product> findAllById(Iterable<String> ids);
	  
	  @Override
	  @Query("SELECT p FROM Product p WHERE p.sku = ?1 and p.isDeleted = false")
	  Optional<Product> findById(String id);
	  
	  @Override
	  default boolean existsById(String id) {
	      return findById(id).isPresent();
	  }
	  
	  @Override
	  @Query("UPDATE Product p SET p.isDeleted = true WHERE p.sku = ?1")
	  @Modifying
	  void deleteById(String id);
	  
	  @Override
	  default void delete(Product product) {
		  deleteById(product.getSku());
	  }
	  
	  default Map<String, BigDecimal> getPriceList(Iterable<String> ids) {
	        return findAllById(ids).stream().collect(Collectors.toMap(Product::getSku, Product::getPrice));
	    }
}
