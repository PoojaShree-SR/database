package com.example.userdatabaseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editEmail;
    Button btnAdd, btnView, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.editTextName);
        editEmail = findViewById(R.id.editTextEmail);
        btnAdd = findViewById(R.id.buttonAdd);
        btnView = findViewById(R.id.buttonView);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);

        addUser();
        viewAllUsers();
        updateUser();
        deleteUser();
    }

    public void addUser() {
        btnAdd.setOnClickListener(v -> {
            boolean isInserted = myDb.insertData(editName.getText().toString(), editEmail.getText().toString());
            if (isInserted)
                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
        });
    }

    public void viewAllUsers() {
        btnView.setOnClickListener(v -> {
            Cursor res = myDb.getAllData();
            if (res.getCount() == 0) {
                showMessage("Error", "No data found");
                return;
            }
            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                buffer.append("ID: ").append(res.getString(0)).append("\n");
                buffer.append("Name: ").append(res.getString(1)).append("\n");
                buffer.append("Email: ").append(res.getString(2)).append("\n\n");
            }
            showMessage("User Data", buffer.toString());
        });
    }

    public void updateUser() {
        btnUpdate.setOnClickListener(v -> {
            boolean isUpdate = myDb.updateData("1", editName.getText().toString(), editEmail.getText().toString());
            if (isUpdate)
                Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
        });
    }

    public void deleteUser() {
        btnDelete.setOnClickListener(v -> {
            Integer deletedRows = myDb.deleteData("1");
            if (deletedRows > 0)
                Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
