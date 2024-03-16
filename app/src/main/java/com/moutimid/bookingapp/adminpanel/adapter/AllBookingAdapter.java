package com.moutimid.bookingapp.adminpanel.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.bookingapp.R;
import com.moutimid.bookingapp.adminpanel.model.BookingModel;

import java.util.Collections;
import java.util.List;

public class AllBookingAdapter extends RecyclerView.Adapter<AllBookingAdapter.ProductViewHolder> {

    private Context context;
    private List<BookingModel> bookingModels;
    private AllBookingAdapter.onItemClickListener itemListener;
    private AllBookingAdapter.onLongClickListener longListener;

    public interface onItemClickListener{
        void onItemClick(int pos);
    }
    public interface onLongClickListener{
        void onItemLongClick(int pos);
    }

    public void setOnItemClickListener(AllBookingAdapter.onItemClickListener listener)
    {
        itemListener = listener;
    }

    public void setOnLongClickListener(AllBookingAdapter.onLongClickListener listener)
    {
        longListener = listener;
    }

    public AllBookingAdapter(Context context, List<BookingModel> bookingModels) {
        this.context = context;
        this.bookingModels = bookingModels;
    }

    public void addList(List<BookingModel> list)
    {
        bookingModels.clear();
        Collections.copy(bookingModels , list);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.booking_list , parent , false);

        return new ProductViewHolder(v , itemListener , longListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.name.setText("Name: "+bookingModels.get(position).getName());
        holder.time.setText("Time: "+bookingModels.get(position).getTime());
        holder.buzzer_no.setText("Buzzer Number: " + bookingModels.get(position).getBuzzer_no() + "  ");
        holder.no_of_guest.setText("No. of guests: " + bookingModels.get(position).getNo_of_guest() + "  ");
        holder.contact_no.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.contact_no.setText("Contact No: " + bookingModels.get(position).getContact_no());
                return true;
                }
        });

        if (bookingModels.get(position).isBooked() && bookingModels.get(position).isSeated()) {
            holder.booked.setText("Table ready: true");
            holder.seated.setText("Seated: true");
            holder.PrContainer.setBackgroundColor(Color.parseColor("#8BC34A"));
        } else if (!bookingModels.get(position).isBooked() && bookingModels.get(position).isSeated()) {
            holder.booked.setText("Table ready: false");
            holder.seated.setText("Seated: true");
            holder.PrContainer.setBackgroundColor(Color.parseColor("#ECE131"));
        } else if (bookingModels.get(position).isBooked() && !bookingModels.get(position).isSeated()) {
            holder.booked.setText("Table ready: true");
            holder.seated.setText("Seated: false");
            holder.PrContainer.setBackgroundColor(Color.parseColor("#ECE131"));
        } else {
            holder.booked.setText("Table ready: false");
            holder.seated.setText("Seated: false");
            holder.PrContainer.setBackgroundColor(Color.parseColor("#f51b00"));
        }
        holder.contact_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    holder.contact_no.setText("Contact No: ********");
                }
                return false;
                }
        });
    }

    @Override
    public int getItemCount() {
        return bookingModels.size();
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView time , name,  contact_no, buzzer_no, no_of_guest, booked, seated;
        RelativeLayout PrContainer;
        public ProductViewHolder(@NonNull View itemView, final AllBookingAdapter.onItemClickListener itemlistener , final AllBookingAdapter.onLongClickListener longClickListener) {
            super(itemView);
            PrContainer = itemView.findViewById(R.id.PrContainer);
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
                    if(itemlistener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            itemlistener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(longClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                            longClickListener.onItemLongClick(position);
                    }
                    return false;
                }
            });
        }
    }
}
