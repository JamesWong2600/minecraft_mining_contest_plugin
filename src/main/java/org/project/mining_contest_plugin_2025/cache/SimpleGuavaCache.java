package org.project.mining_contest_plugin_2025.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class SimpleGuavaCache {
    //private static SimpleGuavaCache instance;
    private static final ConcurrentHashMap<String, SimpleGuavaCache> instances = new ConcurrentHashMap<>();
    private Cache<String, String> cache;
    private long expirationTimeInSeconds;
    private ConcurrentHashMap<String, Long> insertionTimes;

    public SimpleGuavaCache(int timeoutSeconds) {
        this.expirationTimeInSeconds = timeoutSeconds;

        this.insertionTimes = new ConcurrentHashMap<>();

        cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                 .expireAfterWrite(timeoutSeconds, TimeUnit.SECONDS)
                .recordStats()  // Enable statistics
                .build();
    }

    public static SimpleGuavaCache getInstance(String name, int timeoutSeconds) {
        return instances.computeIfAbsent(name, k -> new SimpleGuavaCache(timeoutSeconds));
    }
    //public static SimpleGuavaCache getInstance(int timeoutSeconds) {
      //  if (instance == null) {
      //      instance = new SimpleGuavaCache(timeoutSeconds);
      //  }
     //   return instance;
   // }

    public void put(String key, String value) {
        cache.put(key, value);
        insertionTimes.put(key, TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }

    public String get(String key) {
        String value = cache.getIfPresent(key);
        if (value == null) {
            System.out.println("nopevalue");
            return "nopevalue";
        }
        System.out.println(value);
        return value;
    }

    public long getTimeToExpire(String key) {
        Long insertionTimeInSeconds = insertionTimes.get(key);
        if (insertionTimeInSeconds == null || Objects.equals(cache.getIfPresent(key), "null")) {
            return -1; // Key doesn't exist or already expired
        }

        long currentTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        long elapsedSeconds = currentTimeInSeconds - insertionTimeInSeconds;
        long remainingSeconds = expirationTimeInSeconds - elapsedSeconds;

        return Math.max(0, remainingSeconds);

    }
}