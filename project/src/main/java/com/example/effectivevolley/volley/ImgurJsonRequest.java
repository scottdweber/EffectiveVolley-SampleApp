package com.example.effectivevolley.volley;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

public class ImgurJsonRequest extends JsonObjectRequest {

	private static final String CLIENT_ID = "<PUT_YOUR_IMGUR_API_CLIENT_ID_HERE>";

	public ImgurJsonRequest(int method, String url, JSONObject jsonRequest,
			Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
	}

	public ImgurJsonRequest(String url, JSONObject jsonRequest,
			Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener) {
		super(url, jsonRequest, listener, errorListener);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> headers = new HashMap<String, String>();

		headers.put("Authorization", "Client-ID " + CLIENT_ID);

		return headers;
	}
}
