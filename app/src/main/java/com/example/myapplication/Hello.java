package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.entity.Account;
import com.example.myapplication.entity.Role;

public class Hello extends AppCompatActivity {
    private TextView helloText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        helloText=(TextView) findViewById(R.id.helloText);
        Intent intent= getIntent();


        Account userAccount = (Account) intent.getSerializableExtra("userAccount");

        helloText.setText("Welcome " + userAccount.getUserName() + "! You are logged in as " + userAccount.getRole());
    }
}