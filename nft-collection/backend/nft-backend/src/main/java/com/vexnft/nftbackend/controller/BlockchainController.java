package com.vexnft.nftbackend.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import com.vexnft.nftbackend.service.BlockchainService;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

	private final BlockchainService blockchainService;

	public BlockchainController(BlockchainService blockchainService) {
		this.blockchainService = blockchainService;
	}

	@Operation(summary = "Check blockchain connection", description = "Checks the connection with Arbitrum One and returns the current chain ID and latest block.")
	@GetMapping("/status")
	public Map<String, Object> getStatus() throws IOException {

		return Map.of("connected", true, "network", "Arbitrum One", "chainId", blockchainService.getChainId(),
				"latestBlock", blockchainService.getLatestBlock());
	}
}