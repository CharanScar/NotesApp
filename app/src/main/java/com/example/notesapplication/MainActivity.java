package com.example.notesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    static ListView listView;
    static ArrayList<String> arr;
    static ArrayAdapter<String> arrayAdapter;
    String objAsString;
    SharedPreferences sharedPreferences;


    void processString() {
        String process[] = objAsString.split(",");
        int len = process.length;
        process[0] = process[0].substring(1);
        process[len - 1] = process[len - 1].substring(0, process[len - 1].length() - 1);
        for (int i = 0; i < len; i++) {
            process[i].trim();
            arr.add(process[i]);
        }
    }

    @Override
    public void onBackPressed() {
        if (arr.size() == 0)
            objAsString = null;
        else
            objAsString = arr.toString();
        sharedPreferences.edit().putString("data", objAsString).apply();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        intent = new Intent(this, NotesActivity.class);
        intent.putExtra("notesID", -1);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.notesapplication", Context.MODE_PRIVATE);
        listView = (ListView) findViewById(R.id.listView);
        arr = new ArrayList<String>();
        objAsString = sharedPreferences.getString("data", null);
        if (objAsString == null) {
            arr.add("Example Note!! Click to Edit");
        } else {
            processString();
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(getApplicationContext(), NotesActivity.class);
                intent.putExtra("notesID", i);
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int in, long l) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure you want to delete this Note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                arr.remove(in);
                                arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arr);
                                listView.setAdapter(arrayAdapter);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

    }
}