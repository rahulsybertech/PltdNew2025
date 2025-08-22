package com.syber.ssspltd.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
public class VolleySingleton {

    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private VolleySingleton(Context context) {
        mCtx = context.getApplicationContext(); // Use app context to avoid leaks

        mImageLoader = new ImageLoader(
                getRequestQueue(),
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                }
        );
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
/*    public RequestQueue getRequestQueue() {
        if (mRequestQueue== null) {
            Log.d("VolleySingleton", "Initializing RequestQueue with Chucker");

            // Step 1: Create ChuckerCollector
            ChuckerCollector chuckerCollector = new ChuckerCollector(
                    mCtx,
                    true,
                    RetentionManager.Period.ONE_HOUR
            );


            // Step 2: Create ChuckerInterceptor
            ChuckerInterceptor chuckerInterceptor = new ChuckerInterceptor.Builder(mCtx)
                    .collector(chuckerCollector)
                    .maxContentLength(250_000L)
                    .alwaysReadResponseBody(true)
                    .addBodyDecoder(new ProtoDecoder())
                    .createShortcut(true)
                    .build();

            // Step 3: Create OkHttpClient with ChuckerInterceptor
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(chuckerInterceptor)
                    .build();

            // Step 4: Set up Volley with OkHttp3Stack
            mRequestQueue = Volley.newRequestQueue(mCtx, new OkHttp3Stack(okHttpClient));
        } else {
            Log.d("VolleySingleton", "RequestQueue already initialized");
        }

        return mRequestQueue;
    }*/

    public <T> void addToRequestQueue(Request<T> req) {
        Log.d("VolleySingleton", "Adding request to queue: " + req.getUrl());
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
