package com.javaspring.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;

import com.javaspring.api.dto.ProductCreationDto;
import com.javaspring.api.dto.ProductUpdationDemandDto;
import com.javaspring.api.dto.ProductUpdationInstockDto;
import com.javaspring.api.dto.ProductUpdationReserveDto;
import com.javaspring.api.entity.Product;
import com.javaspring.api.repository.ProductRepository;
import com.javaspring.api.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest
public class ProductControllerTest {

	@Autowired
	WebTestClient webTestClient;
	
	
	@MockBean
	ProductService productService;
	
	@Test
	void testGetProducts() {
		Flux<Product> products = Flux.just(new Product("1","a123", 10,0,0,0),new Product("2","a124", 20,0,0,0));
		Mockito.when(productService.getProducts()).thenReturn(products);
		
		
		
		webTestClient.get().uri("/inventory/allProducts")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus()
		.isOk()
		.expectBody()
		.jsonPath("$").isArray()
		.jsonPath("$[0].id").isEqualTo("1")
		.jsonPath("$[0].productSku").isEqualTo("a123");
		
		
	}
	
	@Test
	void testPostProduct() {
		ProductCreationDto productCreationDto= new ProductCreationDto("a",20);
		Mono<Product> monoProduct=Mono.just(new Product("1","a123", 30,0,0,0));
		Product product =new Product("1","a123", 30,0,0,0);
		Mockito.when(productService.saveProduct(productCreationDto)).thenReturn(monoProduct);
		
		webTestClient.post().uri("/inventory/saveProduct")
		.contentType(MediaType.APPLICATION_JSON)
		.body(Mono.just(productCreationDto),ProductCreationDto.class)
		.exchange()
		.expectStatus().isOk()
		.expectBody(Product.class).value(successResponse -> {
			
            assertNotNull(successResponse);
            assertEquals(product,successResponse);
            assertNotNull(successResponse.getInStockQty());
            System.out.println(successResponse.getInStockQty()+" "+successResponse.getProductSku());
           
            

        });
		
		
	}
	
	@Test
	void testPostProductNullCase() {
		ProductCreationDto productCreationDto= new ProductCreationDto();
		Mono<Product> monoProduct=Mono.just(new Product("1","a123", 30,0,0,0));
		Mockito.when(productService.saveProduct(productCreationDto)).thenReturn(monoProduct);
		
		webTestClient.post().uri("/inventory/saveProduct")
		.contentType(MediaType.APPLICATION_JSON)
		.body(Mono.just(productCreationDto),ProductCreationDto.class)
		.exchange()
		.expectStatus().isBadRequest();
		
	}
	
	@Test
	void testUpdateInstock() {
		ProductUpdationInstockDto productUpDto =new ProductUpdationInstockDto();
		productUpDto.setProductSku("a123");
		productUpDto.setInStockQty(10);
		Mono<Product> monoProduct=Mono.just(new Product("1","a123", 10,0,0,0));
		Mockito.when(productService.updateProductInstockOnl("1", productUpDto, 10))
		.thenReturn(monoProduct);
		
		String id="1";
		
		webTestClient.put().uri("/inventory/update/instQtyOnly/"+id)
		.contentType(MediaType.APPLICATION_JSON)
		.body(Mono.just(productUpDto),ProductUpdationInstockDto.class)
		.exchange()
		.expectStatus().isOk()
		.expectBody(Product.class).value(successResponse -> {
            assertNotNull(successResponse);
            System.out.println(successResponse);
            System.out.println(productUpDto);
            System.out.println("---------------------");
            assertEquals(successResponse.getInStockQty(),productUpDto.getInStockQty());
          
        });
		
		
	}
	
	@Test
	void testUpdateProductReserve() {
		ProductUpdationReserveDto productUpDto =new ProductUpdationReserveDto();
		productUpDto.setProductSku("a1234");
		productUpDto.setReservedQty(2);
		Mono<Product> monoProduct=Mono.just(new Product("1","a1234", 10,2,0,0));
		Mockito.when(productService.updateProductReserveOnl("1", productUpDto, 2))
		.thenReturn(monoProduct);
		
		String id="1";
		
		webTestClient.put().uri("/inventory/update/reserveQtyOnly/"+id)
		.contentType(MediaType.APPLICATION_JSON)
		.body(Mono.just(productUpDto),ProductUpdationReserveDto.class)
		.exchange()
		.expectStatus().isOk()
		.expectBody(Product.class).value(successResponse -> {
            assertNotNull(successResponse);
          assertEquals(successResponse.getReservedQty(),productUpDto.getReservedQty());

        });
		
		
	}
	
	@Test
	void testUpdateProductDemand() {
		ProductUpdationDemandDto productUpDto =new ProductUpdationDemandDto();
		productUpDto.setProductSku("a123");
		productUpDto.setDemandQty(3);
		Mono<Product> monoProduct=Mono.just(new Product("1","a123", 10,0,3,7));
		Mockito.when(productService.updateProductDemandOnl("1", productUpDto, 3))
		.thenReturn(monoProduct);
		
		String id="1";
		
		webTestClient.put().uri("/inventory/update/demandQtyOnly/"+id)
		.contentType(MediaType.APPLICATION_JSON)
		.body(Mono.just(productUpDto),ProductUpdationDemandDto.class)
		.exchange()
		.expectStatus().isOk()
		.expectBody(Product.class).value(successResponse -> {
            assertNotNull(successResponse);
         //   System.out.print(successResponse);
            assertEquals(successResponse.getDemandQty(),productUpDto.getDemandQty());

        });
		
		
	}
	
	@Test
	void getProductBySkuTest() {
		Mono<Product> product = Mono.just(new Product("1","a123", 10,0,0,0));
		Mockito.when(productService.getProductByProductSku("a123")).thenReturn(product);
		
		String productSku = "a123";
		
		webTestClient.get().uri("/inventory/"+productSku)
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus()
		.isOk()
		.expectBody()
		.jsonPath("productSku").isEqualTo("a123");
		
	}
	
	@Test
	void getProductByAvailibilityTest() {
		Flux<Product> products = Flux.just(new Product("1","a123", 10,0,0,10),new Product("2","a124", 20,0,0,20));
		Mockito.when(productService.getProductsByAvail()).thenReturn(products);
		

		
		webTestClient.get().uri("/inventory/byAvailability")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus()
		.isOk()
		.expectBody()
		.jsonPath("$").isArray()
		.jsonPath("$[0].id").isEqualTo("1")
		.jsonPath("$[0].productSku").isEqualTo("a123")
		.jsonPath("$[1].id").isEqualTo("2")
		.jsonPath("$[1].productSku").isEqualTo("a124");
		
	}
}
