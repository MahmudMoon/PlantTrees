package com.example.moon.planttrees;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    ArrayList<ObjectCreated> mArrayList;
    TextView _name,_ratio,_unit;
    ImageView _imageView;

    public CustomAdapter(Context mContext, ArrayList<ObjectCreated> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_layout, null);
        _name = (TextView)view.findViewById(R.id.tv_name);
        _unit = (TextView)view.findViewById(R.id.tv_unit);
        _ratio = (TextView)view.findViewById(R.id.tv_ratio);
        _imageView = (ImageView) view.findViewById(R.id.image_view);

        _name.setText(mArrayList.get(position).get_name());
        _unit.setText(mArrayList.get(position).getUnit());
        _ratio.setText(String.valueOf(mArrayList.get(position).getRatio()));
        _imageView.setImageResource(mArrayList.get(position).getId_number());

        return view;
    }
}
