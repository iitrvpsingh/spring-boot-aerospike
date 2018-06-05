package com.demo.aerospike.springbootaerospike.cache;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadAerospikeCache {

	private static final Logger log = LoggerFactory.getLogger(LoadAerospikeCache.class);

	static String aerospikeCluster = "127.0.0.1:3000";

	@Autowired
	private DemoCacheServiceProvider demoCacheServiceProvider;

	@PostConstruct
	public void loadAndUpdateAerospikeCache() {
		log.info("----------Cache update started[ConfigCache, AccessCache]------------");
		demoCacheServiceProvider.connectToCacheCluster(aerospikeCluster);
		log.info("-------------------Cache update completed-----------------");
	}
}
