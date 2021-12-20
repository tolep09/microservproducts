package com.items.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.items.app.models.Item;
import com.commons.app.models.entity.Product;
import com.items.app.models.service.IItemService;

@RefreshScope
@RestController
public class ItemController {
	private final Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private Environment env;
	
	@Value("${config.text}")
	private String text;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	
	@Qualifier("serviceFeign")
	//@Qualifier("serviceRTemplate")
	private IItemService itemService;

	@Autowired
	public ItemController(IItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		logger.info(itemService.getClass().toString());
		List<Item> items = itemService.findAll();

		if (items.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}

		return ResponseEntity.ok(items);
	}

	@GetMapping(value = "/{id}/quantity/{quantity}")
	public Item detail(@PathVariable(name = "id") Long id,
			@PathVariable(name = "quantity") Integer quantity) {
		return circuitBreakerFactory.create("items")
				.run(() -> itemService.findById(id, quantity), e -> alternative(id, quantity, e));
		//ResponseEntity.ok(itemService.findById(id, quantity));
	}
	
	private Item alternative(Long id, Integer quantity, Throwable e) {
		logger.error(e.getMessage());
		Item item = new Item();
		Product product = new Product();
		
		product.setId(id);
		product.setName("No encontrado");
		product.setPrice(0.00);
		
		item.setQuantity(quantity);
		item.setProduct(product);
		
		return item;
	}
	
	@GetMapping(value="/config")
	public ResponseEntity<?> getConfig(@Value("${server.port}") String port) {
		logger.info(text);
		
		Map<String, String> info = new HashMap<>();
		info.put("text", text);
		info.put("port", port);
		
		if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			info.put("author", env.getProperty("config.author.name"));
			info.put("email", env.getProperty("config.author.email"));
		}
		
		return new ResponseEntity<Map<String, String>>(info, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Product product) {
		Product tmpProduct = itemService.save(product);
		
		if (tmpProduct != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(tmpProduct);
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<?> update(@RequestBody Product product, @PathVariable Long id) {
		Product tmpProduct = itemService.update(product, id);
		
		if (tmpProduct != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(tmpProduct);
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		itemService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
