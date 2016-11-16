package com.cihangul.firebasechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OdaEkle extends AppCompatActivity {

    EditText editText;
    Button odaEkle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oda_ekle);
        editText=(EditText)findViewById(R.id.oda_ismi);
        odaEkle=(Button)findViewById(R.id.odaEkle);
        odaEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.odaEkle(editText.getText().toString());
            }
        });
    }
}
