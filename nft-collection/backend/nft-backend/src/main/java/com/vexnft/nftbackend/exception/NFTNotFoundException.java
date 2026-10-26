package com.vexnft.nftbackend.exception;

import java.math.BigInteger;

public class NFTNotFoundException extends RuntimeException {

	public NFTNotFoundException(BigInteger tokenId) {
		super("Token " + tokenId + " does not exist");
	}
}