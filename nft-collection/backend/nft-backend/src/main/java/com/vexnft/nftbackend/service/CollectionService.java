package com.vexnft.nftbackend.service;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;

@Service
public class CollectionService {

	private final Web3j web3j;

	@Value("${blockchain.contract-address}")
	private String contractAddress;

	public CollectionService(Web3j web3j) {
		this.web3j = web3j;
	}

	public String getName() throws IOException {

		Function function = new Function("name", Collections.emptyList(),
				Collections.singletonList(new TypeReference<Utf8String>() {
				}));

		return callStringFunction(function);
	}

	public String getSymbol() throws IOException {

		Function function = new Function("symbol", Collections.emptyList(),
				Collections.singletonList(new TypeReference<Utf8String>() {
				}));

		return callStringFunction(function);
	}

	private String callStringFunction(Function function) throws IOException {

		String encodedFunction = FunctionEncoder.encode(function);

		Transaction transaction = Transaction.createEthCallTransaction(null, contractAddress, encodedFunction);

		var ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();

		if (ethCall.hasError()) {
			throw new RuntimeException("Error calling contract: " + ethCall.getError().getMessage());
		}

		String response = ethCall.getValue();

		System.out.println("Contract address: " + contractAddress);
		System.out.println("Function: " + function.getName());
		System.out.println("Raw response: " + response);

		var decoded = FunctionReturnDecoder.decode(response, function.getOutputParameters());

		if (decoded.isEmpty()) {
			throw new RuntimeException("Empty response from contract. Function: " + function.getName() + ", contract: "
					+ contractAddress + ", raw response: " + response);
		}

		return (String) decoded.get(0).getValue();
	}
}