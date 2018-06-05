package com.demo.aerospike.springbootaerospike.cache;


public interface DemoCacheServiceProvider {

	public boolean isConnected();

	public void connectToCacheCluster(String clusterInfo);

	public AerospikeBaseClient getClient();

	public AerospikeConfiguration getClientConfig();

}
