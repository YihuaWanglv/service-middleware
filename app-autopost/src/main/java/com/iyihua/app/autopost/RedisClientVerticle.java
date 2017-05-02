package com.iyihua.app.autopost;

import com.google.gson.Gson;
import com.iyihua.app.service.core.entity.Repository;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class RedisClientVerticle extends AbstractVerticle {

	EventBus eb;

	RedisClient redis;

	@Override
	public void start() throws Exception {
		String host = Vertx.currentContext().config().getString("redis.host");
		if (host == null) {
			host = "127.0.0.1";
		}
		redis = RedisClient.create(vertx, new RedisOptions().setHost(host));

		eb = vertx.eventBus();

		vertx.eventBus().<JsonObject>consumer("io.vertx.redis.post", received -> {
			JsonObject value = received.body().getJsonObject("value");
			System.out.println("consumer:" + value.encode());
			String message = value.getString("message");
			Repository repo = new Gson().fromJson(message, Repository.class);
			String name = repo.getName();
			if ("YihuaWanglv/dev-lib-resources-collectting".equalsIgnoreCase(name)) {
				System.out.println("going to do process of post to wenxin.");
			}
		});

		redis.subscribe("post", r -> {
			System.out.println("subscribe:" + r.result());
		});
	}
}
