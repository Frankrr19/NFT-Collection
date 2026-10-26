package com.vexnft.nftbackend.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

@Service
public class BlockchainService {

	private final Web3j web3j;

	public BlockchainService(Web3j web3j) {
		this.web3j = web3j;
	}

	public long getChainId() throws IOException {
		return web3j.ethChainId().send().getChainId().longValue();
	}

	public long getLatestBlock() throws IOException {
		return web3j.ethBlockNumber().send().getBlockNumber().longValue();
	}
}