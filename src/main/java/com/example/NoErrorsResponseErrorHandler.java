package com.example;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class NoErrorsResponseErrorHandler extends DefaultResponseErrorHandler {
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return false;
	}
}