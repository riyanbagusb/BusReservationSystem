package com.bagus.busreservationsystem.controller.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bagus.busreservationsystem.R;
import com.bagus.busreservationsystem.adapter.TicketListAdapter;
import com.bagus.busreservationsystem.models.Ticket;
import com.bagus.busreservationsystem.rest.APIClient;
import com.bagus.busreservationsystem.rest.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketFragment extends Fragment {
    private APIInterface apiInterface;
    private RecyclerView rvListTicket;
    private RecyclerView.Adapter ticketAdapter;
    private RecyclerView.LayoutManager ticketLayoutManager;
    private View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_ticket, container, false);

        rvListTicket = fragmentView.findViewById(R.id.rvListTicket);
        ticketLayoutManager = new LinearLayoutManager(getActivity());
        rvListTicket.setLayoutManager(ticketLayoutManager);

        getTickets();

        return fragmentView;
    }

    private void getTickets() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Ticket>> listCall = apiInterface.getTickets();
        listCall.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                List<Ticket> ticketList = response.body();
                ticketAdapter = new TicketListAdapter(ticketList, getContext());
                rvListTicket.setAdapter(ticketAdapter);
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
            }
        });

    }
}