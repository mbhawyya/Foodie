package com.bhawyyamittal.foodie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void addFoodButton(View view){
        Intent intent = new Intent(this,AddFood.class);
        startActivity(intent);

    }


    public void openFoodButton(View view) {
        Intent intent = new Intent(this,OpenOrderActivity.class);
        startActivity(intent);
    }
}
