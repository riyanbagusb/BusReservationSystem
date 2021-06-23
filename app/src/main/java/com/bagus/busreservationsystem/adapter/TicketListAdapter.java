package com.bagus.busreservationsystem.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bagus.busreservationsystem.R;
import com.bagus.busreservationsystem.models.Ticket;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.MyViewHolder> {

    List<Ticket> ticketList;
    Context context;

    public TicketListAdapter(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }

    @Override
    public TicketListAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ticket_item, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TicketListAdapter.MyViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date today = Calendar.getInstance().getTime();
            Date ticketDate = format.parse(String.valueOf(ticketList.get(position).getJourneyDate()));

            if (today.after(ticketDate)){
                holder.itemView.setVisibility(View.INVISIBLE);
                ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                params.height = 0;
                holder.itemView.setLayoutParams(params);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.txtSeatNumber.setText(ticketList.get(position).getSeatNumber().toString());
        holder.txtJourneyDate.setText(ticketList.get(position).getJourneyDate());
        holder.txtFare.setText("Rp."+ticketList.get(position).getTripSchedule().getTripDetail().getFare().toString());
        holder.txtSourceStop.setText(ticketList.get(position).getTripSchedule().getTripDetail().getSourceStop().getName());
        holder.txtDestinationStop.setText(ticketList.get(position).getTripSchedule().getTripDetail().getDestStop().getName());
        holder.txtAgency.setText(ticketList.get(position).getTripSchedule().getTripDetail().getAgency().getName());
        holder.txtBus.setText(ticketList.get(position).getTripSchedule().getTripDetail().getBus().getCode());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtSeatNumber, txtJourneyDate, txtFare, txtSourceStop, txtDestinationStop, txtAgency, txtBus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSeatNumber = itemView.findViewById(R.id.txtSeatNumber);
            txtJourneyDate = itemView.findViewById(R.id.txtJourneyDate);
            txtFare = itemView.findViewById(R.id.txtFare);
            txtSourceStop = itemView.findViewById(R.id.txtSourceStop);
            txtDestinationStop = itemView.findViewById(R.id.txtDestinationStop);
            txtAgency = itemView.findViewById(R.id.txtAgency);
            txtBus = itemView.findViewById(R.id.txtBus);
        }
    }
}
