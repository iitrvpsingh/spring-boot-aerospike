package com.demo.aerospike.springbootaerospike.cache;

import org.springframework.stereotype.Service;

import com.aerospike.client.Key;
import com.aerospike.client.policy.GenerationPolicy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.demo.aerospike.springbootaerospike.cache.AerospikeProperties.Bin;
import com.demo.aerospike.springbootaerospike.cache.AerospikeProperties.Set;

@Service("userListSet")
public class UserListSet extends AerospikeBaseClassWrapper<String, User> {

	public UserListSet() {
		super(Set.USER_SET);
	}

	@Override
	Key getKey(String keyContent) {
		return new Key(namespace.getValue(), set.getValue(), keyContent);
	}

	@Override
	WritePolicy getWritePolicy(User value) {
		WritePolicy writePolicy = new WritePolicy();
		writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
		return writePolicy;
	}

	@Override
	WritePolicy getDeletePolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	WritePolicy getUpdatePolicy(AerospikeRecord<User> existingRecord) {
		WritePolicy writePolicy = new WritePolicy();
		writePolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
		writePolicy.generation = existingRecord.getGeneration();
		writePolicy.generationPolicy = GenerationPolicy.EXPECT_GEN_EQUAL;
		return writePolicy;
	}

	@Override
	WritePolicy getRefreshTTLPolicy(int ttlSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	Bin getBin() {

		return Bin.USER_BIN;
	}

	@Override
	void checkAndRefreshValueTTL(String keyContent, int expiration) {
		// TODO Auto-generated method stub

	}
}
