package com.example.mycalc;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText textOut;
    Boolean isNew = true;
    String operator;
    String lastValue;

    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textOut = findViewById(R.id.textOut);

        mediaPlayer = MediaPlayer.create(this, R.raw.click_sound);
    }

    public void clickNumber(View view) {
        mediaPlayer.start();
        if (isNew){
            textOut.setText("");
        }
        isNew = false;
        String number = textOut.getText().toString();
        switch (view.getId()){
            case R.id.but_1: number +="1"; break;
            case R.id.but_2: number +="2"; break;
            case R.id.but_3: number +="3"; break;
            case R.id.but_4: number +="4"; break;
            case R.id.but_5: number +="5"; break;
            case R.id.but_6: number +="6"; break;
            case R.id.but_7: number +="7"; break;
            case R.id.but_8: number +="8"; break;
            case R.id.but_9: number +="9"; break;
            case R.id.but_0:
                if (number.equals("0") || number.equals("")){
                    isNew =true;
                    number="0";
                }
                else
                    number +="0";
                break;
            case R.id.but_change:
                if (!number.equals("") && !number.equals("0")) {
                    if (number.charAt(0) != '-')
                        number = "-" + number;
                    else
                        number = number.substring(1);
                }
                else {
                    isNew = true;
                    number="0";
                }
                break;
            case R.id.but_comma: if (!containsComma(number)) number +="."; break;
            case R.id.but_del: number = removeLastDigit(number); break;

        }
        textOut.setText(number);
    }



    public void operation(View view) {
        mediaPlayer.start();
        isNew = true;
        lastValue = textOut.getText().toString();
        switch (view.getId()) {
            case R.id.but_divide: operator = "/"; break;
            case R.id.but_multiply: operator = "*"; break;
            case R.id.but_minus: operator = "-"; break;
            case R.id.but_plus: operator = "+"; break;
        }
    }

    public void operationEqual(View view) {
        mediaPlayer.start();
        String newValue = textOut.getText().toString();
        Double result = 0.0;

        //Check if divide by 0 and escape from method
        if (Integer.parseInt(newValue)==0 && operator.equals("/")){
            Toast.makeText(MainActivity.this, "Can't divide by 0", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (operator){
            case "-": result = Double.parseDouble(lastValue) - Double.parseDouble(newValue); break;
            case "+": result = Double.parseDouble(lastValue) + Double.parseDouble(newValue); break;
            case "/": result = Double.parseDouble(lastValue) / Double.parseDouble(newValue); break;
            case "*": result = Double.parseDouble(lastValue) * Double.parseDouble(newValue); break;
        }
        textOut.setText(String.valueOf(result));
    }

    public void operationAC(View view) {
        mediaPlayer.start();
        textOut.setText("0");
        isNew = true;
    }

    private boolean containsComma(String str){
        return str.contains(".");
    }

    private String removeLastDigit(String str) {
        if (str != null && str.length() > 0 ) {
            int min = 1;
            if (str.charAt(0) == '-' || str.charAt(0) == '.') min=2;
            if(str.length()>min)
                str = str.substring(0, str.length() - 1);
            else {
                str="0";
                isNew = true;
            }
        }
        return str;
    }
}