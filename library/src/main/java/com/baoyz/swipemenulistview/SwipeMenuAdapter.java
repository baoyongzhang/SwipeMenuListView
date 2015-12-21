package com.baoyz.swipemenulistview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import com.baoyz.swipemenulistview.SwipeMenuView.OnSwipeItemClickListener;

/**
 * 
 * @author baoyz
 * @date 2014-8-24
 * 
 */
public class SwipeMenuAdapter implements WrapperListAdapter,
		OnSwipeItemClickListener {

    private ListAdapter mAdapter;
    private Context mContext;
    private SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener;
    private int mPosition = -1;

    public SwipeMenuAdapter(Context context, ListAdapter adapter, int menuPosition) {
        mAdapter = adapter;
        mContext = context;
        mPosition = menuPosition;
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
        ViewGroup viewGroup = null;
        if (convertView == null) {
            View contentView = mAdapter.getView(position, convertView, parent);
            viewGroup = (ViewGroup) contentView;
            if (mPosition >= 0 && mPosition < viewGroup.getChildCount()) {
                contentView = viewGroup.getChildAt(mPosition);
                viewGroup.removeView(contentView);
            }
            SwipeMenu menu = new SwipeMenu(mContext);
            menu.setViewType(getItemViewType(position));
            createMenu(menu);
            SwipeMenuView menuView = new SwipeMenuView(menu,
                    (SwipeMenuListView) parent);
            menuView.setOnSwipeItemClickListener(this);
            SwipeMenuListView listView = (SwipeMenuListView) parent;
            layout = new SwipeMenuLayout(contentView, menuView,
                    listView.getCloseInterpolator(),
                    listView.getOpenInterpolator());
            layout.setPosition(position);
            if (mPosition >= 0) {
                viewGroup.addView(layout);
            } else {
                viewGroup = layout;
            }
        } else {
            viewGroup = (ViewGroup) convertView;
            if (convertView instanceof SwipeMenuLayout) {
                layout = (SwipeMenuLayout)convertView;
            } else {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View view = viewGroup.getChildAt(i);
                    if (view instanceof SwipeMenuLayout) {
                        layout = (SwipeMenuLayout)view;
                        break;
                    }
                }
            }
            if (layout != null) {
                layout.closeMenu();
                layout.setPosition(position);
                mAdapter.getView(position, layout.getContentView(), parent);
            } else {
                // must ensure invoke mAdapter.getView()
                mAdapter.getView(position, convertView, parent);
            }
        }
        if (mAdapter instanceof BaseSwipListAdapter) {
            boolean swipEnable = (((BaseSwipListAdapter) mAdapter).getSwipEnableByPosition(position));
            layout.setSwipEnable(swipEnable);
        }
        return viewGroup;
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

    public void setOnSwipeItemClickListener(
            SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mAdapter.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(int position) {
        return mAdapter.isEnabled(position);
    }

    @Override
    public boolean hasStableIds() {
        return mAdapter.hasStableIds();
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapter.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return mAdapter.getViewTypeCount();
    }

    @Override
    public boolean isEmpty() {
        return mAdapter.isEmpty();
    }

    @Override
    public ListAdapter getWrappedAdapter() {
        return mAdapter;
    }

}
