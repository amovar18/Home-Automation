package com.iot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class home extends AppCompatActivity {
    protected DrawerLayout drawer;
    protected Toolbar toolbar;
    public ActionBarDrawerToggle toggle;
    public NavigationView nav;
    View header;
    TextView show_username;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar=(Toolbar)findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
        toolbar.setTitle("IoT");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.Open, R.string.Close);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if(user==null){
            Intent intent=new Intent(home.this,MainActivity.class);
            startActivity(intent);
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/"+user.getUid());

        nav = (NavigationView) findViewById(R.id.nv);
        header=nav.getHeaderView(0);
        show_username=(TextView) header.findViewById(R.id.show_username);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                show_username.setText(Objects.requireNonNull(dataSnapshot.child("username").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
        drawer.addDrawerListener(toggle);
        nav.getMenu().findItem(R.id.logout).setVisible(false);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentholder, new device_console_fragment()).commit();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();

                        Intent intent=new Intent(home.this,MainActivity.class);
                        startActivity(intent);
                        show_username.setVisibility(View.GONE);
                        nav.getMenu().findItem(R.id.logout).setVisible(false);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.add:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentholder, new add_device_fragment()).commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.manage_devices:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentholder, new device_console_fragment()).commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(user==null){
            Intent intent=new Intent(home.this,MainActivity.class);
            startActivity(intent);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentholder, new device_console_fragment()).commit();
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(user==null){
            show_username.setVisibility(View.GONE);
        }else{
            show_username.setVisibility(View.VISIBLE);

        }
    }
}