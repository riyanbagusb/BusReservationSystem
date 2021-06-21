package com.bagus.busreservationsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bagus.busreservationsystem.R;
import com.bagus.busreservationsystem.models.Ticket;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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
        holder.txtSeatNumber.setText(ticketList.get(position).getSeatNumber().toString());
        holder.txtJourneyDate.setText(ticketList.get(position).getJourneyDate());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtSeatNumber, txtJourneyDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSeatNumber = itemView.findViewById(R.id.txtSeatNumber);
            txtJourneyDate = itemView.findViewById(R.id.txtJourneyDate);
        }
    }
}
