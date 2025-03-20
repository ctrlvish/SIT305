package com.example.w2wrk;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // create the variables first, make sure to tab to auto-import
    Spinner metricSpinner;
    Spinner convertFrom;
    Spinner convertTo;
    EditText valueInput;
    Button convertButton;
    TextView resultTextView;

    //array for spinner
    private String[] metricTypes;
    private String[] lengthUnits;
    private String[] weightUnits;
    private String[] tempUnits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //load string arrays from resources
        metricTypes = getResources().getStringArray(R.array.metric_types);
        lengthUnits = getResources().getStringArray(R.array.length_units);
        weightUnits = getResources().getStringArray(R.array.weight_units);
        tempUnits = getResources().getStringArray(R.array.temp_units);

        //assign the variables to the IDs to link to XML file
        metricSpinner = findViewById(R.id.metricSpinner);
        convertFrom = findViewById(R.id.convertFrom);
        convertTo = findViewById(R.id.convertTo);
        valueInput = findViewById(R.id.valueInput);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        //metric spinner setup using array adapter
        ArrayAdapter<String> metricAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, metricTypes);
        metricAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metricSpinner.setAdapter(metricAdapter);

        //setup event listener for metric spinner
        metricSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUnitSpinners(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            //do nothing
            }
        });

        //set a default view for metric
        //updateUnitSpinners(2);

        convertButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String inputString = valueInput.getText().toString();

                //validation before anything
                if (inputString.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    //save user input to inputValue
                    double inputValue = Double.parseDouble(inputString);

                    //get selected metric type
                    int metricPosition = metricSpinner.getSelectedItemPosition();
                    String fromUnit = convertFrom.getSelectedItem().toString();
                    String toUnit = convertTo.getSelectedItem().toString();

                    if(fromUnit.equals(toUnit)){
                        Toast.makeText(MainActivity.this, "Conversion units cannot be the same", Toast.LENGTH_SHORT).show();
                    }

                    double result = performConversion(metricPosition, fromUnit, toUnit, inputValue);

                    //perform conversion
                    resultTextView.setText(String.format("Result: %.2f %s", result, toUnit));

                }catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //method to update unit spinners based on selected metric type
    private void updateUnitSpinners(int metricPosition){
        String[] units;

        //select appropriate units based on metric type
        switch (metricPosition){
            case 0:
                units = lengthUnits;
                break;
            case 1:
                units = weightUnits;
                break;
            case 2:
            default:
                units = tempUnits;
                break;
        }

        //update the convert from and convert to spinners
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        convertFrom.setAdapter(fromAdapter);

        ArrayAdapter<String> toAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        convertTo.setAdapter(toAdapter);
    }

    //method to perform conversion
    private double performConversion(int metricType, String fromUnit, String toUnit, double value){
        switch(metricType){
            case 0:
                return convertLength(fromUnit, toUnit, value);
            case 1:
                return convertWeight(fromUnit, toUnit, value);
            case 2:
                return convertTemp(fromUnit, toUnit, value);
            default:
                return 0.0;
        }
    }

    //method to convert length
    private double convertLength(String fromUnit, String toUnit, double value){
        double cmValue = 0.0;

        //convert the input value to cm
        switch (fromUnit){
            case "Inches":
                cmValue = value * 2.54;
                break;
            case "Feet":
                cmValue = value * 30.48;
                break;
            case "Yards":
                cmValue = value * 91.44;
                break;
            case "Miles":
                cmValue = value * 160934;
                break;
            case "Centimeters":
                cmValue = value;
                break;
            case "Kilometers":
                cmValue = value * 100000;
                break;
        }

        //convert from cm to target unit
        switch (toUnit) {
            case "Inches":
                return cmValue / 2.54;
            case "Feet":
                return cmValue / 30.48;
            case "Yards":
                return cmValue / 91.44;
            case "Miles":
                return cmValue / 160934;
            case "Centimeters":
                return cmValue;
            case "Kilometers":
                return cmValue / 100000;
            default:
                return 0.0;
        }
    }
    //method to convert weight
    private double convertWeight(String fromUnit, String toUnit, double value){
        double gValue = 0.0;

        //convert input to grams
        switch (fromUnit){
            case "Pounds":
                gValue = value * 453.592;
                break;
            case "Ounces":
                gValue = value * 28.3495;
                break;
            case "Tons":
                gValue = value * 907185;
                break;
            case "Kilograms":
                gValue = value * 1000;
                break;
            case "Grams":
                gValue = value;
                break;
        }

        //convert grams to target unit
        switch (toUnit){
            case "Pounds":
                return gValue / 453.592;
            case "Ounces":
                return gValue / 28.3495;
            case "Tons":
                return gValue / 907185;
            case "Kilograms":
                return gValue / 1000;
            case "Grams":
                return gValue;
            default:
                return 0.0;
        }
    }
    //method to convert temperature
    private double convertTemp(String fromUnit, String toUnit, double value){
        double cValue = 0.0;

        //convert to celcius
        switch (fromUnit){
            case "Celsius":
                cValue = value;
                break;
            case "Fahrenheit":
                cValue = (value - 32) * 1.8;
                break;
            case "Kelvin":
                cValue = value - 273.15;
                break;
        }

        switch (toUnit){
            case "Celsius":
                return cValue;
            case "Fahrenheit":
                return (cValue * 1.8) + 32;
            case "Kelvin":
                return cValue + 273.15;
            default:
                return 0.0;
        }
    }
}