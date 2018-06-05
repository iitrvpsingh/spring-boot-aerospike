package com.demo.aerospike.springbootaerospike.cache;

import org.springframework.beans.factory.annotation.Autowired;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.demo.aerospike.springbootaerospike.cache.AerospikeProperties.Namespace;
import com.demo.aerospike.springbootaerospike.cache.AerospikeProperties.Set;
import com.demo.aerospike.springbootaerospike.cache.AerospikeCacheUtil.OPERATION;;

public abstract class AerospikeBaseClassWrapper<SetKey, SetValue> implements BaseAerospikeSet<SetKey, SetValue> {

	@Autowired
	DemoCacheServiceProvider cacheServiceProvider;

	protected Namespace namespace = Namespace.BasicUserSpace;

	protected Set set;

	@Autowired
	AerospikeCacheUtil cacheUtil;

	public AerospikeBaseClassWrapper(Set set) {
		this.set = set;
	}

	@Override
	public AerospikeRecord<SetValue> get(SetKey keyContent) {
		AerospikeRecord<SetValue> value = new AerospikeRecord<SetValue>();

		Key key = getKey(keyContent);
		try {
			Policy policy = cacheServiceProvider.getClientConfig().getReadPolicy();
			Record record = cacheServiceProvider.getClient().get(policy, key);

			if (record != null) {
				value = new AerospikeRecord<SetValue>(record, getBin());

				checkAndRefreshValueTTL(keyContent, record.expiration);
			}

			cacheUtil.logCacheOperation(OPERATION.GET, set, getBin(), keyContent, value);
		} catch (AerospikeException aex) {
			cacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to fetch record from aerospike", aex);
		}

		return value;
	}

	@Override
	public boolean insert(SetKey keyContent, SetValue value) {
		boolean writeSuccessful = true;
		Key key = getKey(keyContent);
		try {
			// write policy will contain recordExistsAction and expiration which
			// comes in value itself
			WritePolicy writePolicy = getWritePolicy(value);

			Bin valueBin = new Bin(getBin().getValue(), value);

			cacheServiceProvider.getClient().put(writePolicy, key, valueBin);

			cacheUtil.logCacheOperation(OPERATION.PUT, set, getBin(), keyContent, value);
		} catch (AerospikeException aex) {
			cacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to write data in aerospike: ", aex);
			writeSuccessful = false;
		}
		return writeSuccessful;
	}

	@Override
	public boolean update(SetKey keyContent, AerospikeRecord<SetValue> valueRecord) {
		boolean writeSuccessful = true;
		Key key = getKey(keyContent);
		try {

			// This write policy should have been from the existingRecord which was
			// fetched and updated : "which is value as passed parameter"
			WritePolicy writePolicy = getUpdatePolicy(valueRecord);

			Bin valueBin = new Bin(getBin().getValue(), valueRecord.getValue());
			cacheServiceProvider.getClient().put(writePolicy, key, valueBin);

			cacheUtil.logCacheOperation(OPERATION.PUT, set, getBin(), keyContent, valueRecord.getValue());
		} catch (AerospikeException aex) {
			cacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to update data in aerospike: ", aex);
			writeSuccessful = false;
		}
		return writeSuccessful;
	}

	@Override
	public boolean delete(SetKey keyContent) {
		boolean deleteSuccessful = true;
		Key key = getKey(keyContent);
		try {
			WritePolicy deletePolicy = getDeletePolicy();
			cacheServiceProvider.getClient().delete(deletePolicy, key);

			cacheUtil.logCacheOperation(OPERATION.EVICT, set, getBin(), keyContent, null);
		} catch (AerospikeException aex) {
			cacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to delete data in aerospike: ", aex);
			deleteSuccessful = false;
		}
		return deleteSuccessful;
	}

	@Override
	public boolean exist(SetKey keyContent) {

		AerospikeRecord<SetValue> value = get(keyContent);
		if (value != null && value.getValue() != null) {
			return true;
		}

		return false;
	}

	abstract Key getKey(SetKey keyContent);

	abstract WritePolicy getWritePolicy(SetValue value);

	abstract WritePolicy getDeletePolicy();

	abstract WritePolicy getUpdatePolicy(AerospikeRecord<SetValue> existingRecord);

	abstract WritePolicy getRefreshTTLPolicy(int ttlSeconds);

	abstract AerospikeProperties.Bin getBin();

	abstract void checkAndRefreshValueTTL(SetKey keyContent, int expiration);

}
