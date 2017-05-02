package com.iyihua.app.autopost.handler;

import com.iyihua.app.autopost.entity.Post;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class PostHandler extends AbstractHandler {

	public PostHandler(Vertx vertx) {
		super(vertx);
	}

	@Override
	public void handle(RoutingContext event) {
		System.out.println("RedisMessageHandler.handle");
		HttpServerResponse response = event.response();

		Post post = Json.decodeValue(event.getBodyAsString(), Post.class);
		if (post == null) {
			sendError(400, response);
		} else {
			System.err.println(post.toString());
			response.putHeader("content-type", "application/json").end("save ok");
		}
	}

	

}
