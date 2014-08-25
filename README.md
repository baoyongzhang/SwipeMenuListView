SwipeMenuListView
=================

a SwipeMenu of ListView.

# Demo
<p>
   <img src="https://raw.githubusercontent.com/baoyongzhang/SwipeMenuListView/master/demo.gif" width="320" alt="Screenshot"/>
</p>

# Usage

### Step 1

```xml
<com.baoyz.swipemenulistview.SwipeMenuListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

### Step 2

```java
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
```

### Step 3

```java
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
```
