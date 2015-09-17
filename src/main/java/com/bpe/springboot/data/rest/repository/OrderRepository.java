package com.bpe.springboot.data.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bpe.springboot.data.rest.entity.Order;

@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

	List<Order> findByStatus(@Param("status") String status);
	
	@Query("SELECT o FROM Order o where o.dateSent IS NULL")
	List<Order> findByDateSentIsNull();
}
