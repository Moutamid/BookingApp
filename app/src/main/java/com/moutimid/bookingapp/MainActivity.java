package com.moutimid.bookingapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.bookingapp.R;
import com.moutimid.bookingapp.adapter.AllBookingAdapter;
import com.moutimid.bookingapp.adminpanel.AdminActivity;
import com.moutimid.bookingapp.adminpanel.model.BookingModel;
import com.moutimid.bookingapp.authetications.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private RecyclerView ProductsRecycler;
    private AllBookingAdapter adapter;
    private List<BookingModel> adminProducts;
    private DatabaseReference mDataBaseRef;
    private ProgressBar bar;
    private static final int EDIT_DETAILS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProductsRecycler = (RecyclerView) findViewById(R.id.ProductsRecycler);
        bar = findViewById(R.id.productProgressBar);
        Dialog lodingbar = new Dialog(MainActivity.this);
        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        mDataBaseRef = FirebaseDatabase.getInstance().getReference().child("BookingApp").child("Details");
        adminProducts = new ArrayList<>();
        adapter = new com.moutimid.bookingapp.adapter.AllBookingAdapter(getApplicationContext(), adminProducts);
        ProductsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ProductsRecycler.setAdapter(adapter);
        mDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminProducts.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())//category
                {
                    adminProducts.add(new BookingModel(snapshot1.getKey(),
                            snapshot1.child("time").getValue(String.class),
                            snapshot1.child("name").getValue(String.class),
                            snapshot1.child("contact_no").getValue(String.class),
                            snapshot1.child("buzzer_no").getValue(String.class),
                            snapshot1.child("no_of_guest").getValue(String.class),
                            snapshot1.child("booked").getValue(boolean.class),
                            snapshot1.child("seated").getValue(boolean.class)));
                }
                adapter.notifyDataSetChanged();
                bar.setVisibility(View.INVISIBLE);
                lodingbar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_DETAILS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Refresh the RecyclerView to reflect the changes made in EditDetailsActivity
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void logout(View view) {
        AlertDialog.Builder checkAlert = new AlertDialog.Builder(MainActivity.this);
        checkAlert.setMessage("Do you want to Logout?")
                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth mAuth;
                        mAuth = FirebaseAuth.getInstance();

                        mAuth.signOut();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
        alert.show();
    }
}