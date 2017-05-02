package com.iyihua.app.autopost;

import com.iyihua.app.autopost.handler.PostHandler;

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

		final PostHandler postHandler = new PostHandler(vertx);

		router.route().handler(BodyHandler.create());
		router.get("").handler(Server::handleHello);
		router.post("/post").handler(postHandler);
		JsonObject conf = config();

		vertx.createHttpServer().requestHandler(router::accept).listen(conf.getInteger("http.port", 8301),
				conf.getString("http.host", "localhost"));
	}

	private static void handleHello(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		response.putHeader("content-type", "application/json").end("Hello autopost");
	}

}
