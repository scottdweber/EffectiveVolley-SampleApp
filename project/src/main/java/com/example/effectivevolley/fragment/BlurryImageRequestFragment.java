package com.example.effectivevolley.fragment;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.effectivevolley.activity.MainActivity;
import com.example.effectivevolley.adapter.ImageRequestAdapter;
import com.example.effectivevolley.util.VolleyUtils;
import com.example.effectivevolley.volley.BlurryImageLoader;
import com.example.effectivevolley.volley.ImgurJsonRequest;

public class BlurryImageRequestFragment extends ListFragment {

	ImageLoader mImageLoader;
	ImageRequestAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mImageLoader = new BlurryImageLoader(VolleyUtils.getRequestQueue(), VolleyUtils.getMemoryCache(), getActivity());
		mAdapter = new ImageRequestAdapter(getActivity(), mImageLoader);

		Request request = new ImgurJsonRequest("https://api.imgur.com/3/gallery/r/EarthPorn", null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						mAdapter.loadContent(response);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getActivity(), "Error: " + error, Toast.LENGTH_SHORT).show();
					}
				}
		);

		request.setTag(this);
		VolleyUtils.getRequestQueue().add(request);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		setListAdapter(mAdapter);
	}

	@Override
	public void onStop() {
		VolleyUtils.getRequestQueue().cancelAll(this);
		super.onStop();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(3);
	}
}
