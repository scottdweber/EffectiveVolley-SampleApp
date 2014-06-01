package com.example.effectivevolley;

import android.app.Application;

import com.example.effectivevolley.util.VolleyUtils;

public class EffectiveVolleyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		VolleyUtils.init(this);
	}
}
