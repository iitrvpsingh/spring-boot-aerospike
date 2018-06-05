package com.demo.aerospike.springbootaerospike.cache;

public interface BaseAerospikeSet<Key, Value> {

	public AerospikeRecord<Value> get(Key keyContent);

	public boolean insert(Key keyContent, Value value);

	public boolean update(Key keyContent, AerospikeRecord<Value> valueRecord);

	public boolean delete(Key keyContent);

	public boolean exist(Key keyContent);
}
