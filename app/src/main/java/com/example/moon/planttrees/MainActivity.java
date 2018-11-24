package com.example.moon.planttrees;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String email_;
    String TAG = "MyTag";
    TextView tv_user_Name;
    TextView tv_date;
    RelativeLayout relativeLayout;
    CheckBox checkBox;
    EditText treePlanted;
    Button submit;
    TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        email_ = Info.getEmail();


        total = (TextView)findViewById(R.id.total_t);
        tv_date = (TextView)findViewById(R.id.startedFrom);
        tv_user_Name = (TextView)findViewById(R.id.userName);
        relativeLayout = (RelativeLayout)findViewById(R.id.layout_submit);
        relativeLayout.setVisibility(View.INVISIBLE);
        checkBox = (CheckBox)findViewById(R.id.ibtn);
        treePlanted = (EditText)findViewById(R.id.number);
        submit = (Button)findViewById(R.id.total_tree_planted);



        setUser();


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    relativeLayout.setVisibility(View.VISIBLE);
                    total.setText(String.valueOf(Info.getTotal_tree_planted()));
                }else{
                    relativeLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tree = treePlanted.getText().toString();
                int tree_n = Integer.parseInt(tree);
                String pre_planted = total.getText().toString();
                int tree_planted = Integer.parseInt(pre_planted)+tree_n;
                total.setText(String.valueOf(tree_planted));

                saveTreeCount(tree_planted);

                Toast.makeText(getApplicationContext(),""+tree_n,Toast.LENGTH_SHORT).show();
                relativeLayout.setVisibility(View.INVISIBLE);
                checkBox.setChecked(false);

            }
        });








        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    ///////////////////////////////////////////////////////////////////////////
    private void saveTreeCount(int treePlanted) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Info.user).child(Info.getKey());
        databaseReference.child("total_tree").setValue(treePlanted);
    }


    private void setUser() {
        Log.i(TAG, "setUser: "+"CALLED for" + Info.user);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Info.user);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    CreateUser createUser = snapshot.getValue(CreateUser.class);
                    String mEmail = createUser.getEmail();


                    if(TextUtils.equals(mEmail,email_)){
                        int total_tree_planted = createUser.getTotal_tree();
                        Info.setTotal_tree_planted(total_tree_planted);


                        Info.setEmail(mEmail);


                        String key = createUser.getKey();
                        Info.setKey(key);

                        total.setText(String.valueOf(total_tree_planted));

                        Log.i(TAG, "onDataChange: "+mEmail+" "
                                +createUser.getUser()+" "
                                +createUser.getDate()+createUser.getTotal_tree());

                        Info.setCurrent_user(createUser.getUser());
                        Info.setDate(createUser.getDate());
                        tv_user_Name.setText(Info.getCurrent_user());
                        tv_date.setText(Info.getDate());

                       // Log.i(TAG, "onDataChange: "+Info.getCurrent_user() + " " + Info.getDate());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dashbord) {

        }else if(id==R.id.map){
            Intent intent = new Intent(MainActivity.this,MapsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.forum) {
            Intent intent = new Intent(MainActivity.this,Forum.class);
            startActivity(intent);

        }else if(id==R.id.challange){
            Intent intent = new Intent(MainActivity.this,Challange.class);
            startActivity(intent);
        }

        else if (id == R.id.logout) {
           finishAffinity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
