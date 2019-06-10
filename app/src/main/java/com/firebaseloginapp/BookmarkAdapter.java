package com.firebaseloginapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class BookmarkAdapter extends BaseAdapter {
    private ListItemListner listner;
    private ListItemListner listnerBtnDelete;
    Context mContext;
    ArrayList<String> mSourse;
    public BookmarkAdapter(Context context,ArrayList<String> source){
        this.mSourse=source;
        this.mContext=context;
    }
    @Override
    public int getCount() {

        return mSourse.size();
    }

    @Override
    public Object getItem(int position) {
        return mSourse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView ==null ){
        viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.bookmark_layout_item,parent,false);
            viewHolder.textView =  convertView.findViewById(R.id.tvWord);
            viewHolder.btnDelete=  convertView.findViewById(R.id.btnDelete);

            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(mSourse.get(position));
        //بۆ ئەوەی پەنجەمان بە نوسینەکەشا نا هەر ئیش بکات
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listner !=null)
                listner.onItemClick(position);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listnerBtnDelete !=null)
                    listnerBtnDelete.onItemClick(position);
            }
        });
        return convertView;
    }

    public void removeItem(int position){
        mSourse.remove(position);
    }

    class ViewHolder{
        TextView textView;
        ImageView btnDelete;
    }

    public void setOnItemClick(ListItemListner listItemListner){
        this.listner=listItemListner;
    }

    public void setOnItemDeleteClick(ListItemListner listItemListner){
        this.listnerBtnDelete=listItemListner;

    }


}
