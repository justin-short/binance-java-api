package com.binance.api.client;

import com.binance.api.client.config.BinanceApiConfig;
import com.binance.api.client.impl.*;

/**
 * A factory for creating BinanceApi client objects.
 */
public class BinanceApiClientFactory {

	private static final String DEFAULT_DOMAIN = "binance.com";
	
	private final BinanceApiConfig config;
	private final BinanceApiServiceGenerator service;

	/**
	 * Instantiates a new binance api client factory.
	 *
	 * @param apiKey the API key
	 * @param secret the Secret
	 */
	private BinanceApiClientFactory(BinanceApiConfig config) {
		this.config = config;
		this.service = new BinanceApiServiceGenerator(); 
	}

	/**
	 * New instance.
	 *
	 * @param apiKey the API key
	 * @param secret the Secret
	 *
	 * @return the binance api client factory
	 */
	public static BinanceApiClientFactory newInstance(String apiKey, String secret) {
		return newInstance(apiKey, secret, DEFAULT_DOMAIN);
	}

	public static BinanceApiClientFactory newInstance(String apiKey, String secret, String domain) {
		return new BinanceApiClientFactory(new BinanceApiConfig(apiKey, secret, domain));
	}

	/**
	 * New instance without authentication.
	 *
	 * @return the binance api client factory
	 */
	public static BinanceApiClientFactory newInstance() {
		return newInstance(null, null);
	}

	/**
	 * Creates a new synchronous/blocking REST client.
	 */
	public BinanceApiRestClient newRestClient() {
		return new BinanceApiRestClientImpl(service, config);
	}

	/**
	 * Creates a new asynchronous/non-blocking REST client.
	 */
	public BinanceApiAsyncRestClient newAsyncRestClient() {
		return new BinanceApiAsyncRestClientImpl(service, config);
	}

	/**
	 * Creates a new web socket client used for handling data streams.
	 */
	public BinanceApiWebSocketClient newWebSocketClient() {
		return new BinanceApiWebSocketClientImpl(service, config);
	}
}
