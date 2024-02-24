package com.moutimid.bookingapp.adminpanel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.bookingapp.R;
import com.moutimid.bookingapp.authetications.LoginActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void add_new(View view) {
        startActivity(new Intent(AdminActivity.this, AllBookingActivity.class));
    }
    public void show_booking(View view) {
        startActivity(new Intent(AdminActivity.this, ShowDetailsActivity.class));
    }    public void logout(View view) {
        AlertDialog.Builder checkAlert = new AlertDialog.Builder(AdminActivity.this);
        checkAlert.setMessage("Do you want to Logout?")
                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth mAuth;
                        mAuth = FirebaseAuth.getInstance();

                        mAuth.signOut();
                        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = checkAlert.create();
        alert.setTitle("LogOut");
        alert.show();    }
}