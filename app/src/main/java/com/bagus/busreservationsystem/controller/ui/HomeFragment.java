package com.bagus.busreservationsystem.controller.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bagus.busreservationsystem.R;
import com.bagus.busreservationsystem.models.Stop;
import com.bagus.busreservationsystem.rest.APIClient;
import com.bagus.busreservationsystem.rest.APIInterface;
import com.bagus.busreservationsystem.utils.MySession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private EditText edCalendar, edStartDate, edEndDate;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private Spinner spSourceStop,spDestinationStop;
    private APIInterface apiInterface;
    private List<Integer> spinnerListId;
    private List<String> spinnerListName;
    private View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        myCalendar = Calendar.getInstance();

        edStartDate = fragmentView.findViewById(R.id.edStartDate);
        edEndDate = fragmentView.findViewById(R.id.edEndDate);
        spSourceStop = fragmentView.findViewById(R.id.spSourceStop);
        spDestinationStop = fragmentView.findViewById(R.id.spDestinationStop);


        date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(edCalendar);
        };

        edStartDate.setOnClickListener(v -> {
            showCalendar(date);
            edCalendar = edStartDate;
        });

        edEndDate.setOnClickListener(v -> {
            showCalendar(date);
            edCalendar = edEndDate;
        });

        getStops();

        return fragmentView;
    }

    private void showCalendar(DatePickerDialog.OnDateSetListener date) {
        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(new Date().getTime());
        dialog.show();
    }

    private void updateLabel(EditText editText) {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    private void getStops(){

        spinnerListId = new ArrayList<>();
        spinnerListName = new ArrayList<>();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Stop>> stopsCall = apiInterface.getStops();

        stopsCall.enqueue(new Callback<List<Stop>>() {
            @Override
            public void onResponse(Call<List<Stop>> call, Response<List<Stop>> response) {
                List<Stop> stops = response.body();
                for (int i = 0; i < stops.size(); i++) {
                    spinnerListId.add(stops.get(i).getId());
                    spinnerListName.add(stops.get(i).getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(fragmentView.getContext(), R.layout.spinner_list_item, spinnerListName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSourceStop.setAdapter(adapter);
                spDestinationStop.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Stop>> call, Throwable t) {

            }
        });
    }
}