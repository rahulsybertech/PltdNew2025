package com.syber.ssspltd.Utils;

import androidx.annotation.Nullable;
import com.android.volley.toolbox.HurlStack;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.BridgeInterceptor;

public class OkHttp3Stack extends HurlStack {

    private final OkHttpClient client;

    public OkHttp3Stack(OkHttpClient client) {
        this.client = client;
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection(); // fallback - won't use OkHttp here
    }
}
