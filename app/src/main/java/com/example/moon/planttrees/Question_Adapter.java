package com.example.moon.planttrees;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Question_Adapter extends BaseAdapter {
    ArrayList<CreateObject> arrayList;
    LayoutInflater inflater;
    Context context;
    TextView user;
    TextView ques;

    public Question_Adapter(ArrayList<CreateObject> arrayList, Context context) {
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
        view = inflater.inflate(R.layout.question_adapter,null);
        user = (TextView)view.findViewById(R.id.user);
        ques = (TextView)view.findViewById(R.id.ques);

        user.setText(arrayList.get(i).getUserName());
        ques.setText(arrayList.get(i).getQuestion());

        return view;
    }
}
