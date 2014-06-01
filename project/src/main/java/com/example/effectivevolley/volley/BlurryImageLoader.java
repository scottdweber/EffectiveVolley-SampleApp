package com.example.effectivevolley.volley;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;

public class BlurryImageLoader extends ImageLoader {

	private Context mContext;

	public BlurryImageLoader(RequestQueue queue, ImageCache imageCache) {
		super(queue, imageCache);
	}

	public BlurryImageLoader(RequestQueue queue, ImageCache imageCache, Context context) {
		super(queue, imageCache);
		mContext = context;
	}

	@Override
	protected ImageRequest getImageRequest(String requestUrl, Response.Listener<Bitmap> listener, int maxWidth,
			int maxHeight, Response.ErrorListener errorListener) {
		return new BlurryImageRequest(requestUrl, listener, maxWidth, maxHeight, Bitmap.Config.ARGB_8888, errorListener, mContext);
	}
}
