package com.javaspring.api.service;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.javaspring.api.dto.ProductCreationDto;
import com.javaspring.api.dto.ProductUpdationDemandDto;
import com.javaspring.api.dto.ProductUpdationDto;
import com.javaspring.api.dto.ProductUpdationInstockDto;
import com.javaspring.api.dto.ProductUpdationReserveDto;
import com.javaspring.api.entity.Product;
import com.javaspring.api.exception.ErrorCode;
import com.javaspring.api.exception.SystemException;
import com.javaspring.api.repository.ProductRepository;
import com.javaspring.api.utils.AppUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	public ProductService(ProductRepository repo) {
		this.productRepo=repo;
	}
	public Flux<Product> getProducts(){
		return productRepo.findAll();
		
	}
	public Flux<Product> getProductsByAvail(){
		return productRepo.findProductByAvailability();
		
	}
	
	
	public Mono<Product> getProductByProductSku(String product_sku) {
        Mono<Product> savedProductMono = productRepo.findByProductSku(product_sku);
        return savedProductMono.hasElement().flatMap(exists -> {
            if (Boolean.FALSE.equals(exists)) {
                return Mono.error(
                        new SystemException(ErrorCode.ENTITY_NOT_FOUND, "Resource not found"));
            }
            return savedProductMono;
        });
    }
	
	
	public Mono<Product> saveProduct(ProductCreationDto productDtoMono){
		System.out.println("he");
	
		if(productDtoMono.getInStockQty()<0)
		{
			return Mono.error(new SystemException(ErrorCode.INSTOCK_CANNOT_BE_NEGATIVE, "Instock is less than zero!!"));
					
		}
		
		Mono<Product> newProduct = productRepo.findByProductSku(productDtoMono.getProductSku());
		return newProduct.hasElement().flatMap(exists -> {
			if (Boolean.TRUE.equals(exists)) {
                return Mono.error(
                        new SystemException(ErrorCode.PRODUCTSKU_MUST_BE_UNIQUE, "Duplicate Entry"));
            }
			System.out.println("Hello.."+productDtoMono.getInStockQty()+"...");	
			Product product = AppUtils.dtoToEntity(productDtoMono);		
			Integer productInstockQty=product.getInStockQty().intValue();
			product.setReservedQty(0);
			product.setDemandQty(0);
			product.setAvailableQty(0);
			Integer productReserveQty=0;
			Integer productDemandQty=0;
			Integer availableQty=productInstockQty-productReserveQty-productDemandQty;
			product.setAvailableQty(availableQty);
			System.out.println(productInstockQty+" "+productReserveQty+" "+productDemandQty+" = "+availableQty);

			
			return productRepo.save(product);
			
		});
//		System.out.println("Hello.."+productDtoMono.getInStockQty()+"...");	
//		Product product = AppUtils.dtoToEntity(productDtoMono);		
//		Integer productInstockQty=product.getInStockQty().intValue();
//		product.setReservedQty(0);
//		product.setDemandQty(0);
//		product.setAvailableQty(0);
//		Integer productReserveQty=0;
//		Integer productDemandQty=0;
//		Integer availableQty=productInstockQty-productReserveQty-productDemandQty;
//		product.setAvailableQty(availableQty);
//		System.out.println(productInstockQty+" "+productReserveQty+" "+productDemandQty+" = "+availableQty);
//
//		
//		return productRepo.save(product);
		
	}
	
	    
	    public Mono<Product> updateProductInstockOnl(String productId,
	            @Valid @RequestBody ProductUpdationInstockDto productDtoMono, Integer inst) {
	    	if(inst<0)
			{
				return Mono.error(new SystemException(ErrorCode.INSTOCK_CANNOT_BE_NEGATIVE, "Instock is less than zero!!"));
						
			}
	    	
	    	Mono<Product> savedProductMono = productRepo.findById(productId);
	        
	        return savedProductMono.hasElement().flatMap(exists -> {
	        	
	        	if (Boolean.FALSE.equals(exists)) {
	                return Mono.error(
	                        new SystemException(ErrorCode.ENTITY_NOT_FOUND, "Resource not found"));
	            }
	        	
	        	return savedProductMono.flatMap(p->{
	        		if(inst<(p.getReservedQty()+p.getDemandQty())) {
	        			return Mono.error(new SystemException(ErrorCode.INSTOCK_CANNOT_BE_NEGATIVE, "Instock is less than zero!!"));
	        		}
	        		
	        		Integer oldInstock=p.getInStockQty();
	        		System.out.println(oldInstock);
	        		p.setInStockQty(inst+oldInstock);
	        		
	        		
	        		
	        		Integer productInstockQty=p.getInStockQty();
	        		Integer productReserveQty=p.getReservedQty();
	        		Integer productDemandQty=p.getDemandQty();
	        		Integer availableQty=productInstockQty-productReserveQty-productDemandQty;
            		p.setAvailableQty(availableQty);
	        		return productRepo.save(p);
	        	});
	    });
	    }
	    public Mono<Product> updateProductReserveOnl(String productId,
	           @Valid @RequestBody ProductUpdationReserveDto productDtoMono, Integer reserve) {
	    	if(reserve<0)
			{
				return Mono.error(new SystemException(ErrorCode.RESERVE_CANNOT_BE_NEGATIVE, "Reserve Qty is less than zero!!"));
						
			}
	        Mono<Product> savedProductMono = productRepo.findById(productId);
	        
	        return savedProductMono.hasElement().flatMap(exists -> {
	        	if (Boolean.FALSE.equals(exists)) {
	                return Mono.error(
	                        new SystemException(ErrorCode.ENTITY_NOT_FOUND, "Resource not found"));
	            }
	        	return savedProductMono.flatMap(p->{
	        		
	        		Integer oldReserve=p.getReservedQty();
	        		if(p.getInStockQty()<(oldReserve+reserve+p.getDemandQty())) {
	        			return Mono.error(new SystemException(ErrorCode.INSTOCK_CANNOT_BE_NEGATIVE, "Instock is less than zero!!"));
	        		}
	        		p.setReservedQty(reserve+oldReserve);
	        		
	        		Integer productInstockQty=p.getInStockQty();
	        		Integer productReserveQty=p.getReservedQty();
	        		Integer productDemandQty=p.getDemandQty();
	        		Integer availableQty=productInstockQty-productReserveQty-productDemandQty;
            		p.setAvailableQty(availableQty);
	        		return productRepo.save(p);
	        	});
	    });
	    }
	    
	    public Mono<Product> updateProductDemandOnl(String productId,
	    		@Valid @RequestBody ProductUpdationDemandDto productDtoMono, Integer demand) {
	    	if(demand<0)
			{
				return Mono.error(new SystemException(ErrorCode.DEMAND_CANNOT_BE_NEGATIVE, "Demand Qty is less than zero!!"));
						
			}
	    	Mono<Product> savedProductMono = productRepo.findById(productId);
	        
	        return savedProductMono.hasElement().flatMap(exists -> {
	        	if (Boolean.FALSE.equals(exists)) {
	                return Mono.error(
	                        new SystemException(ErrorCode.ENTITY_NOT_FOUND, "Resource not found"));
	            }
	        	return savedProductMono.flatMap(p->{
	        		
	        		
	        		Integer oldDemand=p.getDemandQty();
	        		if(p.getInStockQty()<(p.getReservedQty()+demand+oldDemand)) {
	        			return Mono.error(new SystemException(ErrorCode.INSTOCK_CANNOT_BE_NEGATIVE, "Instock is less than zero!!"));
	        		}
	        		p.setDemandQty(demand+oldDemand);
	        		
	        		
	        		Integer productInstockQty=p.getInStockQty();
	        		Integer productReserveQty=p.getReservedQty();
	        		Integer productDemandQty=p.getDemandQty();
	        		Integer availableQty=productInstockQty-productReserveQty-productDemandQty;
            		p.setAvailableQty(availableQty);
	        		return productRepo.save(p);
	        	});
	    });
	    }
	    
	    
	    
	    public Mono<Product> updateProductInstockByAction(String productId,@Valid @RequestBody ProductUpdationInstockDto productDtoMono, Integer instock,String action) {
	    	System.out.println(action+"..."+action.equals("add"));
	    	if(action.equals("add")) {
	    		System.out.print("Hel");
	    		return updateProductInstockOnl(productId, productDtoMono, instock);
	    	}
	    	else if(action.equals("replace"))
	    	{
	    		if(instock<0)
				{
					return Mono.error(new SystemException(ErrorCode.INSTOCK_CANNOT_BE_NEGATIVE, "Instock Qty is less than zero!!"));
							
				}
		    	Mono<Product> savedProductMono = productRepo.findById(productId);
		        
		        return savedProductMono.hasElement().flatMap(exists -> {
		        	if (Boolean.FALSE.equals(exists)) {
		                return Mono.error(
		                        new SystemException(ErrorCode.ENTITY_NOT_FOUND, "Resource not found"));
		            }
		        	return savedProductMono.flatMap(p->{
		        		
		        		
		        		//Integer oldDemand=p.getDemandQty();
		        		if(instock<(p.getReservedQty()+p.getDemandQty())) {
		        			return Mono.error(new SystemException(ErrorCode.INSTOCK_CANNOT_BE_NEGATIVE, "Instock is less than zero!!"));
		        		}
		        		//p.setDemandQty(demand+oldDemand);
		        		
		        		p.setInStockQty(instock);
		        		Integer productInstockQty=p.getInStockQty();
		        		Integer productReserveQty=p.getReservedQty();
		        		Integer productDemandQty=p.getDemandQty();
		        		Integer availableQty=productInstockQty-productReserveQty-productDemandQty;
	            		p.setAvailableQty(availableQty);
		        		return productRepo.save(p);
		        	});
		    });
	    	}
	    	
	    	return Mono.error(new SystemException(ErrorCode.ACTION_NOT_EQUAL_TO_REPLACE_OR_ADD, "Add a proper action"));
	    	
	    	
	    }
	    
	    
	    public Mono<Product> updateProductDemandByAction(String productId,@Valid @RequestBody ProductUpdationDemandDto productDtoMono, Integer demand,String action) {
	    	System.out.println(action+"..."+action.equals("add"));
	    	if(action.equals("add")) {
	    		System.out.print("Hel");
	    		return updateProductDemandOnl(productId, productDtoMono, demand);
	    	}
	    	else if(action.equals("replace"))
	    	{
	    		if(demand<0)
				{
					return Mono.error(new SystemException(ErrorCode.DEMAND_CANNOT_BE_NEGATIVE, "Demand Qty is less than zero!!"));
							
				}
		    	Mono<Product> savedProductMono = productRepo.findById(productId);
		        
		        return savedProductMono.hasElement().flatMap(exists -> {
		        	if (Boolean.FALSE.equals(exists)) {
		                return Mono.error(
		                        new SystemException(ErrorCode.ENTITY_NOT_FOUND, "Resource not found"));
		            }
		        	return savedProductMono.flatMap(p->{
		        		
		        		
		        		//Integer oldDemand=p.getDemandQty();
		        		if(p.getInStockQty()<(p.getReservedQty()+demand)) {
		        			return Mono.error(new SystemException(ErrorCode.INSTOCK_CANNOT_BE_NEGATIVE, "Instock is less than zero!!"));
		        		}
		        		//p.setDemandQty(demand+oldDemand);
		        		
		        		p.setDemandQty(demand);
		        		Integer productInstockQty=p.getInStockQty();
		        		Integer productReserveQty=p.getReservedQty();
		        		Integer productDemandQty=p.getDemandQty();
		        		Integer availableQty=productInstockQty-productReserveQty-productDemandQty;
	            		p.setAvailableQty(availableQty);
		        		return productRepo.save(p);
		        	});
		    });
	    	}
	    	
	    	return Mono.error(new SystemException(ErrorCode.ACTION_NOT_EQUAL_TO_REPLACE_OR_ADD, "Add a proper action"));
	    	
	    	
	    }
	    
	    public Mono<Product> updateProductReserveByAction(String productId,@Valid @RequestBody ProductUpdationReserveDto productDtoMono, Integer reserve,String action) {
	    	System.out.println(action+"..."+action.equals("add"));
	    	if(action.equals("add")) {
	    		System.out.print("Hel");
	    		return updateProductReserveOnl(productId, productDtoMono, reserve);
	    	}
	    	else if(action.equals("replace"))
	    	{
	    		if(reserve<0)
				{
					return Mono.error(new SystemException(ErrorCode.RESERVE_CANNOT_BE_NEGATIVE, "Demand Qty is less than zero!!"));
							
				}
		    	Mono<Product> savedProductMono = productRepo.findById(productId);
		        
		        return savedProductMono.hasElement().flatMap(exists -> {
		        	if (Boolean.FALSE.equals(exists)) {
		                return Mono.error(
		                        new SystemException(ErrorCode.ENTITY_NOT_FOUND, "Resource not found"));
		            }
		        	return savedProductMono.flatMap(p->{
		        		
		        		
		        		//Integer oldDemand=p.getDemandQty();
		        		if(p.getInStockQty()<(reserve+p.getDemandQty())) {
		        			return Mono.error(new SystemException(ErrorCode.INSTOCK_CANNOT_BE_NEGATIVE, "Instock is less than zero!!"));
		        		}
		        		//p.setDemandQty(demand+oldDemand);
		        		
		        		p.setReservedQty(reserve);
		        		Integer productInstockQty=p.getInStockQty();
		        		Integer productReserveQty=p.getReservedQty();
		        		Integer productDemandQty=p.getDemandQty();
		        		Integer availableQty=productInstockQty-productReserveQty-productDemandQty;
	            		p.setAvailableQty(availableQty);
		        		return productRepo.save(p);
		        	});
		    });
	    	}
	    	
	    	return Mono.error(new SystemException(ErrorCode.ACTION_NOT_EQUAL_TO_REPLACE_OR_ADD, "Add a proper action"));
	    	
	    	
	    }

	
	
}
