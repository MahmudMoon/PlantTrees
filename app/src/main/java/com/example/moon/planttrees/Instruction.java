package com.example.moon.planttrees;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

public class Instruction extends AppCompatActivity implements ins1.OnFragmentInteractionListener, ins2.OnFragmentInteractionListener,ins3.OnFragmentInteractionListener{

    ViewPager viewPager ;
    ImageButton ib1,ib2,ib3;
    private String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        viewPager = (ViewPager)findViewById(R.id.vp_);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),3);
        viewPager.setAdapter(pageAdapter);

        ib1 = (ImageButton)findViewById(R.id.step1);
        ib2 = (ImageButton)findViewById(R.id.step2);
        ib3 = (ImageButton)findViewById(R.id.step3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        ib1.setBackgroundResource(R.drawable.round);
                        ib2.setBackgroundResource(R.drawable.round_white);
                        ib3.setBackgroundResource(R.drawable.round_white);
                        break;

                    case 1:
                        ib1.setBackgroundResource(R.drawable.round_white);
                        ib2.setBackgroundResource(R.drawable.round);
                        ib3.setBackgroundResource(R.drawable.round_white);
                        break;

                    case 2:
                        ib1.setBackgroundResource(R.drawable.round_white);
                        ib2.setBackgroundResource(R.drawable.round_white);
                        ib3.setBackgroundResource(R.drawable.round);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
