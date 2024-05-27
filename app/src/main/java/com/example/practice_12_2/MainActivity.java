package com.example.practice_12_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private Button queryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
        queryButton = findViewById(R.id.queryButton);

        queryButton.setOnClickListener(v -> queryUser());
    }

    private void queryUser() {
        Uri contentUri = Uri.parse("content://com.example.practice12.provider/users");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(contentUri, new String[]{"_id", "name", "surname", "age", "email"}, null, null, "name ASC");
        if (cursor != null) {
            try {
                int idColumn = cursor.getColumnIndex("_id");
                int nameColumn = cursor.getColumnIndex("name");
                int addressColumn = cursor.getColumnIndex("surname");
                int ageColumn = cursor.getColumnIndex("age");
                int emailColumn = cursor.getColumnIndex("email");
                StringBuilder sb = new StringBuilder();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(idColumn);
                    String name = cursor.getString(nameColumn);
                    String address = cursor.getString(addressColumn);
                    int age = cursor.getInt(ageColumn);
                    String email = cursor.getString(emailColumn);
                    sb.append("ID: ").append(id).append("\n");
                    sb.append("Name: ").append(name).append("\n");
                    sb.append("Surname: ").append(address).append("\n");
                    sb.append("Age: ").append(age).append("\n");
                    sb.append("Email: ").append(email).append("\n\n");
                }
                resultTextView.setText(sb.toString());
            } finally {
                cursor.close(); // Важно закрыть курсор после использования
            }
        } else {
            resultTextView.setText("No user found");
        }
    }
}