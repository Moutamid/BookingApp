package com.moutimid.bookingapp.adminpanel;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.moutimid.bookingapp.adminpanel.adapter.AllBookingAdapter;
import com.moutimid.bookingapp.adminpanel.model.BookingModel;
import com.moutimid.bookingapp.authetications.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllBookingActivity extends AppCompatActivity {

    private RecyclerView ProductsRecycler;
    private AllBookingAdapter adapter;
    private Button ProductsFloatingActionButton;
    private List<BookingModel> adminProducts;
    private DatabaseReference mDataBaseRef;
    private ProgressBar bar;
    private static final int EDIT_DETAILS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_booking);
        ProductsRecycler = (RecyclerView) findViewById(R.id.ProductsRecycler);
        ProductsFloatingActionButton = (Button) findViewById(R.id.ProductsFloatingBtnId);
        bar = findViewById(R.id.productProgressBar);
        Dialog lodingbar = new Dialog(AllBookingActivity.this);
        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        mDataBaseRef = FirebaseDatabase.getInstance().getReference().child("BookingApp").child("Details");
        adminProducts = new ArrayList<>();
        adapter = new AllBookingAdapter(getApplicationContext(), adminProducts);
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

        adapter.setOnItemClickListener(new AllBookingAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(AllBookingActivity.this, EditDetailsActivity.class);
                BookingModel selectedBooking = adminProducts.get(pos);
                intent.putExtra("key", selectedBooking.getKey());
                intent.putExtra("time", selectedBooking.getTime());
                intent.putExtra("name", selectedBooking.getName());
                intent.putExtra("contact", selectedBooking.getContact_no());
                intent.putExtra("buzzer", selectedBooking.getBuzzer_no());
                intent.putExtra("guests", selectedBooking.getNo_of_guest());
                intent.putExtra("book", selectedBooking.isBooked());
                intent.putExtra("seat", selectedBooking.isSeated());
                startActivityForResult(intent, EDIT_DETAILS_REQUEST_CODE);
            }
        });


        adapter.setOnLongClickListener(new AllBookingAdapter.onLongClickListener() {
            @Override
            public void onItemLongClick(final int pos) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(AllBookingActivity.this).setTitle("Confirmation").setMessage("Are You Sure You Want To Delete ?!").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reference = mDataBaseRef.child(adminProducts.get(pos).getKey());
                        reference.removeValue();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert);
                dialog.show();
            }
        });

        ProductsFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllBookingActivity.this, AddNewDetailsActivity.class));
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
    adapter.notifyDataSetChanged();}
    public void logout(View view) {
        androidx.appcompat.app.AlertDialog.Builder checkAlert = new androidx.appcompat.app.AlertDialog.Builder(AllBookingActivity.this);
        checkAlert.setMessage("Do you want to Logout?")
                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth mAuth;
                        mAuth = FirebaseAuth.getInstance();

                        mAuth.signOut();
                        Intent intent = new Intent(AllBookingActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        androidx.appcompat.app.AlertDialog alert = checkAlert.create();
        alert.setTitle("LogOut");
        alert.show();
    }
}