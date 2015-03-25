package com.example.slidingmenu.view;

import com.example.slidingmenu.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView{

	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	
	private int mScreenWidth;
	private int mMenuRightPadding;

	private boolean onece = true;
	
	private int mMenuWidth;
	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs , 0);
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		
		mScreenWidth = displayMetrics.widthPixels;
		
		//mMenuRightPadding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
		
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyle ,0);
		
		int indexCount = a.getIndexCount();
		
		for (int i = 0; i < indexCount; i++) {
			int attr = a.getIndex(i);
			switch(attr){
			case R.styleable.SlidingMenu_rightPadding:
				int defaultRightPadding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
				mMenuRightPadding = a.getDimensionPixelOffset(attr, defaultRightPadding);
				break;
				default:
				break;
			}
		}
	}

	public SlidingMenu(Context context) {
		this(context,null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		if(onece){
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
			
			mContent.getLayoutParams().width = mScreenWidth;
			
			onece = false;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		
		if(changed){
			this.scrollTo(mMenuWidth, 0);
			
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		
		int action = ev.getAction();
		switch(action){
		case MotionEvent.ACTION_UP:
			
			int scrollX = getScrollX();
			
			if(scrollX > mMenuWidth /2){
				this.smoothScrollTo(mMenuWidth, 0);
			}else{
				this.smoothScrollTo(0, 0);
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

}
