package com.bagus.busreservationsystem.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bagus.busreservationsystem.R;
import com.bagus.busreservationsystem.controller.ui.ProfileFragment;
import com.bagus.busreservationsystem.controller.ui.TicketFragment;
import com.bagus.busreservationsystem.models.Ticket;
import com.bagus.busreservationsystem.models.User;
import com.bagus.busreservationsystem.models.UserLogin;
import com.bagus.busreservationsystem.models.UserPassword;
import com.bagus.busreservationsystem.models.UserProfile;
import com.bagus.busreservationsystem.rest.APIClient;
import com.bagus.busreservationsystem.rest.APIInterface;
import com.bagus.busreservationsystem.utils.MySession;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
    private TextInputEditText edPassword, edRetypePassword, edFirstName, edLastName, edMobileNumber;
    private Button btnUpdate;
    private MySession mySession;
    private APIInterface apiInterface;
    private UserPassword userPassword;
    private UserProfile userProfile;
    private String token;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mySession = new MySession(this);
        HashMap<String, String> userSession = mySession.getUserDetails();
        token = userSession.get(MySession.KEY_TOKEN);

        edPassword = findViewById(R.id.edPassword);
        edRetypePassword = findViewById(R.id.edRetypePassword);
        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastName);
        edMobileNumber = findViewById(R.id.edMobileNumber);
        btnUpdate = findViewById(R.id.btnUpdate);

        edFirstName.setText(getIntent().getStringExtra("firstName"));
        edLastName.setText(getIntent().getStringExtra("lastName"));
        edMobileNumber.setText(getIntent().getStringExtra("mobileNumber"));

        switch (getIntent().getStringExtra("showMenu")) {
            case "updatePassword":
                btnUpdate.setText("Ubah Password");
                linearLayout = findViewById(R.id.layoutUpdateProfile);
                linearLayout.setVisibility(View.GONE);

                btnUpdate.setOnClickListener(v -> {
                    String password = edPassword.getText().toString();
                    String retypePassword = edRetypePassword.getText().toString();

                    if(!password.equals(retypePassword)) {
                        Toast.makeText(this, "Password tidak sama!", Toast.LENGTH_LONG).show();
                    } else {
                        updatePassword();
                    }
                });

                break;
            case "updateProfile":
                btnUpdate.setText("Update Profile");
                linearLayout = findViewById(R.id.layoutUpdatePassword);
                linearLayout.setVisibility(View.GONE);

                btnUpdate.setOnClickListener(v -> {
                    updateProfile();
                });

                break;
        }
    }

    private void updatePassword() {
        userPassword = new UserPassword(edPassword.getText().toString());
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<User> userCall = apiInterface.updatePassword(userPassword, "Bearer " + token);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(UpdateProfileActivity.this, "Password berhasil diubah!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(UpdateProfileActivity.this, MainActivity.class);
                i.putExtra("profileFragment",1);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void updateProfile() {
        userProfile = new UserProfile(
                edFirstName.getText().toString(),
                edLastName.getText().toString(),
                edMobileNumber.getText().toString());

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<User> userCall = apiInterface.updateProfile(userProfile, "Bearer " + token);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mySession.updateProfileSession(userProfile);
                Toast.makeText(UpdateProfileActivity.this, "Profile berhasil diupdate!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(UpdateProfileActivity.this, MainActivity.class);
                i.putExtra("profileFragment",1);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}