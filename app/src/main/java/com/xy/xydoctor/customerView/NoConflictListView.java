package com.xy.xydoctor.customerView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 重写ListView，实现listview的高度为所有子Item的高度的总和
 * @author yuan
 *
 */
public class NoConflictListView extends ListView
{
	public NoConflictListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	public NoConflictListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	public NoConflictListView(Context context)
	{
		super(context);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
