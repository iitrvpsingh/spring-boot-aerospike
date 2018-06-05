package com.demo.aerospike.springbootaerospike.cache;

import org.springframework.stereotype.Service;

import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;

@Service
public class AerospikeConfiguration {


	   private ClientPolicy clientPolicy;
	   private Policy readPolicy;
	   private WritePolicy writePolicy;

	   public void loadConfiguration() {
	      // initialize client policy
	      this.clientPolicy = new ClientPolicy();

	      String maxSocketIdle = ConfigurationConstants.SOCKET_IDLE.getDefaultValue();
	      if (maxSocketIdle != null && !maxSocketIdle.isEmpty()) {
	         this.clientPolicy.maxSocketIdle = Integer.parseInt(maxSocketIdle);
	      }

	      String maxThreads = ConfigurationConstants.MAX_THREADS.getDefaultValue();
	      if (maxThreads != null && !maxThreads.isEmpty()) {
	         this.clientPolicy.maxThreads = Integer.parseInt(maxThreads);
	      }

	      String isThreadPoolShared = ConfigurationConstants.SHARED_THREADS.getDefaultValue();
	      if (isThreadPoolShared != null && !isThreadPoolShared.isEmpty()) {
	         this.clientPolicy.sharedThreadPool = Boolean.parseBoolean(isThreadPoolShared);
	      }

	      String connTimeout = ConfigurationConstants.CONNECTION_TIMEOUT.getDefaultValue();
	      if (connTimeout != null && !connTimeout.isEmpty()) {
	         this.clientPolicy.timeout = Integer.parseInt(connTimeout);
	      }

	      // initialize default read policy
	      this.readPolicy = new Policy();

	      String maxRetries = ConfigurationConstants.DEFAULT_MAX_READ_RETRIES.getDefaultValue();
	      if (maxRetries != null && !maxRetries.isEmpty()) {
	         this.readPolicy.maxRetries = Integer.parseInt(maxRetries);
	      }

	      String sleepInterval = ConfigurationConstants.DEFAULT_SLEEP_BETWEEN_READ_RETRIES
	            .getDefaultValue();
	      if (sleepInterval != null && !sleepInterval.isEmpty()) {
	         this.readPolicy.sleepBetweenRetries = Integer.parseInt(sleepInterval);
	      }

	      String readTimeout = ConfigurationConstants.DEFAULT_READ_TIMEOUT.getDefaultValue();
	      if (readTimeout != null && !readTimeout.isEmpty()) {
	         this.readPolicy.timeout = Integer.parseInt(readTimeout);
	      }
	      this.writePolicy = new WritePolicy();
	   }

	   public ClientPolicy getClientPolicy() {
	      return clientPolicy;
	   }

	   public Policy getReadPolicy() {
	      return readPolicy;
	   }

	   public WritePolicy getWritePolicy() {
	      return writePolicy;
	   }


}
