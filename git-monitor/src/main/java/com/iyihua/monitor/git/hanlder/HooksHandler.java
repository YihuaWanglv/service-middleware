package com.iyihua.monitor.git.hanlder;

import java.io.IOException;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HooksHandler extends AbstractHandler {

	// https://github.com/YihuaWanglv/github-trending
	private static final String ENDPOINT = "https://github.com/repos/YihuaWanglv/github-trending/hooks";

	public HooksHandler(Vertx vertx) {
		super(vertx);
	}

	@Override
	public void handle(RoutingContext event) {
		HttpServerResponse res = event.response();
		try {
			OkHttpClient client = new OkHttpClient();

			// Create request for remote resource.
			Request request = new Request.Builder().url(ENDPOINT).build();

			// Execute the request and retrieve the response.
			Response response;
			response = client.newCall(request).execute();
			// Deserialize HTTP response to concrete type.
			ResponseBody body = response.body();
			res.putHeader("content-type", "application/json").end(body.string());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sendError(400, res);
		}

	}

}
