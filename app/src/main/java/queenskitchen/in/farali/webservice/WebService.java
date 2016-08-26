package queenskitchen.in.farali.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

public class WebService {
    public static OkHttpClient client;
    private static String timeOutMessage = "Connection time out";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static boolean isNetworkAvailable(final Context context) {
        boolean isNetAvailable = false;
        if (context != null) {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                final NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null) isNetAvailable = true;
            }
        }
        return isNetAvailable;
    }

	/*public static boolean isNetworkAvailable(final Context context) {
        boolean isNetAvailable = false;
		if (context != null) {
			final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (mConnectivityManager != null) {
				boolean mobileNetwork = false;
				boolean wifiNetwork = false;

				boolean mobileNetworkConnecetd = false;
				boolean wifiNetworkConnecetd = false;

				final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

				if (mobileInfo != null) {
					mobileNetwork = mobileInfo.isAvailable();
				}

				if (wifiInfo != null) {
					wifiNetwork = wifiInfo.isAvailable();
				}

				if (wifiNetwork || mobileNetwork) {
					if (mobileInfo != null)
						mobileNetworkConnecetd = mobileInfo.isConnectedOrConnecting();
					wifiNetworkConnecetd = wifiInfo.isConnectedOrConnecting();
				}

				isNetAvailable = (mobileNetworkConnecetd || wifiNetworkConnecetd);
			}
		}

		return isNetAvailable;
	}*/

    public static String callServiceHttpPost(final String url, final RequestBody requestBody) {

        String responseString = "";

        try {
            final OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);

            final Request.Builder builder = new Request.Builder();
            builder.url(url);
            builder.post(requestBody);
            final Response response = okHttpClient.newCall(builder.build()).execute();
            responseString = response.body().string();

//            Log.e(WSUtil.class.getSimpleName(), String.format("Response String : %s", responseString));
        } catch (Exception e) {
            e.printStackTrace();
            responseString = WSConstants.getNetworkError();
        }

        return responseString;

    }

    public static String callServiceHttpPostBulk(String url, String json) {
        try {
            final OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String getMethod(final String url) {
        String response = "";
        if (client == null) {
            client = new OkHttpClient();
            client.setConnectTimeout(30, TimeUnit.SECONDS);
            client.setReadTimeout(30, TimeUnit.SECONDS);

			/*final CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			client.setCookieHandler(cookieManager);*/
        }
        try {
            Log.e("URL", url);


            final Request request = new Request.Builder()
                    .url(url)
                    .build();
            final Response okResponse = client.newCall(request).execute();
            response = okResponse.body().string();
            Log.e("Response", response);
            return response;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            String myString = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", 0);
                jsonObject.put("message", timeOutMessage);
                myString = jsonObject.toString();

            } catch (Exception t) {
            }
            return myString;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
