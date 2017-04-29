package com.iyihua.monitor.git;

import com.iyihua.monitor.git.hanlder.HooksHandler;
import com.iyihua.monitor.git.hanlder.RedisMessageHandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class Server extends AbstractVerticle {

	@Override
	public void start() {

		Router router = Router.router(vertx);

		final RedisMessageHandler redisMessageHanlder = new RedisMessageHandler(vertx);

		router.route().handler(BodyHandler.create());
		router.get("/rest/hello").handler(Server::handleHello);
		router.post("/git/update").handler(redisMessageHanlder);
		router.get("/git/hooks").handler(new HooksHandler(vertx));
		JsonObject conf = config();

		vertx.createHttpServer().requestHandler(router::accept).listen(conf.getInteger("http.port", 8300),
				conf.getString("http.host", "localhost"));
	}

	private static void handleHello(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		response.putHeader("content-type", "application/json").end("Hello world");
	}

}
