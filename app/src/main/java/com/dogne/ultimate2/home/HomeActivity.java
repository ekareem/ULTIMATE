package com.dogne.ultimate2.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dogne.ultimate2.R;

public class HomeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HomeLayoutManager layoutManager = new HomeLayoutManager(this);
    }
}