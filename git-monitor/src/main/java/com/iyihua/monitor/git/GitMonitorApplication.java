package com.iyihua.monitor.git;

import java.util.concurrent.TimeUnit;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.rx.java.ObservableFuture;
import io.vertx.rx.java.RxHelper;

public class GitMonitorApplication {

	public static void main(String[] args) {
		buildVertxServer();
	}
	
	private static DeploymentOptions initOptionsConfig() {
		DeploymentOptions options = new DeploymentOptions();
        final int port = 8300;
        options.setConfig(new JsonObject()
                .put("http.host", "localhost")
                .put("http.port", port));
        return options;
	}

	private static void buildVertxServer() {
		ObservableFuture<String> observableFuture = RxHelper.observableFuture();
		Vertx.vertx().deployVerticle(Server.class.getName(), initOptionsConfig(), observableFuture.toHandler());
		observableFuture.timeout(1, TimeUnit.MINUTES).toBlocking().first();
		System.out.println("**********************************");
		System.out.println("Vertx server started successfully");
		System.out.println("**********************************");
	}

}
