package com.example.moon.planttrees;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterTree extends BaseAdapter {
    ArrayList<ObjectForTrees> arrayList;
    Context context;
    LayoutInflater inflater;
    TextView Treename;
    ImageView ivTree;



    public CustomAdapterTree(ArrayList<ObjectForTrees> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_adapter_tree, null);
         Treename = (TextView)view.findViewById(R.id.tv_name_t);
         ivTree = (ImageView)view.findViewById(R.id.image_view_t);
         Treename.setText(arrayList.get(i).getName());
         Picasso.get().load(arrayList.get(i).getUrl()).into(ivTree);
        return view;
    }
}
