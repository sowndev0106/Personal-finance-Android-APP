package com.example.personalfinance.database;

import com.google.firebase.database.FirebaseDatabase;

public class Database {

    private static FirebaseDatabase firebaseDatabase = null;

    public static FirebaseDatabase getIntance(){
        if (firebaseDatabase == null) {
            firebaseDatabase=FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
        }
        return firebaseDatabase;
    }

}
