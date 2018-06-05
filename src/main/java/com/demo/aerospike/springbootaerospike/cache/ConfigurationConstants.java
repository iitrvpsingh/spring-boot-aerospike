package com.demo.aerospike.springbootaerospike.cache;


public enum ConfigurationConstants {

	// Aerospike Properties
	// Aerospike Client properties
	SOCKET_IDLE("aerospike.maxSocketIdle", null), MAX_THREADS("aerospike.maxThreads", "300"), SHARED_THREADS(
			"aerospike.sharedThreadPool", "false"), CONNECTION_TIMEOUT("aerospike.connection.timeout", "60000"),

	// aerospike read policy
	DEFAULT_MAX_READ_RETRIES("aerospike.default.read.maxRetries", "2"), DEFAULT_SLEEP_BETWEEN_READ_RETRIES(
			"aerospike.default.read.sleepBetweenRetries",
			"3"), DEFAULT_READ_TIMEOUT("aerospike.default.read.timeout", "60000");

	private String key;
	private String defaultValue;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	ConfigurationConstants(String key, String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}

	public static ConfigurationConstants forName(String key) {
		ConfigurationConstants configConst = null;
		for (ConfigurationConstants constant : values()) {
			if (constant.getKey().equals(key)) {
				return constant;
			}
		}
		return configConst;
	}

}
