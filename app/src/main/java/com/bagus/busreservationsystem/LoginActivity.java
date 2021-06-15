package com.bagus.busreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bagus.busreservationsystem.rest.APIClient;
import com.bagus.busreservationsystem.rest.APIInterface;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView btnSignup;
    private Intent intent;

    private APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        apiInterface = APIClient.getClient().create(APIInterface.class);


        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        });

        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(v -> {
            intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}