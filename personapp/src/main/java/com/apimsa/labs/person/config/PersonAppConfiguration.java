package com.apimsa.labs.person.config;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

@Configuration
public class PersonAppConfiguration extends RedisConfiguration{



    @Autowired
    public CustomConfigProperties appConf;
	
    
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        
    	JedisConnectionFactory jedisConnectionFactory = null;
    	
        if(appConf.getActiveRedisMode().equalsIgnoreCase(RedisAppConstants.REDIS_SENTINEL_MODE)) {

        	RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        	
            String[] sentinelNodes = appConf.getRedis().getSentinel().getHost().split(";");

            Set sentinels = new HashSet<>();

            for (String sentinelNode : sentinelNodes) {

                if (sentinelNode != null &&
                        !sentinelNode.isEmpty()) {
                    sentinels.add(sentinelNode);

                }

            }
            
            JedisPoolConfig jedisPoolConfig = poolConfig();   
            
            jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration, poolConfig());
        }
        else if(appConf.getActiveRedisMode().equalsIgnoreCase(RedisAppConstants.REDIS_STANDALONE_MODE)) {
        		
        		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
                redisStandaloneConfiguration.setPassword(RedisPassword.of("password"));
                jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        }
        
        return jedisConnectionFactory;
    }
    
    

    @Bean
    public JedisCluster jedisCluster() {
    
    	
	    JedisCluster jedisCluster = null;
	    
	    if(appConf.getActiveRedisMode().equalsIgnoreCase(RedisAppConstants.REDIS_CLUSTER_MODE)) {
	    	String[] clusterNodes = appConf.getRedis().getCluster().getHost().split(";");
	
	
	        Set<HostAndPort> clusters = new HashSet<>();
	
	
	        for (String clusterNode : clusterNodes) {
	
	            if (clusterNode != null &&
	                    !clusterNode.isEmpty()) {
	
	                String[] clusterNodeEndpoint = clusterNode.split(":");
	
	                if (clusterNodeEndpoint.length == 2)
	                    clusters.add(new HostAndPort(clusterNodeEndpoint[0], Integer.parseInt(clusterNodeEndpoint[1])));
	
	            }
	
	        }
	
	        JedisPoolConfig jedisPoolConfig = poolConfig();
	
	
	        jedisCluster = new JedisCluster(clusters,
	                appConf.getRedis().getCluster().getTimeout(),
	                appConf.getRedis().getCluster().getTimeout(), jedisPoolConfig);
	    }
	    
	    return jedisCluster;
    }
    

    
}
