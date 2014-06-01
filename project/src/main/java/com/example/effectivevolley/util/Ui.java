package com.example.effectivevolley.util;

import android.view.View;

public class Ui {

	public static <T extends View> T findView(View v, int id) {
		return (T) v.findViewById(id);
	}
}
