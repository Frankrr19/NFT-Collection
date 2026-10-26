package com.vexnft.nftbackend.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import com.vexnft.nftbackend.dto.NFTMetadata;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import com.vexnft.nftbackend.exception.NFTNotFoundException;

@Service
public class NFTService {

	private final Web3j web3j;
	private final RestClient restClient;

	@Value("${blockchain.contract-address}")
	private String contractAddress;

	@Value("${ipfs.gateway}")
	private String ipfsGateway;

	public NFTService(Web3j web3j) {
		this.web3j = web3j;
		this.restClient = RestClient.create();
	}

	public String getOwner(BigInteger tokenId) throws IOException {

		Function function = new Function("ownerOf", List.of(new Uint256(tokenId)),
				List.of(new TypeReference<Address>() {
				}));

		return callFunction(function, tokenId);
	}

	public String getTokenUri(BigInteger tokenId) throws IOException {

		Function function = new Function("tokenURI", List.of(new Uint256(tokenId)),
				List.of(new TypeReference<Utf8String>() {
				}));

		return callFunction(function, tokenId);
	}

	public NFTMetadata getMetadata(String tokenUri) {

		String httpUrl = tokenUri.replace("ipfs://", ipfsGateway);

		return restClient.get().uri(httpUrl).retrieve().body(NFTMetadata.class);
	}

	private String callFunction(Function function, BigInteger tokenId) throws IOException {

		String encodedFunction = FunctionEncoder.encode(function);

		Transaction transaction = Transaction.createEthCallTransaction(null, contractAddress, encodedFunction);

		var ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();

		if (ethCall.hasError()) {

			String errorMessage = ethCall.getError().getMessage();

			if (errorMessage != null && errorMessage.contains("execution reverted")) {

				throw new NFTNotFoundException(tokenId);
			}

			throw new RuntimeException("Blockchain error: " + errorMessage);
		}

		List<Type> decoded = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());

		if (decoded.isEmpty()) {
			throw new RuntimeException("No data returned by contract for function: " + function.getName());
		}

		return decoded.get(0).getValue().toString();
	}
}