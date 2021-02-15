package com.iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class register extends AppCompatActivity {
    Button register;
    EditText devices_edittext,phone,username,cpass,email,password;
    String pass,mail,uname,mobile,devices;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register= findViewById(R.id.btLogin);
        password= findViewById(R.id.etPassword);
        username= findViewById(R.id.etUsername);
        phone= findViewById(R.id.etphone);
        email= findViewById(R.id.etEmail);
        cpass= findViewById(R.id.etRePassword);
        devices_edittext=findViewById(R.id.etDevices);
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail=email.getText().toString();
                pass=password.getText().toString();
                uname=username.getText().toString();
                mobile=phone.getText().toString();
                devices=devices_edittext.getText().toString();
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            assert user != null;
                            String userid=user.getUid();
                            myRef.child("Users").child(userid).setValue(new User(uname,mail,pass,mobile,devices));
                            Toast.makeText(register.this,"Registration successfull",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(register.this,home.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(register.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}