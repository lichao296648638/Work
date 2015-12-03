package com.example.tianshijie1.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 用来计算 Listview 的高度 我在这里加了转换成dp的方法
 *
 * @author admin
 */
public class LvHeightUtil {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        /**
         *  XQ3Start
         * 	需求编号:XQ3
         * 	需求描述：优化主页热门项目加载速度
         * 	修复人：李超
         * 	修复日期：2015-11-24
         */
        View listItem = listAdapter.getView(0, null, listView);
        listItem.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        listItem.measure(0, 0);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) +
                listItem.getMeasuredHeight() * (listAdapter.getCount());
        listView.setLayoutParams(params);
        //XQ3End
    }

    public static void setListViewHeightBasedOnChildren2(GridView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount() / 2; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
