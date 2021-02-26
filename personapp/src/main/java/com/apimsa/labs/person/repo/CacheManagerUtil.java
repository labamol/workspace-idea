package com.apimsa.labs.person.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.apimsa.labs.person.config.CustomConfigProperties;
import com.apimsa.labs.person.config.RedisAppConstants;
import com.apimsa.labs.person.config.RedisClusterConfiguration;
import com.apimsa.labs.person.config.RedisConfiguration;
import com.apimsa.labs.person.config.RedisSentinelConfiguration;
import com.apimsa.labs.person.config.RedisStandaloneConfiguration;
import com.apimsa.labs.person.domain.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

public class CacheManagerUtil {

	
	@Autowired
    RedisConfiguration redisConf;

    @Autowired
    private CustomConfigProperties appConf;

    private Jedis redisClient;
    
    public void afterPropertiesSet() {

        if (appConf.getActiveRedisMode().equalsIgnoreCase(RedisAppConstants.REDIS_CLUSTER_MODE)) {

        	JedisCluster redisClient = redisConf.getJedisCluster();
        }
        
        
        if(appConf.getActiveRedisMode().equalsIgnoreCase(RedisAppConstants.REDIS_SENTINEL_MODE)) {

            JedisSentinelPool jedisSentinelPool = redisConf.getJedisSentinel();

            Jedis redisClient = jedisSentinelPool.getResource();

        }
        
        if(appConf.getActiveRedisMode().equalsIgnoreCase(RedisAppConstants.REDIS_STANDALONE_MODE)) {
        
	        JedisPool jedisPool = redisConf.getJedisStandalone();
	
	        Jedis redisClient = jedisPool.getResource();
        
        }
    }
    
    
    public Person getObjectFromHashWithKey(String key) {

        Map<String, String> redisMap = redisClient.hgetAll(key);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.convertValue(redisMap, Person.class);

    }

    public String getSingleFieldFromHashWithKey(String key, String valueType) {

        return redisClient.hget(key, valueType);
    }

    public void updateObjectInHashWithKey(String key, Person person) {

        if (person == null)
            throw new IllegalArgumentException("[(updateObjectInHashWithKey)] Person object cannot be null");

        if (key == null)
            throw new IllegalArgumentException("[(updateObjectInHashWithKey)] Key cannot be null");

        Map<String, String> personObjectMap = convertPersonObjectToMapObject(person);

        redisClient.hmset(key, personObjectMap);
    }

    private Map<String, String> convertPersonObjectToMapObject(Person person) {

        ObjectMapper objectMapper = new ObjectMapper();
        
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper.convertValue(person, Map.class);
    }

    public void setNewObjectToHashWithKey(Person person) {

        if (person == null)
            throw new IllegalArgumentException("[(setNewObjectToHashWithKey)] Person object cannot be null");

        if (person.getPersonId() == null)
            throw new IllegalArgumentException("[(setNewObjectToHashWithKey)] Person id cannot be null");

        Map<String, String> personObjectMap = convertPersonObjectToMapObject(person);

        redisClient.hmset(person.getPersonId(), personObjectMap);

    }

    public void removeSingleValueFromHashWithKey(String key, String removedType) {

        redisClient.hdel(key, removedType);
    }

    public void removeObjectFromHashWithKey(String key) {

        redisClient.hdel(key, "personId", "personName", "city");
    }

    public List<Person> getAllObjects() {

        List<Person> personList = new ArrayList<>();

        Set<String> keys = redisClient.keys("*");
        if (!CollectionUtils.isEmpty(keys)) {
            for (String key : keys) {

                Map<String, String> redisObjectMap = redisClient.hgetAll(key);

                ObjectMapper mapper = new ObjectMapper();

                personList.add(mapper.convertValue(redisObjectMap, Person.class));
            }

            return personList;
        }

        return Collections.emptyList();
    }
    
    
}
