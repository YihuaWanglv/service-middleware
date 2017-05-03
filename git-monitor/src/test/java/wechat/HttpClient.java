package wechat;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.vertx.core.json.JsonObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final String ENDPOINT = "http://localhost:8302/wechat/message?content=9999";

	public static void main(String[] args) throws IOException {
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> repo = new HashMap<>();
		repo.put("id", 60496099);
		repo.put("full_name", "dev-lib-resources-collectting");
		repo.put("full_name", "YihuaWanglv/dev-lib-resources-collectting");
		repo.put("url", "https://github.com/YihuaWanglv/dev-lib-resources-collectting");
		params.put("repository", repo);
		params.put("ref", "refs/heads/master");
		JsonObject json = new JsonObject(params);
		
		System.err.println(post(ENDPOINT, json.encode()));
		
	}

	private static String post(String url, String json) throws IOException {
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
}
