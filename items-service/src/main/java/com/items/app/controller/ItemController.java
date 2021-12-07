package com.items.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.items.app.models.Item;
import com.items.app.models.service.IItemService;

@RestController
public class ItemController {
	
	
	@Qualifier("serviceFeign")
	private IItemService itemService;

	@Autowired
	public ItemController(IItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		List<Item> items = itemService.findAll();

		if (items.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}

		return ResponseEntity.ok(items);
	}

	@GetMapping(value = "/{id}/quantity/{quantity}")
	public ResponseEntity<?> detail(@PathVariable(name = "id") Long id,
			@PathVariable(name = "quantity") Integer quantity) {
		return ResponseEntity.ok(itemService.findById(id, quantity));
	}
}
