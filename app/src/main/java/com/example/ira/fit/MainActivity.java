package com.example.ira.fit;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageButton;

import com.example.ira.fit.Model.Exercise;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.deleteDatabase("exerciseDB");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            ImageButton squats = (ImageButton) findViewById(R.id.image_button_squats);
            ImageButton sit_ups = (ImageButton) findViewById(R.id.image_button_sit_ups);
            ImageButton push_ups = (ImageButton) findViewById(R.id.image_button_push_ups);
            ImageButton pull_ups = (ImageButton) findViewById(R.id.image_button_pull_ups);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.image_button_squats:
                            startExerciseActivity(Exercise.SQUAT);
                            break;
                        case R.id.image_button_sit_ups:
                            startExerciseActivity(Exercise.SITUP);
                            break;
                        case R.id.image_button_push_ups:
                            startExerciseActivity(Exercise.PUSHUP);
                            break;
                        case R.id.image_button_pull_ups:
                            startExerciseActivity(Exercise.PULLUP);
                            break;
                        default:
                            throw new RuntimeException("Unknow button ID");
                    }
                }
            };
            squats.setOnClickListener(listener);
            sit_ups.setOnClickListener(listener);
            push_ups.setOnClickListener(listener);
            pull_ups.setOnClickListener(listener);
        }catch(Exception e) {
            Log.e("myLog", e.getMessage());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void startExerciseActivity(String type) {
        Intent intent = new Intent(getBaseContext(), ExerciseActivity.class);
        intent.putExtra("EXTRA_EXERCISE_TYPE", type);
        startActivity(intent);
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
        getMenuInflater().inflate(R.menu.navigation, menu);
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
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_statistics) {
            startActivity(new Intent(MainActivity.this, StatisticsActivity.class));

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_exit) {
            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
