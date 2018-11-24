package com.example.moon.planttrees;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Forum extends AppCompatActivity {

    EditText question;
    Button myQuestions,allQuestions;
    Button submit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        init_views();
        init_variables();
        init_functions();
        init_listeners();
    }

    private void init_views() {
        question = (EditText)findViewById(R.id.et_q);
        myQuestions = (Button)findViewById(R.id.btn_my_q);
        allQuestions = (Button)findViewById(R.id.btn_all_q);
        submit = (Button)findViewById(R.id.btn_submit);
    }

    private void init_variables() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Info.Question_table);

    }

    private void init_functions() {

    }

    private void init_listeners() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(!TextUtils.isEmpty(question.getText().toString())){
                     final String que = question.getText().toString();
                    String key = databaseReference.push().getKey();

                    CreateObject CreateObject = new CreateObject(que,Info.getCurrent_user(),key);

                    Task<Void> voidTask = databaseReference.child(key).setValue(CreateObject);
                    voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                           Toast.makeText(getApplicationContext(),"Successfully Asked",Toast.LENGTH_SHORT).show();
                           question.setText("");
                        }
                    });

                    voidTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed to Ask",Toast.LENGTH_SHORT).show();
                        }
                    });



                }else{
                    Toast.makeText(getApplicationContext(),"Question Field is Empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

        myQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forum.this,Questions_List.class);
                intent.putExtra("que","my");
                startActivity(intent);
            }
        });

        allQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forum.this,Questions_List.class);
                intent.putExtra("que","all");
                startActivity(intent);
            }
        });


    }
}
