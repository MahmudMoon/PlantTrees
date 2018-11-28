package com.example.moon.planttrees;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ScrollingActivity extends Activity {

   private String name;
   private String description;
   private String url;
   ImageView iv_tree;
   TextView tv_tree_name,tv_tree_des;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        iv_tree = (ImageView)findViewById(R.id.iv_tree);
        tv_tree_name = (TextView)findViewById(R.id.tv_treeName);
        tv_tree_des = (TextView)findViewById(R.id.tv_description);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        description = intent.getStringExtra("description");
        url = intent.getStringExtra("url");


        tv_tree_des.setText(description);
        tv_tree_name.setText(name);
        Picasso.get().load(url).into(iv_tree);




    }
}
