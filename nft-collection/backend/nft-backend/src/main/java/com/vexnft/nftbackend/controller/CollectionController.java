package com.vexnft.nftbackend.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import com.vexnft.nftbackend.service.CollectionService;

@RestController
@RequestMapping("/api/collection")
public class CollectionController {

	private final CollectionService collectionService;

	public CollectionController(CollectionService collectionService) {
		this.collectionService = collectionService;
	}

	@Operation(summary = "Get collection information", description = "Reads the collection name and symbol directly from the VEXCollection smart contract.")
	@GetMapping
	public Map<String, Object> getCollection() throws IOException {

		return Map.of("name", collectionService.getName(), "symbol", collectionService.getSymbol(), "network",
				"Arbitrum One");
	}
}