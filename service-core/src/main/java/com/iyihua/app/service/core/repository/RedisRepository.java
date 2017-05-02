package com.iyihua.app.service.core.repository;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class RedisRepository {

	private static RedisRepository redisRepository;

	private final RedisClient redisClient;

	private RedisRepository(final Vertx vertx) {
		final JsonObject redisConfig = getRedisConfig();
		redisClient = RedisClient.create(vertx,
				new RedisOptions().setHost(redisConfig.getString("host")).setPort(redisConfig.getInteger("port")));
	}

	public static RedisRepository getInstance(final Vertx vertx) {
		if (null == redisRepository) {
			redisRepository = new RedisRepository(vertx);
		}
		return redisRepository;
	}

	private JsonObject getRedisConfig() {
		String host = System.getenv("redis.host");
		String port = System.getenv("redis.port");
		if (null == host) {
			host = "localhost";
		}
		if (null == port) {
			port = "6379";
		}
		return new JsonObject().put("host", host).put("port", Integer.valueOf(port));
	}

	public void publish(String channel, String message) {
		redisClient.publish(channel, message, new Handler<AsyncResult<Long>>() {

			@Override
			public void handle(AsyncResult<Long> result) {
				System.out.println(result.toString());
			}
		});
	}
}
