package com.example;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RouteServiceController {
	private static final String FORWARDED_URL = "X-CF-Forwarded-Url";
	private static final String PROXY_METADATA = "X-CF-Proxy-Metadata";
	private static final String PROXY_SIGNATURE = "X-CF-Proxy-Signature";
	private final Logger log = LoggerFactory.getLogger(RouteServiceController.class);

	private final RestTemplate restTemplate;

	public RouteServiceController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@RequestMapping(headers = { FORWARDED_URL, PROXY_METADATA, PROXY_SIGNATURE })
	ResponseEntity<?> service(RequestEntity<byte[]> incoming) {
		log.info("Incoming Request: {}", incoming);
		RequestEntity<?> outgoing = getOutgoingRequest(incoming);
		log.info("Outgoing Request: {}", outgoing);
		return restTemplate.exchange(outgoing, byte[].class);
	}

	private static RequestEntity<?> getOutgoingRequest(RequestEntity<?> incoming) {
		HttpHeaders headers = new HttpHeaders();
		headers.putAll(incoming.getHeaders());

		URI uri = headers.remove(FORWARDED_URL).stream().findFirst().map(URI::create)
				.orElseThrow(() -> new IllegalStateException(
						String.format("No %s header present", FORWARDED_URL)));

		return new RequestEntity<>(incoming.getBody(), headers, incoming.getMethod(),
				uri);
	}
}
