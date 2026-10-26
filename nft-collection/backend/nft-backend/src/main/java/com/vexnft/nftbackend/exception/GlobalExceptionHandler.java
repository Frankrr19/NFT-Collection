package com.vexnft.nftbackend.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vexnft.nftbackend.dto.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NFTNotFoundException.class)
	public ResponseEntity<ApiError> handleNFTNotFound(NFTNotFoundException exception) {

		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), "NFT not found", exception.getMessage(),
				LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}