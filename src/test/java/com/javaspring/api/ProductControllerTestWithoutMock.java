package com.javaspring.api;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.javaspring.api.dto.ProductCreationDto;

import reactor.core.publisher.Mono;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
public class ProductControllerTestWithoutMock {

	    @Autowired
	    private WebTestClient client;

	    private static String id;
	    @Test
	    @Order(1)
	    void createValidation() {

	    	ProductCreationDto productCreationDto = new ProductCreationDto();

	        client.post().uri("/inventory/saveProduct")
	        .contentType(MediaType.APPLICATION_JSON)
	                .body(Mono.just(productCreationDto), ProductCreationDto.class).exchange()
	                .expectStatus().isBadRequest();
	    }
}
