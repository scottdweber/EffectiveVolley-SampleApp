package com.example.effectivevolley.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.effectivevolley.R;
import com.example.effectivevolley.activity.MainActivity;
import com.example.effectivevolley.util.Ui;
import com.example.effectivevolley.util.VolleyUtils;

public class BasicRequestFragment extends Fragment {

	private static final String URL_BASE = "http://maps.googleapis.com/maps/api/elevation/json";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_basic_request, container, false);

		final EditText latitude = Ui.findView(v, R.id.latitude);
		final EditText longitude = Ui.findView(v, R.id.longitude);
		final EditText elevation = Ui.findView(v, R.id.elevation);

		Ui.findView(v, R.id.get_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = URL_BASE + "?locations=" + latitude.getText() + "," + longitude.getText() + "&sensor=false";
				Request elevationRequest = new JsonObjectRequest(url, null,
						new Response.Listener<JSONObject>() {
							public void onResponse(JSONObject response) {
								try {
									elevation.setText(
											response.getJSONArray("results").getJSONObject(0).getString("elevation"));
								}
								catch (JSONException e) {
									elevation.setText("error parsing JSON");
								}
							}
						},
						new Response.ErrorListener() {
							public void onErrorResponse(VolleyError error) {
								elevation.setText("volley error: " + error);
							}
						}
				);

				elevationRequest.setTag(BasicRequestFragment.this);
				VolleyUtils.getRequestQueue().add(elevationRequest);
			}
		});

		return v;
	}

	@Override
	public void onStop() {
		VolleyUtils.getRequestQueue().cancelAll(this);
		super.onStop();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(1);
	}

}
