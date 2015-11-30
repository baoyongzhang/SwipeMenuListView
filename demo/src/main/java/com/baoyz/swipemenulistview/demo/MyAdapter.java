package com.baoyz.swipemenulistview.demo;

import java.util.List;

import com.baoyz.swipemenulistview.BaseSwipListAdapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.example.swipelistview.R;
import com.example.swipelistview.R.id;
import com.example.swipelistview.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseSwipListAdapter {
	public List<User> list = null;
	public Context context;

	public MyAdapter(Context cxt, List<User> values) {
		this.context = cxt;
		list = values;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item,
					null);
			holder=new ViewHolder();
			holder.tx = (TextView) convertView.findViewById(R.id.tip);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tx.setText(list.get(position).getName());
		return convertView;
	}
   
	@Override
	public void SwipeMenuMaker(SwipeMenu menu, int position) {
		// TODO Auto-generated method stub
		if(list.get(position).getName().equals("2")){
			SwipeMenuItem item=menu.getMenuItem(0);
			item.setTitle("已收藏");
			menu.updateMenuItem(item, 0);
		}else if(list.get(position).getName().equals("1")){
			SwipeMenuItem item=menu.getMenuItem(0);
			item.setTitle("收藏"); 
			menu.updateMenuItem(item, 0);
		}
	}

	public class ViewHolder {
		TextView tx;
	}
}
