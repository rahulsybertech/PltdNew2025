package com.syber.ssspltd.Utils;

import android.util.Base64;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageHelper {

    public interface Base64Callback {
        void onResult(String base64);
    }

    public static void imageUrlToBase64(String imageUrl, Base64Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(imageUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onResult(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    byte[] imageBytes = response.body().bytes();
                    String base64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
                    callback.onResult(base64);
                } else {
                    callback.onResult(null);
                }
            }
        });
    }
}
