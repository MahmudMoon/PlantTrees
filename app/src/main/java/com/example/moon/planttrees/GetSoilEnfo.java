package com.example.moon.planttrees;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetSoilEnfo extends AppCompatActivity {

    private static final String TAG = "myTag" ;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<ObjectCreated> arrayList;
    String jsonUrl;
    ListView listView;
    FloatingActionButton fbtn;
    GoogleProgressBar progressBar;


    int[] images = {R.drawable.im1,R.drawable.im2,R.drawable.im3,R.drawable.im4,R.drawable.im5,
            R.drawable.im1,R.drawable.im2,R.drawable.im3,R.drawable.im4,R.drawable.im5};
    String[] names = {"Clay","Sand","Nitrozen","carbon","Alliuminium"
            ,"calsium","Magnesium","sodium","Potasium","water"};
    int[] ratios = new int[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_soil_enfo);
        Intent intent = getIntent();
        jsonUrl = intent.getStringExtra(Info.urlName);
        arrayList = new ArrayList<ObjectCreated>();
        listView = (ListView)findViewById(R.id.list);
        fbtn = (FloatingActionButton)findViewById(R.id.btn_f);
        progressBar = (GoogleProgressBar)findViewById(R.id.google_progress);
        jsonObjectRequest  = new JsonObjectRequest(Request.Method.GET, jsonUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Toast.makeText(getApplicationContext(),String.valueOf(response.getBoolean("raw")),Toast.LENGTH_SHORT).show();
                    fbtn.setClickable(false);

                    jsonParsing(response);
                    packEveryThing();
                    CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),arrayList);
                    listView.setAdapter(customAdapter);
                    fbtn.setClickable(true);
                    progressBar.setVisibility(View.INVISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        SingleToneClass.getInstance(getApplicationContext()).addToRequest(jsonObjectRequest);

        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String soil_type = getSoilType();

                Intent intent1 = new Intent(GetSoilEnfo.this,Choose_Tree.class);
                intent1.putExtra("soil",soil_type);
                startActivity(intent1);
            }
        });

    }

    private String getSoilType() {
        String soil_type;
        int clay = ratios[0];
        int sand =ratios[1];
        int carbon = ratios[3];
        int water = ratios[9];

        if(carbon>=190){
            soil_type = Info.peat;
        }else if(clay<=35 && sand <= 35){
            soil_type=Info.silt;
        }else if(clay>=sand){
            soil_type = Info.clay;
        }else if(sand>clay && sand>40){
            soil_type = Info.sandy;
        }else{
            soil_type = Info.loam;
        }
        return soil_type;
    }

    private void packEveryThing() {
        for(int i=0;i<10;i++) {
            arrayList.add( new ObjectCreated (names[i],ratios[i],images[i]));
        }
        Log.i(TAG, "packEveryThing: Data LOADED");

    }

    private void jsonParsing(JSONObject response) {
        try {
            JSONObject properties = response.getJSONObject("properties");
            int clay_rate =  getClay(properties);
            ratios[0] = clay_rate;
            Log.i(TAG, "jsonParsing: CLAY " + clay_rate);

            int sand_rate =  getSand(properties);
            ratios[1] = sand_rate;
            Log.i(TAG, "jsonParsing: SAND " + sand_rate);

            int nitrozen_rate = getNitrozen(properties);
            ratios[2] = nitrozen_rate;
            Log.i(TAG, "jsonParsing: NITRO " + nitrozen_rate);

            int carbon = getCarbon(properties);
            ratios[3] = carbon;
            Log.i(TAG, "jsonParsing: Carbon "+carbon);

            int Aluminium = getAlluminium(properties);
            ratios[4] = Aluminium;
            Log.i(TAG, "jsonParsing: Alluminium : " + Aluminium );

            int calsium = getCalsium(properties);
            ratios[5] = calsium;
            Log.i(TAG, "jsonParsing: Calsium " + calsium);

            int magnesium = getMagnisium(properties);
            ratios[6] = magnesium;
            Log.i(TAG, "jsonParsing: Magnesium : " + magnesium);

            int sodium = getSodium(properties);
            ratios[7] = sodium;
            Log.i(TAG, "jsonParsing: Sodium " + sodium);

            int potasium = getPotasium(properties);
            ratios[8] = potasium;
            Log.i(TAG, "jsonParsing: Potasium " + potasium);

            int waterLevel = getWater(properties);
            ratios[9] = waterLevel;
            Log.i(TAG, "jsonParsing: Water " + waterLevel);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private int getPotasium(JSONObject properties) {
        JSONObject _rate_name = null;
        try {
            _rate_name = properties.getJSONObject("EXKX");
            JSONObject  _ratio = _rate_name.getJSONObject("M");
            int sl1 = _ratio.getInt("sl1");
            return sl1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getSodium(JSONObject properties) {
        JSONObject _rate_name = null;
        try {
            _rate_name = properties.getJSONObject("ENAX");
            JSONObject  _ratio = _rate_name.getJSONObject("M");
            int sl1 = _ratio.getInt("sl1");
            return sl1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getMagnisium(JSONObject properties) {
        JSONObject _rate_name = null;
        try {
            _rate_name = properties.getJSONObject("EMGX");
            JSONObject  _ratio = _rate_name.getJSONObject("M");
            int sl1 = _ratio.getInt("sl1");
            return sl1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getCalsium(JSONObject properties) {
        JSONObject _rate_name = null;
        try {
            _rate_name = properties.getJSONObject("ECAX");
            JSONObject  _ratio = _rate_name.getJSONObject("M");
            int sl1 = _ratio.getInt("sl1");
            return sl1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getAlluminium(JSONObject properties) {
        JSONObject _rate_name = null;
        try {
            _rate_name = properties.getJSONObject("ALUM3S");
            JSONObject  _ratio = _rate_name.getJSONObject("M");
            int sl1 = _ratio.getInt("sl1");
            return sl1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getWater(JSONObject properties) {
        JSONObject _rate_name = null;
        try {
            _rate_name = properties.getJSONObject("WWP");
            JSONObject  _ratio = _rate_name.getJSONObject("M");
            int sl1 = _ratio.getInt("sl1");
            return sl1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getCarbon(JSONObject properties) {
        JSONObject _rate_name = null;
        try {
            _rate_name = properties.getJSONObject("ORCDRC");
            JSONObject  _ratio = _rate_name.getJSONObject("M");
            int sl1 = _ratio.getInt("sl1");
            return sl1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getNitrozen(JSONObject nitrozen) {
        JSONObject _rate_name = null;
        try {
            _rate_name = nitrozen.getJSONObject("NTO");
            JSONObject  _ratio = _rate_name.getJSONObject("M");
            int sl1 = _ratio.getInt("sl1");
            return sl1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getSand(JSONObject sand) {
        JSONObject rate_name = null;
        try {
            rate_name = sand.getJSONObject("SNDPPT");
            JSONObject  _ratio = rate_name.getJSONObject("M");
            int sl1 = _ratio.getInt("sl1");

            return sl1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getClay(JSONObject clay) {
        JSONObject _rate_name = null;
        try {
            _rate_name = clay.getJSONObject("CLYPPT");
            JSONObject  _ratio = _rate_name.getJSONObject("M");
            int sl1 = _ratio.getInt("sl1");

            return sl1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }

    }



}
