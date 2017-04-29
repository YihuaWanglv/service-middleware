package com.iyihua.monitor.git.hanlder;

import com.iyihua.monitor.git.core.RedisDao;
import com.iyihua.monitor.git.entity.GitUpdateMessage;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class RedisMessageHandler extends AbstractHandler {

	public RedisMessageHandler(Vertx vertx) {
		super(vertx);
	}

	@Override
	public void handle(RoutingContext event) {
		System.out.println("RedisMessageHandler.handle");
		HttpServerResponse response = event.response();

		RedisDao redisDao = RedisDao.getInstance(vertx);
		GitUpdateMessage message = Json.decodeValue(event.getBodyAsString(), GitUpdateMessage.class);
		if (message == null) {
			sendError(400, response);
		} else {
			System.err.println(message.getKey());
			redisDao.publish("vertx", Json.encode(message));
			response.putHeader("content-type", "application/json").end("save ok");
		}
	}

	

}
