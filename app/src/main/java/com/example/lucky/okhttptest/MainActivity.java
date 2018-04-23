package com.example.lucky.okhttptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnGet;
    private Button btnPost;
    private Button btnCookies;
    private TextView tvTitle;

    OkHttpClient client = new OkHttpClient();
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                    cookieStore.put(httpUrl.host(), list);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cookies = cookieStore.get(httpUrl.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .build();
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvTitle = findViewById(R.id.tvTitle);
        btnGet = findViewById(R.id.btnGet);
        btnCookies = findViewById(R.id.btnCookies);
        btnPost = findViewById(R.id.btnPost);

        btnCookies.setOnClickListener(v -> {


            String url = "http://10.0.2.2/home/Show?username=a&pwd=b";
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = okHttpClient.newCall(request);


            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, "onResponse: ");
                    runOnUiThread(() -> {
                        try {
                            tvTitle.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });

        });


        btnGet.setOnClickListener(v -> {
            String url = "http://10.0.2.2/home/Show?username=a&pwd=b";
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, "onResponse: ");
                    runOnUiThread(() -> {
                        try {
                            tvTitle.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });

        });


        btnPost.setOnClickListener(v -> {
            String url = "http://10.0.2.2/home/Show2";

//key value传递
//            RequestBody formBody = new FormBody.Builder()
//                    .add("username", "c1")
//                    .add("pwd", "d2")
//                    .build();

//            RequestBody requestBody = new MultipartBuilder()
//                    .type(MultipartBuilder.FORM)
//                    .addPart(
//                            Headers.of("Content-Disposition", "form-data; name=\"title\""),
//                            RequestBody.create(null, "Square Logo"))
//                    .addPart(
//                            Headers.of("Content-Disposition", "form-data; name=\"image\""),
//                            RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
//                    .build();

            //json格式的数据传递
            Map<String, String> map=new HashMap<>();
            map.put("username", "ddddeeee");
            map.put("pwd", "ppppoooo");
            Gson mGson = new Gson();
            String params = mGson.toJson(map);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");


//使用拼接的方式 提交表单形式
//            MediaType JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
//            String params = "UserName=json格式1&PWD=json格式2";
            RequestBody body = RequestBody.create(JSON, params);

//            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),"{\"UserName\":\"json格式1\",\"PWD\":\"json格式2\"");


            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("au","ok")
                    .build();


            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, "onResponse: ");
                    runOnUiThread(() -> {
                        try {
                            tvTitle.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });

        });
    }
}
