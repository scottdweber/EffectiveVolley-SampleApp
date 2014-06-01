package com.example.effectivevolley.volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

public class BlurryImageRequest extends ImageRequest {

	private static final String PROCESSED_HEADER = "X-EffectiveVolley-Image-Processed";

	private static final Object sDecodeLock = new Object();

	private Context mContext;

	public BlurryImageRequest(String url, Response.Listener<Bitmap> listener, int maxWidth, int maxHeight,
			Bitmap.Config decodeConfig,	Response.ErrorListener errorListener) {
		super(url, listener, maxWidth, maxHeight, decodeConfig, errorListener);
	}

	public BlurryImageRequest(String url, Response.Listener<Bitmap> listener, int maxWidth, int maxHeight,
			Bitmap.Config decodeConfig,	Response.ErrorListener errorListener, Context context) {
		super(url, listener, maxWidth, maxHeight, decodeConfig, errorListener);
		mContext = context;
	}

	@Override
	protected Response<Bitmap> parseNetworkResponse(NetworkResponse response) {
		Response<Bitmap> bitmapResponse = super.parseNetworkResponse(response);

		if (bitmapResponse.isSuccess() && !bitmapResponse.cacheEntry.responseHeaders.containsKey(PROCESSED_HEADER)) {
			synchronized (sDecodeLock) {
				Bitmap blurredBitmap = Bitmap.createBitmap(bitmapResponse.result);

				RenderScript rs = RenderScript.create(mContext);

				Allocation inputAllocation = Allocation.createFromBitmap(rs, bitmapResponse.result);
				Allocation outputAllocation = Allocation.createTyped(rs, inputAllocation.getType());

				ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
				blur.setInput(inputAllocation);
				blur.setRadius(25);
				blur.forEach(outputAllocation);

				outputAllocation.copyTo(blurredBitmap);

				bitmapResponse.result.recycle();

				HashMap<String, String> headers = new HashMap<String, String>(bitmapResponse.cacheEntry.responseHeaders);
				headers.put(PROCESSED_HEADER, "true");
				bitmapResponse.cacheEntry.responseHeaders = headers;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				blurredBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
				bitmapResponse.cacheEntry.data = baos.toByteArray();
				bitmapResponse = Response.success(blurredBitmap, bitmapResponse.cacheEntry);
			}
		}

		return bitmapResponse;
	}
}
