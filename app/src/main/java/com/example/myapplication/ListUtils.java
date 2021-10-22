package com.example.myapplication;

import android.widget.Toast;

import com.example.myapplication.entity.Account;

import java.util.List;

public class ListUtils {
    public static boolean contains(List<Account> accounts, String userName){
        for(Account userAccount: accounts){
            if(userAccount.getUserName().equals(userName)){
                return true;
            }
        }
        return false;
    }

    public static boolean contains(List<Account> accounts, String userName, String password){
        for(Account userAccount : accounts){
           if(userAccount.getUserName().equals(userName) && userAccount.getPassword().equals(password)){
               return true;
           }
        }
        return false;
    }

}
