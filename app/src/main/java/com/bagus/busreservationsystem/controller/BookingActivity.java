package com.bagus.busreservationsystem.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bagus.busreservationsystem.R;
import com.bagus.busreservationsystem.controller.ui.TicketFragment;
import com.bagus.busreservationsystem.models.Ticket;
import com.bagus.busreservationsystem.models.TicketReservation;
import com.bagus.busreservationsystem.models.TripSchedule;
import com.bagus.busreservationsystem.rest.APIClient;
import com.bagus.busreservationsystem.rest.APIInterface;
import com.bagus.busreservationsystem.utils.MySession;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {
    private MySession mySession;
    private APIInterface apiInterface;
    private TripSchedule tripSchedule;
    private TextView txtJourneyDate, txtFare, txtSourceStop, txtDestinationStop, txtAgency, txtBus;
    private Button btnPesanTiket;
    private Integer passengerId;
    private TicketReservation ticketReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mySession = new MySession(BookingActivity.this);
        HashMap<String, String> userSession = mySession.getUserDetails();
        passengerId = Integer.valueOf(userSession.get(MySession.KEY_ID));

        txtJourneyDate = findViewById(R.id.txtJourneyDate);
        txtFare = findViewById(R.id.txtFare);
        txtSourceStop = findViewById(R.id.txtSourceStop);
        txtDestinationStop = findViewById(R.id.txtDestinationStop);
        txtAgency = findViewById(R.id.txtAgency);
        txtBus = findViewById(R.id.txtBus);
        btnPesanTiket = findViewById(R.id.btnPesanTiket);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        getTripSchedule(id);

        btnPesanTiket.setOnClickListener(v -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
            builder.setTitle("Pesan Tiket");
            builder.setMessage("Apakah anda yakin ingin memesan tiket ini?");
            builder.setPositiveButton("ya",
                    (dialog, which) -> {
                        dialog.dismiss();
                        pesanTiket();
                    });
            builder.setNegativeButton("tidak",
                    (dialog, which) -> dialog.dismiss());
            builder.setCancelable(false);
            builder.show();
        });
    }

    private void getTripSchedule(String id) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<TripSchedule> tripScheduleCall = apiInterface.getTripSchedule(id);

        tripScheduleCall.enqueue(new Callback<TripSchedule>() {
            @Override
            public void onResponse(Call<TripSchedule> call, Response<TripSchedule> response) {
                tripSchedule = response.body();
                txtJourneyDate.setText(tripSchedule.getTripDate());
                txtFare.setText("Rp."+tripSchedule.getTripDetail().getFare());
                txtSourceStop.setText(tripSchedule.getTripDetail().getSourceStop().getName());
                txtDestinationStop.setText(tripSchedule.getTripDetail().getDestStop().getName());
                txtAgency.setText(tripSchedule.getTripDetail().getAgency().getName());
                txtBus.setText(tripSchedule.getTripDetail().getBus().getCode());
            }

            @Override
            public void onFailure(Call<TripSchedule> call, Throwable t) {

            }
        });
    }

    private void pesanTiket() {
        ticketReservation = new TicketReservation(1, false, tripSchedule.getTripDate(), tripSchedule.getId(), passengerId);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Ticket> ticketCall = apiInterface.createTicket(ticketReservation);
        ticketCall.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                Toast.makeText(BookingActivity.this, "Tiket berhasil dipesan", Toast.LENGTH_LONG).show();
                Intent i = new Intent(BookingActivity.this, MainActivity.class);
                i.putExtra("ticketFragment",1);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {

            }
        });
    }
}
