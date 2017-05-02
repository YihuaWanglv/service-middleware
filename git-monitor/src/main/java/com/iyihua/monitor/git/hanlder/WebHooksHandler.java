package com.iyihua.monitor.git.hanlder;

import com.iyihua.monitor.git.core.RedisDao;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class WebHooksHandler extends AbstractHandler {

	public WebHooksHandler(Vertx vertx) {
		super(vertx);
	}

	@Override
	public void handle(RoutingContext event) {
		HttpServerResponse response = event.response();
		String body = event.getBodyAsString();
		RedisDao redisDao = RedisDao.getInstance(vertx);
		if (body == null) {
			sendError(400, response);
		} else {
			System.err.println(body);
			redisDao.publish("vertx", body);
			response.putHeader("content-type", "application/json").end("save ok");
		}
	}

}
