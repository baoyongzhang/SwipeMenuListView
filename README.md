SwipeMenuListView
=================
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SwipeMenuListView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/912)

A SwipeMenu of ListView.

# Demo
<p>
   <img src="https://raw.githubusercontent.com/baoyongzhang/SwipeMenuListView/master/demo.gif" width="320" alt="Screenshot"/>
</p>

# Usage

### Step 1

* add SwipeMenuListView in layout xml

```xml
<com.baoyz.swipemenulistview.SwipeMenuListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

### Step 2

* create a SwipeMenuCreator to add items.

```java
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
		openItem.setWidth(dp2px(90));
		// set item title
		openItem.setTitle("Open");
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
		deleteItem.setWidth(dp2px(90));
		// set a icon
		deleteItem.setIcon(R.drawable.ic_delete);
		// add to menu
		menu.addMenuItem(deleteItem);
	}
};

// set creator
listView.setMenuCreator(creator);
```

### Step 3

* listener item click event

```java
listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	@Override
	public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
		switch (index) {
		case 0:
			// open
			break;
		case 1:
			// delete
			break;
		}
		// false : close the menu; true : not close the menu
		return false;
	}
});
```

### Create Different Menu

* Use the ViewType of adapter

```java
	class AppAdapter extends BaseAdapter {

		...
		
		@Override
		public int getViewTypeCount() {
			// menu type count
			return 2;
		}
		
		@Override
		public int getItemViewType(int position) {
			// current menu type
			return type;
		}

		...
	}
```

* Create different menus depending on the view type

```java
	SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// Create different menus depending on the view type
				switch (menu.getViewType()) {
				case 0:
					// create menu of type 0
					break;
				case 1:
					// create menu of type 1
					break;
				...
				}
			}

		};
```

* Demo

<p>

<img src="https://raw.githubusercontent.com/baoyongzhang/SwipeMenuListView/master/demo3.gif" width="320" alt="Screenshot"/>
</p>

### Other

* OnSwipeListener

```java
listView.setOnSwipeListener(new OnSwipeListener() {
			
	@Override
	public void onSwipeStart(int position) {
		// swipe start
	}
	
	@Override
	public void onSwipeEnd(int position) {
		// swipe end
	}
});
```

* open menu method for SwipeMenuListView

```java
listView.smoothOpenMenu(position);
```

* Open/Close Animation Interpolator

```java
// Close Interpolator
listView.setCloseInterpolator(new BounceInterpolator());
// Open Interpolator
listView.setOpenInterpolator(...);
```  
  

<p>
   <img src="demo2.gif" width="320" alt="Screenshot"/>
</p>
