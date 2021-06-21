package com.bagus.busreservationsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bagus.busreservationsystem.models.User;
import com.bagus.busreservationsystem.models.UserRegister;
import com.bagus.busreservationsystem.rest.APIClient;
import com.bagus.busreservationsystem.rest.APIInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private TextView btnLogin;
    private Intent intent;

    private MaterialButton btnRegister;
    private APIInterface apiInterface;
    private TextInputEditText edEmail, edFirstName, edLastName, edMobileNumber, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnRegister = findViewById(R.id.btnRegister);
        edEmail = findViewById(R.id.edEmail);
        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastName);
        edMobileNumber = findViewById(R.id.edMobileNumber);
        edPassword = findViewById(R.id.edPassword);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        btnRegister.setOnClickListener(v -> {
            ArrayList<String> role = new ArrayList<String>();
            role.add("user");
            UserRegister userRegister = new UserRegister(
                    edEmail.getText().toString(),
                    edFirstName.getText().toString(),
                    edLastName.getText().toString(),
                    edMobileNumber.getText().toString(),
                    edPassword.getText().toString(),role);
            Call<User> userCall = apiInterface.createUser(userRegister);
            userCall.enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user != null){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setTitle("information");
                        builder.setMessage("Registrasi Berhasil Dilakukan");
                        builder.setPositiveButton("Ok",(dialog, which) -> {
                            Intent home=new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(home);
                            finish();
                        });

                        builder.setCancelable(false);
                        builder.show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "No internet Access", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}