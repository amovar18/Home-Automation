package com.iot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ImageButton register;
    Button login;
    EditText uname,pass;
    String username,password;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(MainActivity.this,home.class);
            startActivity(intent);
        }
        else{
            register=(ImageButton) findViewById(R.id.btRegister);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MainActivity.this,register.class);
                    startActivity(intent);
                }
            });
            uname=(EditText) findViewById(R.id.etUsername);
            pass=(EditText) findViewById(R.id.etPassword);
            login=(Button) findViewById(R.id.btLogin);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    username=uname.getText().toString();
                    password=pass.getText().toString();
                    mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mAuth = FirebaseAuth.getInstance();
                                user=mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this,"Signed In", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(MainActivity.this,home.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Sign in Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(user!=null){
            Intent intent=new Intent(MainActivity.this,home.class);
            startActivity(intent);
        }
    }
}