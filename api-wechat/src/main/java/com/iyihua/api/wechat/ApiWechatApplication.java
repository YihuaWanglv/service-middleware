package com.iyihua.api.wechat;


import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class ApiWechatApplication {
	
	

	public static void main(String[] args) {
		VertxOptions options = new VertxOptions();
		Vertx vertx = Vertx.vertx(options);
		Router router = Router.router(vertx);

		router.route().handler(BodyHandler.create());
		router.route("/").handler(ApiWechatApplication::handleHello);
		vertx.createHttpServer().requestHandler(router::accept).listen(8302);
	}

	private static void handleHello(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		HttpServerRequest request = routingContext.request();
		String token = request.getParam("token");
		System.err.println(token);
		String body = routingContext.getBodyAsString();
		System.out.println(body);
		response.putHeader("content-type", "application/json").end("Hello wechat api");
	}
	

	private static void sendError(int statusCode, HttpServerResponse response) {
		response.setStatusCode(statusCode).end();
	}

}
