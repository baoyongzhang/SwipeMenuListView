package com.baoyz.swipemenulistview.demo;

import java.util.ArrayList;
import java.util.List;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.example.swipelistview.R;
import com.example.swipelistview.R.id;
import com.example.swipelistview.R.layout;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DynamicActivity extends Activity {
	public SwipeMenuListView listview;
	List<User> values = new ArrayList<User>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		listview = (SwipeMenuListView) this.findViewById(R.id.listView);
		for (int i = 0; i < 6; i++) {
			User user=new User();
			user.setName("1");
			values.add(user);
		}
		for (int j = 0; j < 3; j++) {
			User user=new User();
			user.setName("2");
			values.add(user);
		}
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				// set item width
				openItem.setWidth(90);
				// set item title
				openItem.setTitle("收藏");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);
				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(90);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		listview.setMenuCreator(creator);
		final MyAdapter adapter = new MyAdapter(this, values);
		listview.setAdapter(adapter);
		listview.setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu,
					int index) {
				// TODO Auto-generated method stub
				String name= ((User)adapter.getItem(position)).getName();
				if(name.equals("1")){
					((User)adapter.getItem(position)).setName("2");
				}
				if(name.equals("2")){
					((User)adapter.getItem(position)).setName("1");
				}
				adapter.notifyDataSetChanged();
				return false;
			}
			
		});

	}
}
