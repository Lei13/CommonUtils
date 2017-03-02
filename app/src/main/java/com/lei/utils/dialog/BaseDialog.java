package com.lei.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lei on 2017/3/2.
 */

public class BaseDialog extends Dialog {
    private Context mContext;
    private SparseArray<View> mArrayViews;/*保存 views*/
    private LayoutInflater mInflater;
    private Builder mBuilder;
    private View mContentView;


    private BaseDialog(Context context, Builder builder) {
        super(context);
        this.mBuilder = builder;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mArrayViews = new SparseArray();
        initContentView();
    }

    /*设置 Dialog 布局内容*/
    private void initContentView() {
        mContentView = mInflater.inflate(mBuilder.layoutId, null);
        setContentView(mContentView);
        setCanceledOnTouchOutside(!mBuilder.isOutsideTouchable);
    }

    /*根据 控件id 得到控件 view*/
    public View findView(int id) {
        View view = mArrayViews.get(id);
        if (view == null) {
            view = mContentView.findViewById(id);
            mArrayViews.put(id, view);
        }

        return view;
    }

    /**
     * 给特定控件设置点击事件
     *
     * @param id
     * @param clickListener
     */
    public void setOnClickListener(int id, View.OnClickListener clickListener) {
        View view = mArrayViews.get(id);
        if (view == null) {
            view = mContentView.findViewById(id);
            mArrayViews.put(id, view);
        }
        if (view != null) {
            view.setOnClickListener(clickListener);
        }
    }

    /**
     * 给 TextView 设置文字内容
     * @param viewId
     * @param resId
     */
    public void setText(int viewId, int resId) {
        View view = mArrayViews.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mArrayViews.put(viewId, view);
        }
        if (view instanceof TextView) {
            ((TextView) view).setText(resId);
        }
    }

    /**
     * 设置背景图片
     * @param viewId
     * @param resId
     */
    public void setBackground(int viewId, int resId) {
        View view = mArrayViews.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mArrayViews.put(viewId, view);
        }
        view.setBackgroundResource(resId);
    }

    /**
     * 给ImageView 设置
     * @param viewId
     * @param resId
     */
    public void setImageResource(int viewId, int resId) {
        View view = mArrayViews.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mArrayViews.put(viewId, view);
        }
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(resId);
        }
    }


    /**
     * 构造者模式
     */
    public static class Builder {
        Context context;
        int layoutId;
        boolean isOutsideTouchable;

        public Builder with(Context context) {
            this.context = context;

            return this;
        }

        public Builder setContentView(int layoutId) {
            this.layoutId = layoutId;

            return this;
        }

        public Builder setOutsideTouchable(boolean isOutsideTouchable) {
            this.isOutsideTouchable = isOutsideTouchable;

            return this;
        }

        public BaseDialog build() {

            return new BaseDialog(context, this);
        }

    }
}
