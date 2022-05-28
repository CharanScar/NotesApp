package com.example.notesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class NotesActivity extends AppCompatActivity {

    EditText editNotes;
    int id;
    String values=null;

    @Override
    public void onBackPressed() {
        String textNotes = editNotes.getText().toString();
        if(textNotes.length()!=0) {
            if (id == -1)
                MainActivity.arr.add(textNotes);
            else if (id == -2)
                Log.i("Error", "Help Me");
            else
                MainActivity.arr.set(id, textNotes);
            MainActivity.arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MainActivity.arr);
            MainActivity.listView.setAdapter(MainActivity.arrayAdapter);
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        editNotes = findViewById(R.id.editNotes);
        id = getIntent().getIntExtra("notesID",-2);
        if(!(id<0))
            values = MainActivity.arr.get(id);
        editNotes.setText(values);
    }
}