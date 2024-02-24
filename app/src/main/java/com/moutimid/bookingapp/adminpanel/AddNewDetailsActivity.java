package com.moutimid.bookingapp.adminpanel;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.bookingapp.R;
import com.moutimid.bookingapp.adminpanel.model.BookingModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewDetailsActivity extends AppCompatActivity {

    private EditText timeEditText, nameEditText, contactEditText, buzzerEditText, guestsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.moutamid.bookingapp.R.layout.activity_add_new_details);

        // Initialize EditText fields
        timeEditText = findViewById(R.id.name);
        nameEditText = findViewById(R.id.email);
        contactEditText = findViewById(R.id.contact_number);
        buzzerEditText = findViewById(R.id.buzzer_number);
        guestsEditText = findViewById(R.id.no_of_guests);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String currentTime = sdf.format(calendar.getTime());
        timeEditText.setText(currentTime);

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("BookingApp").child("Details");

        // Add button click listener
        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = timeEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String contact = contactEditText.getText().toString().trim();
                String buzzer = buzzerEditText.getText().toString().trim();
                String guests = guestsEditText.getText().toString().trim();

                // Check for empty fields and show error if any
                if (time.isEmpty()) {
                    timeEditText.setError("Time is required");
                    timeEditText.requestFocus();
                    return;
                }

                if (name.isEmpty()) {
                    nameEditText.setError("Name is required");
                    nameEditText.requestFocus();
                    return;
                }

                if (guests.isEmpty()) {
                    guestsEditText.setError("Number of guests is required");
                    guestsEditText.requestFocus();
                    return;
                }
                String key = myRef.push().getKey();
                // Create a Booking object
                BookingModel booking = new BookingModel(key, time, name, contact, buzzer, guests, false, false);

                // Push the Booking object to the database
                myRef.child(key).setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddNewDetailsActivity.this, "Booking added successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Finish the activity after successful addition
                        } else {
                            Toast.makeText(AddNewDetailsActivity.this, "Failed to add booking", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    public void back(View view) {
        onBackPressed();
    }
}
