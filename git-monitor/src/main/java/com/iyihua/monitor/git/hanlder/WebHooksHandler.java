package com.iyihua.monitor.git.hanlder;

import com.google.gson.Gson;
import com.iyihua.app.service.core.entity.Repository;
import com.iyihua.monitor.git.core.RedisDao;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
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

			JsonObject json = new JsonObject(body);
			JsonObject repository = json.getJsonObject("repository");
			String fullName = repository.getString("full_name");
			String url = repository.getString("url");
			redisDao.publish("post", new Gson().toJson(Repository.builder().name(fullName).url(url).build()));
			response.putHeader("content-type", "application/json").end("save ok");
		}
	}

}
