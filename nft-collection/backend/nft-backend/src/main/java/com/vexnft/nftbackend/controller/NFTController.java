package com.vexnft.nftbackend.controller;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vexnft.nftbackend.dto.NFTMetadata;
import com.vexnft.nftbackend.dto.NFTResponse;
import com.vexnft.nftbackend.service.NFTService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/nfts")
public class NFTController {

	private final NFTService nftService;

	@Value("${blockchain.contract-address}")
	private String contractAddress;

	public NFTController(NFTService nftService) {
		this.nftService = nftService;
	}

	@Operation(summary = "Get NFT information", description = "Returns on-chain ownership information and IPFS metadata for a given NFT.")
	@GetMapping("/{tokenId}")
	public NFTResponse getNFT(@PathVariable BigInteger tokenId) throws IOException {

		String owner = nftService.getOwner(tokenId);
		String tokenUri = nftService.getTokenUri(tokenId);

		NFTMetadata metadata = nftService.getMetadata(tokenUri);

		return new NFTResponse(tokenId, metadata.name(), metadata.description(), metadata.image(), owner, tokenUri,
				"Arbitrum One", contractAddress);
	}
}