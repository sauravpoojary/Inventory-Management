package com.javaspring.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaspring.api.dto.ProductCreationDto;
import com.javaspring.api.dto.ProductUpdationDemandDto;
import com.javaspring.api.dto.ProductUpdationDto;
import com.javaspring.api.dto.ProductUpdationInstockDto;
import com.javaspring.api.dto.ProductUpdationReserveDto;
import com.javaspring.api.entity.Product;
import com.javaspring.api.exception.ErrorCode;
import com.javaspring.api.exception.SystemException;
import com.javaspring.api.service.ProductService;
import com.javaspring.api.utils.AppUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/inventory")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping("/allProducts")
	public Flux<Product> products(){
		return service.getProducts();
	}
	
	@GetMapping("/{product_sku}")
	public Mono<Product> productsByProductSku(@PathVariable String product_sku){
		System.out.println(product_sku);
		return service.getProductByProductSku(product_sku);
	}
	
	
	@PostMapping("/saveProduct")
	public Mono<Product> saveProduct(@Valid @RequestBody ProductCreationDto productDtoMono){
		return service.saveProduct(productDtoMono);
	}
	
	
	@PutMapping("/update/instQtyOnly/{id}")
    public Mono<Product> DummyUpdateInstockOnly(@PathVariable String id,@Valid @RequestBody ProductUpdationInstockDto productDto) {
		
		
		
		Integer inst = productDto.getInStockQty();
		System.out.println(id);
        return service.updateProductInstockOnl(id, productDto, inst);
    }
	
	@PutMapping("/update/reserveQtyOnly/{id}")
    public Mono<Product> DummyUpdateReserveOnly(@PathVariable String id,@Valid @RequestBody ProductUpdationReserveDto productDto) {
		Integer reserve = productDto.getReservedQty();
		System.out.println(id);
        return service.updateProductReserveOnl(id, productDto, reserve);
    }
	
	@PutMapping("/update/demandQtyOnly/{id}")
    public Mono<Product> DummyUpdateDemandOnly(@PathVariable String id,@Valid @RequestBody ProductUpdationDemandDto productDto) {
		Integer demand = productDto.getDemandQty();
		System.out.println(id);
        return service.updateProductDemandOnl(id, productDto, demand);
    }
	
	
	@GetMapping("/byAvailability")
	public Flux<Product> getproductsByAvail(){
		System.out.println("Inside avail");
		return service.getProductsByAvail();
	}
	
	
	
	

}
