package com.iyihua.monitor.git.core;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class RedisDao {

	private static RedisDao redisDao;

	private final RedisClient redisClient;

	private RedisDao(final Vertx vertx) {
		final JsonObject redisConfig = getRedisConfig();
		redisClient = RedisClient.create(vertx,
				new RedisOptions().setHost(redisConfig.getString("host")).setPort(redisConfig.getInteger("port")));
	}

	public static RedisDao getInstance(final Vertx vertx) {
		if (null == redisDao) {
			redisDao = new RedisDao(vertx);
		}
		return redisDao;
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
