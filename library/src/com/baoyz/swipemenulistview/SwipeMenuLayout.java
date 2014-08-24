package com.baoyz.swipemenulistview;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * 
 * @author baoyz
 * @date 2014-8-23
 * 
 */
public class SwipeMenuLayout extends RelativeLayout {

	private static final int CONTENT_VIEW_ID = 1;
	private static final int MENU_VIEW_ID = 2;

	private static final int STATE_CLOSE = 0;
	private static final int STATE_OPEN = 1;

	private View mContentView;
	private SwipeMenuView mMenuView;
	private int mDownX;
	private int state = STATE_CLOSE;
	private GestureDetectorCompat mGestureDetector;
	private OnGestureListener mGestureListener;
	private boolean isFling;
	private ScrollerCompat mScroller;
	private int mBaseX;
	private int position;

	public SwipeMenuLayout(View contentView, SwipeMenuView menuView) {
		super(contentView.getContext());
		mContentView = contentView;
		mMenuView = menuView;
		init();
	}

	private SwipeMenuLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private SwipeMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private SwipeMenuLayout(Context context) {
		super(context);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
		mMenuView.setPosition(position);
	}

	private void init() {
		mGestureListener = new SimpleOnGestureListener() {
			@Override
			public boolean onDown(MotionEvent e) {
				isFling = false;
				return true;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				// TODO
				// Log.i("byz", "velocityX = " + velocityX);
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		};
		mGestureDetector = new GestureDetectorCompat(getContext(),
				mGestureListener);

		mScroller = ScrollerCompat.create(getContext());

		LayoutParams contentParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mContentView.setLayoutParams(contentParams);
		if (mContentView.getId() < 1) {
			mContentView.setId(CONTENT_VIEW_ID);
		}

		mMenuView.setId(MENU_VIEW_ID);
		mMenuView.setVisibility(View.GONE);

		addView(mMenuView);
		addView(mContentView);

		if (mContentView.getBackground() == null) {
			mContentView.setBackgroundColor(Color.WHITE);
		}

		getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						LayoutParams params = (LayoutParams) mMenuView
								.getLayoutParams();
						params.height = mContentView.getHeight();
						mMenuView.setLayoutParams(mMenuView.getLayoutParams());
						getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});

	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	public boolean onSwipe(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int dis = (int) (mDownX - event.getX());
			if (state == STATE_OPEN) {
				dis += mMenuView.getWidth();
			}
			swpie(dis);
			break;
		case MotionEvent.ACTION_UP:
			if ((mDownX - event.getX()) > (mMenuView.getWidth() / 2)) {
				// open
				smoothOpenMenu();
			} else {
				// close
				smoothCloseMenu();
				return false;
			}
			break;
		}
		mGestureDetector.onTouchEvent(event);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	private void swpie(int dis) {
		if (mMenuView.getVisibility() == View.GONE) {
			mMenuView.setVisibility(View.VISIBLE);
		}
		if (dis > mMenuView.getWidth()) {
			dis = mMenuView.getWidth();
		}
		if (dis < 0) {
			dis = 0;
		}
		mContentView.layout(-dis, mContentView.getTop(),
				mContentView.getWidth() - dis, mContentView.getBottom());
		mMenuView.layout(mContentView.getWidth() - dis, mMenuView.getTop(),
				mContentView.getWidth() + mMenuView.getWidth() - dis,
				mMenuView.getBottom());
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (state == STATE_OPEN) {
				swpie(mScroller.getCurrX());
			} else {
				swpie(mBaseX - mScroller.getCurrX());
			}
			postInvalidate();
		} else {
		}
	}

	public void smoothCloseMenu() {
		state = STATE_CLOSE;
		mBaseX = -mContentView.getLeft();
		mScroller.startScroll(0, 0, mBaseX, 0);
		postInvalidate();
	}

	public void smoothOpenMenu() {
		state = STATE_OPEN;
		mScroller.startScroll(-mContentView.getLeft(), 0, mMenuView.getWidth(),
				0);
		postInvalidate();
	}

	public void closeMenu() {
		if (state == STATE_OPEN) {
			state = STATE_CLOSE;
			swpie(0);
		}
	}

	public void openMenu() {
		if (state == STATE_CLOSE) {
			state = STATE_OPEN;
			swpie(mMenuView.getWidth());
		}
	}

	public View getContentView() {
		return mContentView;
	}

	public SwipeMenuView getMenuView() {
		return mMenuView;
	}

}
