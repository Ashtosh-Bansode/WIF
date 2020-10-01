package com.example.wif;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    EditText editText;
    EditText itemquantity;
    Button button;
    ListView listView;
    Button viewlist;
    ArrayList<String> itemsInFridge = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String key = "Key";


    public static final String EXTRA_MESSAGE = "com.example.WIF.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        //Switching to view list activity
        viewlist = findViewById(R.id.seeListBut);
        viewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View v) {
                Intent intent = new Intent(AddItemActivity.this, View_Items.class);
                startActivity(intent);
            }
        });
    }
    public void itemAdded(View view) {
        Context context = getApplicationContext();
        editText=findViewById(R.id.itemName);
        itemquantity=findViewById(R.id.itemQuantity);
        // Add item to array

        // Save array internally
        SharedPreferences shref;
        SharedPreferences.Editor editor;

        shref = context.getSharedPreferences("itemsInFridge", Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String response=shref.getString(key , "");
        if(response!="") {
            itemsInFridge = gson.fromJson(response,
                    new TypeToken<List<String>>() {
                    }.getType());
        }
        itemsInFridge.add(editText.getText().toString()+":"+itemquantity.getText().toString());
        editText.setText("");
        Gson gson1 = new Gson();
        String json = gson1.toJson(itemsInFridge);
        editor = shref.edit();
        editor.remove(key).commit();
        editor.putString(key, json);
        editor.commit();
        // Show toast
        CharSequence text = "Item Added!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}