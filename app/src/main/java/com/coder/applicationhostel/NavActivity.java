package com.coder.applicationhostel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class NavActivity extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);


        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame , new AddPackage_Fragment()).commit();

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment =null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        selectedFragment = new Home_Fragment();
                        break;

                    case R.id.nav_addpackage:
                        selectedFragment = new AddPackage_Fragment();
                        break;

                    case R.id.navbooking:
                        selectedFragment = new Booking_Fragment();
                        break;

                    case R.id.nav_profile:
                        selectedFragment = new Profile_Fragment();
                        break;

                    case R.id.nav_logout:

                        SharedPreferences settings = getSharedPreferences("Log", MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.remove("isLoggedIn");
                        editor.commit();

                        Intent intent = new Intent(NavActivity.this , LoginActivity.class);
                        startActivity(intent);
                        finish();
                        return false;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,selectedFragment).commit();
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to exit ?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
