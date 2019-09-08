package com.example.predictweather.weatherprediction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.predictweather.R;

public class WeatherByAddress extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;
    Button button;
    ListView listView;
    String[] strings;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_choose_addeess);
        autoCompleteTextView = findViewById(R.id.actvx);
        listView = findViewById(R.id.lv);
        strings = getResources().getStringArray(R.array.list);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, strings);
        listView.setAdapter(arrayAdapter);
        button = findViewById(R.id.btny);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherByAddress.this, ShowWeatherByAddress.class);
                intent.putExtra("address", autoCompleteTextView.getText() + "");
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                autoCompleteTextView.setText(strings[i]);
            }
        });
    }
}
