package com.example.firestoredatabaseactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReadedDataActivity extends AppCompatActivity {
    Button goHomeReadBTN;
    TextView readedNameTV,readedAgeTV,readedEmailTV,readedPasswordTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readed_data);

        setData();

        goHomeReadBTN = findViewById(R.id.goHomeReadBTN);
        goHomeReadBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHomeRead();
            }
        });
    }

    public void setData(){
        readedNameTV = findViewById(R.id.readedNameTV);
        readedAgeTV = findViewById(R.id.readedAgeTV);
        readedEmailTV = findViewById(R.id.readedEmailTV);
        readedPasswordTV = findViewById(R.id.readedPasswordTV);

        String name,age,email,password;

        Intent dataIntent = getIntent();
        name = dataIntent.getStringExtra(ReadActivity.KEY_NAME);
        age = dataIntent.getStringExtra(ReadActivity.KEY_AGE);
        email = dataIntent.getStringExtra(ReadActivity.KEY_EMAIL);
        password = dataIntent.getStringExtra(ReadActivity.KEY_PASSWORD);

        readedNameTV.setText(name);
        readedAgeTV.setText(age);
        readedEmailTV.setText(email);
        readedPasswordTV.setText(password);

    }
   public void goHomeRead(){
       Intent home = new Intent(ReadedDataActivity.this,MainActivity.class);
       startActivity(home);
    }
}