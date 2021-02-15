package com.iot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class device_console_fragment extends Fragment {
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    LinearLayout linearLayout,mainLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.device_console,container,false);

        mainLayout=view.findViewById(R.id.mainLayout);
        if(user==null){
            Intent intent=new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        }

        return view;
    }

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
        // set database path
        myRef=myRef.child("Users").child(user.getUid()).child("devices");
        //read from database and set UI
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int j=0;
                mainLayout.removeAllViews();
                for(final DataSnapshot singledevice: dataSnapshot.getChildren()){
                    //new layout for textview and togglebutton
                    ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    linearLayout=new LinearLayout(context);

                    linearLayout.setLayoutParams(layoutParams);

                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);


                    //textview for device name
                    TextView textView =new TextView(context);
                    LinearLayout.LayoutParams layoutParams_textview=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams_textview.rightMargin=25;
                    textView.setLayoutParams(layoutParams_textview);

                    textView.setText(Objects.requireNonNull(singledevice.getKey()));

                    textView.setTextSize(22);

                    textView.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);

                    // toggle button for on and off
                    Switch toggleButton = new Switch(context);

                    toggleButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    toggleButton.setId(j);

                    boolean on_off=false;

                    if (Objects.equals(singledevice.getValue(), false)){
                        on_off=false;
                    }else{
                        on_off=true;
                    }
                    toggleButton.setChecked(on_off);
                    toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            String name=singledevice.getKey();
                            if(b){
                                myRef.child(name).setValue(true);
                            }else{
                                myRef.child(name).setValue(false);
                            }
                        }

                    });
                    linearLayout.addView(textView);
                    linearLayout.addView(toggleButton);
                    linearLayout.setGravity(Gravity.CENTER);
                    mainLayout.addView(linearLayout);
                    j++;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }
}
