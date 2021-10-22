package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.entity.Role;
import com.example.myapplication.entity.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Register extends AppCompatActivity {
    private EditText userNameText,passwordText;
    private RadioGroup roleGroup;

    private String userName;
    private String password;
    private Role role;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference userAccountsReference = db.getReference("accounts");
    List<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        userNameText = (EditText) findViewById(R.id.userNameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        roleGroup = (RadioGroup) findViewById(R.id.roleGroup);
        userName = userNameText.getText().toString();
        password = passwordText.getText().toString();
        accounts = new LinkedList<>();
        userAccountsReference.addValueEventListener(new ValueEventListener() {
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
        userName = userNameText.getText().toString();
        password = passwordText.getText().toString();
        if(isValid()){
            Account userAccount = new Account(userName,password,role);
            String id = userAccountsReference.push().getKey();
            userAccountsReference.child(id).setValue(userAccount);
            finish();
        }
    }

    private boolean isValid(){
        if(userName.equals("")){
            Toast toast= Toast.makeText(this,"User Name cannot be empty!",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if(password.equals("")){
            Toast toast= Toast.makeText(this,"Password cannot be empty!",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        switch(roleGroup.getCheckedRadioButtonId()){
            case R.id.customerBtn:
                role= Role.customer;
                break;
            case R.id.employeeBtn:
                role=Role.employee;
                break;
            default:
                Toast toast= Toast.makeText(this,"Have to choose a role!",Toast.LENGTH_SHORT);
                toast.show();
                return false;
        }
        if(ListUtils.contains(accounts,userName)) {
            Toast toast = Toast.makeText(this, "The user name has been registered!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }
}