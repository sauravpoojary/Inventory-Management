package com.javaspring.api.repository;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.javaspring.api.entity.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product,String>{

	Mono<Product> findByProductSku(String productSku);
	
	@Query("{availableQty : {$gt: 0}}")
//	@Query("SELECT * FROM PRODUCT3 WHERE availableQty>0")
	Flux<Product> findProductByAvailability();
//	Mono<Product> findByProductSku(String product_sku);


}
