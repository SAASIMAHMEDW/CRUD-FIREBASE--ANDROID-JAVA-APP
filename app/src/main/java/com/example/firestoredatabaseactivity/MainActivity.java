package com.example.firestoredatabaseactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button create,read,update,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create = findViewById(R.id.createBTN);
        read = findViewById(R.id.readBTN);
        update = findViewById(R.id.updateBTN);
        delete = findViewById(R.id.deleteBTN);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIntent();
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readIntent();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIntent();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIntent();
            }
        });

    }

    public void createIntent(){
        Intent create = new Intent(MainActivity.this,CreateActivity.class);
        startActivity(create);
    }

    public void readIntent(){
        Intent read = new Intent(MainActivity.this,ReadActivity.class);
        startActivity(read);
    }

    public void updateIntent(){
        Intent update = new Intent(MainActivity.this,UpdateActivity.class);
        startActivity(update);
    }

    public void deleteIntent(){
        Intent delete = new Intent(MainActivity.this,DeleteActivity.class);
        startActivity(delete);
    }

}