package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.entity.Account;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText userNameText;
    private EditText passwordText;
    private String userName;
    private String password;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference userAccountReference = db.getReference("accounts");
    List<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        userNameText= (EditText) findViewById(R.id.userNameText);
        passwordText= (EditText) findViewById(R.id.passwordText);
        userName = userNameText.getText().toString();
        password = passwordText.getText().toString();
        accounts = new LinkedList<>();
        userAccountReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    Account userAccount = child.getValue(Account.class);
                    accounts.add(userAccount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void register(View view){
        Intent intent=new Intent(this,Register.class);
        startActivity(intent);
    }

    public void login(View view){
        userName = userNameText.getText().toString();
        password = passwordText.getText().toString();
        if(isValid()){
            for(Account userAccount : accounts){
                if(userAccount.getUserName().equals(userName)){
                    Intent intent = new Intent(this,Hello.class);
                    intent.putExtra("userAccount", userAccount);
                    startActivity(intent);
                }
            }
        }
    }

    private boolean isValid() {
        if (userName.equals("")) {
            Toast toast = Toast.makeText(this, "User Name cannot be empty!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (password.equals("")) {
            Toast toast = Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if(!ListUtils.contains(accounts,userName)) {
            Toast toast = Toast.makeText(this, "The user name does not exist!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if(!ListUtils.contains(accounts,userName,password)){
            Toast toast = Toast.makeText(this, "The password is incorrect!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }
}