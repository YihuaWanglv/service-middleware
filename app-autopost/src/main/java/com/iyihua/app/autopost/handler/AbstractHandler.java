package com.iyihua.app.autopost.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public abstract class AbstractHandler implements Handler<RoutingContext> {

	protected Vertx vertx;

	protected AbstractHandler(Vertx vertx) {
		this.vertx = vertx;
	}

	protected void sendError(int statusCode, HttpServerResponse response) {
		response.setStatusCode(statusCode).end();
	}
}
