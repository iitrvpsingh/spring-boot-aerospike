package com.demo.aerospike.springbootaerospike.cache;

public class AerospikeProperties {

	public enum Namespace {
		BasicUserSpace("BasicUserSpace");

		private String value;

		Namespace(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

	}

	public enum Set {
		USER_SET("usersset");

		private String value;

		Set(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum Bin {
		USER_BIN("usersBin");
		private String value;

		Bin(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

}
