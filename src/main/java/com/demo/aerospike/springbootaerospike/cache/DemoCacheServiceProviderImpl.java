package com.demo.aerospike.springbootaerospike.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Host;
import com.aerospike.client.cluster.Node;

@Service("DemoCacheServiceProviderImpl")
public class DemoCacheServiceProviderImpl implements DemoCacheServiceProvider {

	private Logger log = LoggerFactory.getLogger(DemoCacheServiceProviderImpl.class);

	private AerospikeConfiguration config;

	private String hostname;
	
	private int port;

	private String clusterInfo;

	private AerospikeBaseClient aerospikeClient;

	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}

	@Override
	public boolean isConnected() {
		if (aerospikeClient == null) {
			log.warn("umsAerospikeClient is NULL.");
			return false;
		}
		return aerospikeClient.isConnected();
	}

	@Override
	public void connectToCacheCluster(String clusterInfo) {
		try {
			if (this.aerospikeClient == null || this.clusterInfo == null || !this.clusterInfo.equals(clusterInfo)
					|| !isConnected()) {
				String[] nodes = clusterInfo.split(",");
				Host[] hosts = new Host[nodes.length];
				for (int i = 0; i < nodes.length; i++) {
					hosts[i] = new Host(nodes[i].split(":")[0], Integer.parseInt(nodes[i].split(":")[1]));
				}

				// Load Configurations
				this.config = new AerospikeConfiguration();
				this.config.loadConfiguration();

				log.info("Going to initialize AerospikeClient. Hosts are: " + hosts);
				try {
					Class.forName("com.aerospike.client.cluster.Node");
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
				this.aerospikeClient = new AerospikeBaseClient(this.config.getClientPolicy(), hosts);
				if (isConnected()) {
					log.info("Connected to Aerospike cluster");
					this.clusterInfo = clusterInfo;
				} else {
					log.error("Unable to connect to Aerospike cluster");
					throw new RuntimeException("Unable to connect to Aerospike cluster");
				}
			} else {
				log.info("No change in Aerospike connection properties");
			}

			// Prints hostname and port of all the cluster nodes to log stream
			for (Node node : this.aerospikeClient.getNodes()) {
				log.info(node.getHost().toString());
			}

		} catch (AerospikeException aex) {
			handleAerospikeException(clusterInfo, "Exception when connecting to Aerospike: ", aex);

		}
	}

	@Override
	public AerospikeBaseClient getClient() {
		if (aerospikeClient == null) {
			throw new RuntimeException("Exception occoured");
		}
		return aerospikeClient;
	}

	@Override
	public AerospikeConfiguration getClientConfig() {
		if (config == null) {
			throw new RuntimeException("Exception occured");
		}
		return config;
	}

	private void handleAerospikeException(String clusterInfo2, String string, AerospikeException aex) {
		log.error(string + clusterInfo2, aex);
		// since aerosipke is our primary source of truth, if not able to
		// connect due to any situation runtime exception should be thrown
		throw new RuntimeException(aex);

	}

}
