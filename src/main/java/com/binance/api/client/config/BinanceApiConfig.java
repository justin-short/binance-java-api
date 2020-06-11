package com.binance.api.client.config;

/**
 * Configuration used for Binance operations.
 */
public class BinanceApiConfig {

	private final String apiKey;
	private final String secret;
	private final String baseDomain;

	public BinanceApiConfig(String baseDomain) {
		this(null, null, baseDomain);
	}
	
	public BinanceApiConfig(String apiKey, String secret, String baseDomain) {
		this.apiKey = apiKey;
		this.secret = secret;
		this.baseDomain = baseDomain;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getSecret() {
		return secret;
	}

	/**
	 * Get the URL base domain name (e.g., binance.com).
	 *
	 * @return The base domain for URLs
	 */
	public String getBaseDomain() {
		return baseDomain;
	}

	/**
	 * REST API base URL.
	 */
	public String getApiBaseUrl() {
		return String.format("https://api.%s", getBaseDomain());
	}

	/**
	 * Streaming API base URL.
	 */
	public String getStreamApiBaseUrl() {
		return String.format("wss://stream.%s:9443/ws", getBaseDomain());
	}

	/**
	 * Asset info base URL.
	 */
	public String getAssetInfoApiBaseUrl() {
		return String.format("https://%s/", getBaseDomain());
	}
}
