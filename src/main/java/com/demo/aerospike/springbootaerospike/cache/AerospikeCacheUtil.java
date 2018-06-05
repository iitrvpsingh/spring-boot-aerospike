package com.demo.aerospike.springbootaerospike.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;

@Service
public class AerospikeCacheUtil {

	private static final Logger log = LoggerFactory.getLogger(AerospikeCacheUtil.class);

	public void handleAerospikeExceptionEvictKey(Key key, String string, AerospikeException aex) {
		log.error(string, aex);
	}

	public void logCacheOperation(OPERATION op, AerospikeProperties.Set set, AerospikeProperties.Bin bin, Object key,
			Object value) {
		log.trace("{} | {} | {} | {} => {}", new Object[] { op, set, bin, key, value });
	}

	public enum OPERATION {
		GET, PUT, EVICT, TOUCH
	}
}
