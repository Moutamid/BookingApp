package com.moutimid.bookingapp.adminpanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.bookingapp.R;
import com.moutimid.bookingapp.adminpanel.model.BookingModel;

public class EditDetailsActivity extends AppCompatActivity {

    private EditText timeEditText, nameEditText, contactEditText, buzzerEditText, guestsEditText;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        // Initialize EditText fields
        timeEditText = findViewById(R.id.name);
        nameEditText = findViewById(R.id.email);
        contactEditText = findViewById(R.id.contact_number);
        buzzerEditText = findViewById(R.id.buzzer_number);
        guestsEditText = findViewById(R.id.no_of_guests);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("BookingApp").child("Details");

        // Retrieve the details of the selected item passed from the previous activity
        Intent intent = getIntent();
        if (intent != null) {
            key = intent.getStringExtra("key");
            String time = intent.getStringExtra("time");
            String name = intent.getStringExtra("name");
            String contact = intent.getStringExtra("contact");
            String buzzer = intent.getStringExtra("buzzer");
            String guests = intent.getStringExtra("guests");

            // Display the details in EditText fields for editing
            timeEditText.setText(time);
            nameEditText.setText(name);
            contactEditText.setText(contact);
            buzzerEditText.setText(buzzer);
            guestsEditText.setText(guests);
        }

        // Add save button click listener
        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the updated details from EditText fields
                String updatedTime = timeEditText.getText().toString().trim();
                String updatedName = nameEditText.getText().toString().trim();
                String updatedContact = contactEditText.getText().toString().trim();
                String updatedBuzzer = buzzerEditText.getText().toString().trim();
                String updatedGuests = guestsEditText.getText().toString().trim();

                if (updatedTime.isEmpty()) {
                    timeEditText.setError("Time is required");
                    timeEditText.requestFocus();
                    return;
                }

                if (updatedName.isEmpty()) {
                    nameEditText.setError("Name is required");
                    nameEditText.requestFocus();
                    return;
                }

                if (updatedGuests.isEmpty()) {
                    guestsEditText.setError("Number of guests is required");
                    guestsEditText.requestFocus();
                    return;
                }
                // Create a Booking object
                BookingModel booking = new BookingModel(key, updatedTime, updatedName, updatedContact, updatedBuzzer, updatedGuests);

                // Push the Booking object to the database
                myRef.child(key).setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditDetailsActivity.this, "Booking updated successfully", Toast.LENGTH_SHORT).show();
                            Intent resultIntent = new Intent();
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish(); // Finish the activity after saving the changes
                        } else {
                            Toast.makeText(EditDetailsActivity.this, "Failed to update booking", Toast.LENGTH_SHORT).show();
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
