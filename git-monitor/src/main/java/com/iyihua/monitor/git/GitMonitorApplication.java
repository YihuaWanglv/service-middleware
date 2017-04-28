package com.iyihua.monitor.git;

import com.iyihua.monitor.git.entity.GitUpdateMessage;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class GitMonitorApplication {
	
	

	public static void main(String[] args) {
		VertxOptions options = new VertxOptions();
		Vertx vertx = Vertx.vertx(options);
		Router router = Router.router(vertx);
		final RedisClient client = RedisClient.create(vertx,
		        new RedisOptions().setHost("127.0.0.1"));

		router.route().handler(BodyHandler.create());
		router.get("/rest/hello").handler(GitMonitorApplication::handleHello);
		router.post("/git/update").handler(GitMonitorApplication::handleGitUpdate);
		vertx.createHttpServer().requestHandler(router::accept).listen(8300);
	}

	private static void handleHello(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		response.putHeader("content-type", "application/json").end("Hello world");
	}

	private static void handleGitUpdate(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		GitUpdateMessage message = Json.decodeValue(routingContext.getBodyAsString(), GitUpdateMessage.class);
		if (message == null) {
			sendError(400, response);
		} else {
			System.err.println(message.getKey());
			response.putHeader("content-type", "application/json").end("save ok");
		}
	}

	private static void sendError(int statusCode, HttpServerResponse response) {
		response.setStatusCode(statusCode).end();
	}

}
