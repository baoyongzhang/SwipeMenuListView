package com.baoyz.swipemenulistviewsample;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;

public class MainActivity extends Activity {

	private List<ApplicationInfo> mAppList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mAppList = getPackageManager().getInstalledApplications(0);

		SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.listView);
		AppAdapter adapter = new AppAdapter();
		listView.setAdapter(adapter);

		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						getApplicationContext());
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				openItem.setWidth(dp2px(90));
				openItem.setTitle("Open");
				openItem.setTitleSize(18);
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				deleteItem.setWidth(dp2px(90));
				deleteItem.setTitleSize(18);
				deleteItem.setTitleColor(Color.WHITE);
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		listView.setMenuCreator(creator);

		// step 2. listen click event
		listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				ApplicationInfo item = mAppList.get(position);
				switch (index) {
				case 0:
					// open
					open(item);
					break;
				case 1:
					// delete
					delete(item);
					break;
				}
			}
		});

	}

	private void delete(ApplicationInfo item) {
		try {
			Intent intent = new Intent(Intent.ACTION_DELETE);
			intent.setData(Uri.fromParts("package", item.packageName, null));
			startActivity(intent);
		} catch (Exception e) {
		}
	}

	private void open(ApplicationInfo item) {
		try {
			Intent intent = new Intent();
			intent.setPackage(item.packageName);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class AppAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mAppList.size();
		}

		@Override
		public ApplicationInfo getItem(int position) {
			return mAppList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.item_list_app, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			ApplicationInfo item = getItem(position);
			holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
			holder.tv_name.setText(item.loadLabel(getPackageManager()));
			return convertView;
		}

		class ViewHolder {
			ImageView iv_icon;
			TextView tv_name;

			public ViewHolder(View view) {
				iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				tv_name = (TextView) view.findViewById(R.id.tv_name);
				view.setTag(this);
			}
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
}
