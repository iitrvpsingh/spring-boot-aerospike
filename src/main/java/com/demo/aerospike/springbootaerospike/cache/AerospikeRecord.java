package com.demo.aerospike.springbootaerospike.cache;

import com.aerospike.client.Record;
import com.demo.aerospike.springbootaerospike.cache.AerospikeProperties.Bin;

public class AerospikeRecord<ValueType> {

	private ValueType value;

	private Integer expiration;

	private Integer generation;

	public AerospikeRecord() {
	}

	public ValueType getValue() {
		return value;
	}

	public void setValue(ValueType value) {
		this.value = value;
	}

	public Integer getExpiration() {
		return expiration;
	}

	public void setExpiration(Integer expiration) {
		this.expiration = expiration;
	}

	public Integer getGeneration() {
		return generation;
	}

	public void setGeneration(Integer generation) {
		this.generation = generation;
	}

	public AerospikeRecord(Record record, Bin bin) {
		value = (ValueType) record.getValue(bin.getValue());
		expiration = record.expiration;
		generation = record.generation;
	}

}
