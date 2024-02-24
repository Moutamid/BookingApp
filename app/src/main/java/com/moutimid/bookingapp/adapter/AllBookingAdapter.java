package com.moutimid.bookingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.bookingapp.R;
import com.moutimid.bookingapp.adminpanel.model.BookingModel;

import java.util.Collections;
import java.util.List;

public class AllBookingAdapter extends RecyclerView.Adapter<AllBookingAdapter.ProductViewHolder> {

    private Context context;
    private List<BookingModel> bookingModels;
    private onItemClickListener itemListener;
    private onLongClickListener longListener;

    public interface onItemClickListener {
        void onItemClick(int pos);
    }

    public interface onLongClickListener {
        void onItemLongClick(int pos);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        itemListener = listener;
    }

    public void setOnLongClickListener(onLongClickListener listener) {
        longListener = listener;
    }

    public AllBookingAdapter(Context context, List<BookingModel> bookingModels) {
        this.context = context;
        this.bookingModels = bookingModels;
    }

    public void addList(List<BookingModel> list) {
        bookingModels.clear();
        Collections.copy(bookingModels, list);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.booking_list_user, parent, false);

        return new ProductViewHolder(v, itemListener, longListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.name.setText("Name: " + bookingModels.get(position).getName());
        holder.time.setText("Time: " + bookingModels.get(position).getTime());
//        holder.contact_no.setText("Contact No: "+bookingModels.get(position).getContact_no());
        holder.buzzer_no.setText("Buzzer Number: " + bookingModels.get(position).getBuzzer_no() + "  ");
        holder.no_of_guest.setText("No. of guests: " + bookingModels.get(position).getNo_of_guest() + "  ");
// Assuming contactTextView is the TextView containing the mobile number
        holder.contact_no.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.contact_no.setText("Contact No: " + bookingModels.get(position).getContact_no());
                return true; // Returning true indicates that the long click event has been consumed
            }
        });

// You might also want to handle the case when the user releases the long-press
        holder.contact_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    holder.contact_no.setText("Contact No: ********");
                }
                return false; // Allow other touch events to be handled
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("BookingApp").child("Details");
if(bookingModels.get(position).isBooked())
{
    holder.booked.setChecked(true);


}
else
{    holder.booked.setChecked(false);


}
if(bookingModels.get(position).isSeated())
{
    holder.seated.setChecked(true);


}
else
{    holder.seated.setChecked(false);


}
        holder.booked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    BookingModel booking = new BookingModel(bookingModels.get(position).getKey(), bookingModels.get(position).getTime(), bookingModels.get(position).getName(), bookingModels.get(position).getContact_no(), bookingModels.get(position).getBuzzer_no(), bookingModels.get(position).getNo_of_guest(), true, bookingModels.get(position).isSeated());

                    // Push the Booking object to the database
                    myRef.child(bookingModels.get(position).getKey()).setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Booked successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Failed to add booking", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    BookingModel booking = new BookingModel(bookingModels.get(position).getKey(), bookingModels.get(position).getTime(), bookingModels.get(position).getName(), bookingModels.get(position).getContact_no(), bookingModels.get(position).getBuzzer_no(), bookingModels.get(position).getNo_of_guest(), false, bookingModels.get(position).isSeated());

                    // Push the Booking object to the database
                    myRef.child(bookingModels.get(position).getKey()).setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Booked successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Failed to add booking", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
        holder.seated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    BookingModel booking = new BookingModel(bookingModels.get(position).getKey(), bookingModels.get(position).getTime(), bookingModels.get(position).getName(), bookingModels.get(position).getContact_no(), bookingModels.get(position).getBuzzer_no(), bookingModels.get(position).getNo_of_guest(), bookingModels.get(position).isBooked(), true);

                    // Push the Booking object to the database
                    myRef.child(bookingModels.get(position).getKey()).setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Booked successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Failed to add booking", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    BookingModel booking = new BookingModel(bookingModels.get(position).getKey(), bookingModels.get(position).getTime(), bookingModels.get(position).getName(), bookingModels.get(position).getContact_no(), bookingModels.get(position).getBuzzer_no(), bookingModels.get(position).getNo_of_guest(),  bookingModels.get(position).isBooked(),false);

                    // Push the Booking object to the database
                    myRef.child(bookingModels.get(position).getKey()).setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                            } else {
                                Toast.makeText(context, "Failed to update values", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingModels.size();
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView time, name, contact_no, buzzer_no, no_of_guest;
        CheckBox booked, seated;

        public ProductViewHolder(@NonNull View itemView, final onItemClickListener itemlistener, final onLongClickListener longClickListener) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            name = itemView.findViewById(R.id.name);
            contact_no = itemView.findViewById(R.id.contact_number);
            buzzer_no = itemView.findViewById(R.id.buzzer_number);
            no_of_guest = itemView.findViewById(R.id.number_of_guests);
            booked = itemView.findViewById(R.id.booked);
            seated = itemView.findViewById(R.id.seated);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemlistener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemlistener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (longClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            longClickListener.onItemLongClick(position);
                    }
                    return false;
                }
            });
        }
    }
}
