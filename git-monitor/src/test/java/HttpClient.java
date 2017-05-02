import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpClient {

	private static final String ENDPOINT = "http://localhost:8300/git/webhooks";
	
	public static void main(String[] args) throws IOException {
		OkHttpClient client = new OkHttpClient();
		RequestBody requestBody = new FormBody.Builder()
		        .add("message", "Your message")
		        .build();
		Request request = new Request.Builder().url(ENDPOINT).post(requestBody).build();
		Response response = client.newCall(request).execute();
		ResponseBody body = response.body();
		System.err.println(body.string());
	}
}
