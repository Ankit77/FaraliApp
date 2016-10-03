package queenskitchen.in.farali.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;


import org.apache.commons.lang.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import queenskitchen.in.farali.R;
import queenskitchen.in.farali.webservice.WSConstants;

/**
 * Created by indianic on 29/08/16.
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView tvtest = (TextView) findViewById(R.id.textView);
        String unicode = "\u0aa2\u0acb\u0a82\u0ab8\u0abe";
        String Title = StringEscapeUtils.unescapeJava(unicode);
        tvtest.setText(Title);
        tvtest.setMovementMethod(LinkMovementMethod.getInstance());
//        new AsyncPhoto().execute();
    }

//    public MultipartBody uploadRequestBody() {
//
//        MediaType MEDIA_TYPE = MediaType.parse("image/jpg"); // e.g. "image/png"
//        return new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("event_date", "2006-10-25 14:30:59")
//                .addFormDataPart("device", "1")
//                .addFormDataPart("file", "test1.jpg", RequestBody.create(MEDIA_TYPE, new File(Environment.getExternalStorageDirectory() + "/test1.jpg")))
//                .build();
//    }
//
////    private RequestBody generateUpdateProfileRequest() {
////
////        String FilePath = Environment.getExternalStorageDirectory() + "/test1.jpg";
////        final WSConstants wsConstants = new WSConstants();
////        final MultipartBuilder multipartBuilder = new MultipartBuilder();
////        multipartBuilder.type(MultipartBuilder.FORM);
////
////        multipartBuilder.addFormDataPart("event_date", "2006-10-25 14:30:59");
////        multipartBuilder.addFormDataPart("device", "1");
////
////        final File fileAvatarImage = new File(FilePath);
////
////        multipartBuilder.addFormDataPart("file", fileAvatarImage.getName(), RequestBody.create(MediaType.parse("image/png"), fileAvatarImage));
////
////        return multipartBuilder.build();
////    }
//
//    public static String POST(OkHttpClient client, HttpUrl url, RequestBody body) throws IOException {
//        Request request = new Request.Builder()
//                .url("http://volanze.com/api/screenshotlogs/")
//                .post(body)
//                .build();
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }
//
//
//    //    public String callServiceHttpPostBulk() {
////        try {
////            String FilePath = Environment.getExternalStorageDirectory() + "/test1.jpg";
////            final File fileAvatarImage = new File(FilePath);
////
////            final OkHttpClient okHttpClient = new OkHttpClient();
////            okHttpClient.setConnectTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
////            okHttpClient.setReadTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
////            RequestBody requestBody = generateUpdateProfileRequest();
////            Request request = new Request.Builder()
////                    .url("http://volanze.com/api/screenshotlogs/").
////            .header("Content-Length", "" + requestBody.contentLength())
////                    .post(generateUpdateProfileRequest())
////                    .build();
////            Response response = okHttpClient.newCall(request).execute();
////            return response.body().string();
////        } catch (Exception e) {
////            e.printStackTrace();
////            return "";
////        }
////
////    }
//    public static HttpUrl buildURL() {
//        return new HttpUrl.Builder()
//                .scheme("http") //http
//                .host("volanze.com")
//                .addPathSegment("api/screenshotlogs/")//adds "/pathSegment" at the end of hostname
//                .build();
//        /**
//         * The return URL:
//         *  https://www.somehostname.com/pathSegment?param1=value1&encodedName=encodedValue
//         */
//    }
//
//    private class AsyncPhoto extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                POST(new OkHttpClient(), buildURL(), uploadRequestBody());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }
}
