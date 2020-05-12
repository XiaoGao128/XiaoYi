package com.example.xiaoyi_test_2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoyi_test_2.R;

public class ListViewAdapter extends BaseAdapter{
    private String[] type;
    private Context context;
    private sendClick sendClick;
    public ListViewAdapter(String[] type, Context context,sendClick sendClick){
        this.type=type;
        this.context=context;
        this.sendClick=sendClick;
    }
    @Override
    public int getCount() {
        return type.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.layout_item_list,parent,false);
        TextView textView=convertView.findViewById(R.id.item_name);
        textView.setText(type[position]);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendClick.sendClick(position);
            }
        });
        return convertView;
    }

    public interface sendClick{
        public void sendClick(int position);
    }
}
