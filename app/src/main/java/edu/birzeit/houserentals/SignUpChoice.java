package edu.birzeit.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_choice);

        Button ten_btn = (Button) findViewById(R.id.btn1);
        Button agn_btn = (Button) findViewById(R.id.btn2);
        ten_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(SignUpChoice.this, SignUpTenant.class);
                startActivity(signUpIntent);
            }
        });

        agn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(SignUpChoice.this, SignUpAgency.class);
                startActivity(signUpIntent);
            }
        });
    }
}