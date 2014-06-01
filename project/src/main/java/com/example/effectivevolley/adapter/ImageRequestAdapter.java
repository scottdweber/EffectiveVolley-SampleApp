package com.example.effectivevolley.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.effectivevolley.R;
import com.example.effectivevolley.util.Ui;

public class ImageRequestAdapter extends BaseAdapter {

	private ArrayList<ImageItem> mItems = new ArrayList<ImageItem>();
	private Context mContext;
	private ImageLoader mImageLoader;

	public ImageRequestAdapter(Context context, ImageLoader loader) {
		mContext = context;
		mImageLoader = loader;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.row_image, parent, false);

			holder = new ViewHolder();
			holder.imageView = Ui.findView(convertView, R.id.image);
			holder.titleView = Ui.findView(convertView, R.id.title);

			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		ImageItem item = (ImageItem) getItem(position);
		holder.imageView.setImageUrl(item.imageUrl, mImageLoader);
		holder.titleView.setText(item.title);

		return convertView;
	}

	public void loadContent(JSONObject response) {
		mItems.clear();

		try {
			JSONArray data = response.getJSONArray("data");

			int count = data.length();
			for (int i = 0; i < count; i++) {
				JSONObject json = data.getJSONObject(i);
				ImageItem item = new ImageItem();
				item.title = json.optString("title", "");
				item.imageUrl = json.getString("link");
				mItems.add(item);
			}
		}
		catch (JSONException e) {
			Log.e("TAG", "error parsing JSON from imgur", e);
		}

		notifyDataSetChanged();
	}

	private static class ImageItem {
		private String imageUrl;
		private String title;
	}

	private static class ViewHolder {
		private NetworkImageView imageView;
		private TextView titleView;
	}
}
