package org.filewriter.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.filewriter.model.User;
import org.filewriter.repository.UserRepository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.*;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository, AutoCloseable{
    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;
    private final String USERS_KEY_PREFIX = "user:";
    private final String KEY = "id_key";

    public UserRepositoryImpl() {
        this.jedisPool = createJedisPool();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public User save(User user) {
        try (Jedis jedis = jedisPool.getResource()) {
            String userKey = USERS_KEY_PREFIX + user.getId().toString();
            String userJson = objectMapper.writeValueAsString(user);

            jedis.set(userKey, userJson);

            jedis.hset(KEY, user.getUsername(), user.getId().toString());

            return user;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing user to JSON", e);
        }
    }

    @Override
    public Optional<User> findById(UUID id) {
        try (Jedis jedis = jedisPool.getResource()) {
            String userKey = USERS_KEY_PREFIX + id.toString();
            String userJson = jedis.get(userKey);

            if (userJson != null) {
                User user = objectMapper.readValue(userJson, User.class);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing user from JSON", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Jedis jedis = jedisPool.getResource()) {
            String userIdStr = jedis.hget(KEY, username);
            if (userIdStr != null) {
                UUID userId = UUID.fromString(userIdStr);
                return findById(userId);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        try (Jedis jedis = jedisPool.getResource()) {
            Map<String, String> usernameToIdMap = jedis.hgetAll(KEY);

            return usernameToIdMap.values().stream()
                    .map(UUID::fromString)
                    .map(this::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public User update(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User and user ID cannot be null");
        }

        try (Jedis jedis = jedisPool.getResource()) {
            String existingUserJson = jedis.get(USERS_KEY_PREFIX + user.getId().toString());
            if (existingUserJson == null) {
                throw new RuntimeException("User not found with ID: " + user.getId());
            }

            User existingUser = objectMapper.readValue(existingUserJson, User.class);

            if (!existingUser.getUsername().equals(user.getUsername())) {

                jedis.hdel(KEY, existingUser.getUsername());

                jedis.hset(KEY, user.getUsername(), user.getId().toString());
            }

            String userJson = objectMapper.writeValueAsString(user);
            jedis.set(USERS_KEY_PREFIX + user.getId().toString(), userJson);

            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        try (Jedis jedis = jedisPool.getResource()) {
            Optional<User> user = findById(id);
            if (user.isPresent()) {
                String userKey = USERS_KEY_PREFIX + id.toString();

                jedis.del(userKey);

                jedis.hdel(KEY, user.get().getUsername());
            }
        }
    }

    @Override
    public void close() throws Exception {
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
        }
    }

    public void clear() {
        try (Jedis jedis = jedisPool.getResource()) {
            Set<String> userKeys = jedis.keys(USERS_KEY_PREFIX + "*");
            if (!userKeys.isEmpty()) {
                jedis.del(userKeys.toArray(new String[0]));
            }
            jedis.del(KEY);
        }
    }

    private JedisPool createJedisPool() {
        return createJedisPool("localhost", 6379, null);
    }

    private JedisPool createJedisPool(String host, int port, String password) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);

        if (password != null && !password.isEmpty()) {
            return new JedisPool(poolConfig, host, port, 2000, password);
        } else {
            return new JedisPool(poolConfig, host, port, 2000);
        }
    }
}
