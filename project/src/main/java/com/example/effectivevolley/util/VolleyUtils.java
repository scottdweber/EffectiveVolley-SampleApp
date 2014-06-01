package com.example.effectivevolley.util;

import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.effectivevolley.volley.BitmapMemCache;

public class VolleyUtils {

	private static RequestQueue mRequestQueue;
	private static BitmapMemCache mMemoryCache;

	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);

		ActivityManager mgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		mMemoryCache = new BitmapMemCache(mgr.getMemoryClass() / 4 * 1024 * 1024);
	}

	public static RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			throw new IllegalArgumentException("mRequestQueue is null, did you forget to call init?");
		}
		return mRequestQueue;
	}

	public static BitmapMemCache getMemoryCache() {
		if (mMemoryCache == null) {
			throw new IllegalArgumentException("mMemoryCache is null, did you forget to call init?");
		}
		return mMemoryCache;
	}
}
