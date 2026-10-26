package com.vexnft.nftbackend.dto;

import java.math.BigInteger;

public record NFTResponse(BigInteger tokenId, String name, String description, String image, String owner,
		String tokenUri, String network, String contractAddress) {
}