package com.iot;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_device_fragment extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseUser user;
    Button add_device;
    EditText device_name;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_device,container,false);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        add_device=view.findViewById(R.id.btnadddevice);
        device_name=view.findViewById(R.id.etdevicename);
        if(user==null){
            Intent intent=new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        }
        add_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=device_name.getText().toString();
                myRef.child("Users").child(user.getUid()).child("devices").child(name).setValue(false);
            }
        });
        return view;
    }
}
