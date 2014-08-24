package com.baoyz.swipemenulistview;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuView.OnItemClickListener;

/**
 * 
 * @author baoyz
 * @date 2014-8-24
 * 
 */
public class SwipeMenuAdapter extends BaseAdapter implements OnItemClickListener {

	private ListAdapter mAdapter;
	private Context mContext;
	private OnMenuItemClickListener onMenuItemClickListener;

	public SwipeMenuAdapter(Context context, ListAdapter adapter) {
		mAdapter = adapter;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mAdapter.getCount();
	}

	@Override
	public Object getItem(int position) {
		return mAdapter.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return mAdapter.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SwipeMenuLayout layout = null;
		if (convertView == null) {
			View contentView = mAdapter.getView(position, convertView, parent);
			SwipeMenu menu = new SwipeMenu(mContext);
			createMenu(menu);
			SwipeMenuView menuView = new SwipeMenuView(menu,
					(SwipeMenuListView) parent);
			menuView.setOnItemClickListener(this);
			layout = new SwipeMenuLayout(contentView, menuView);
			layout.setPosition(position);
		} else {
			layout = (SwipeMenuLayout) convertView;
			layout.closeMenu();
			layout.setPosition(position);
			View view = mAdapter.getView(position, layout.getContentView(),
					parent);
		}
		return layout;
	}

	public void createMenu(SwipeMenu menu) {
		// Test Code
		SwipeMenuItem item = new SwipeMenuItem(mContext);
		item.setTitle("Item 1");
		item.setBackground(new ColorDrawable(Color.GRAY));
		item.setWidth(300);
		menu.addMenuItem(item);

		item = new SwipeMenuItem(mContext);
		item.setTitle("Item 2");
		item.setBackground(new ColorDrawable(Color.RED));
		item.setWidth(300);
		menu.addMenuItem(item);
	}

	@Override
	public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
		if (onMenuItemClickListener != null) {
			onMenuItemClickListener.onMenuItemClick(view.getPosition(), menu,
					index);
		}
	}

	public void setOnMenuItemClickListener(
			OnMenuItemClickListener onMenuItemClickListener) {
		this.onMenuItemClickListener = onMenuItemClickListener;
	}

}
