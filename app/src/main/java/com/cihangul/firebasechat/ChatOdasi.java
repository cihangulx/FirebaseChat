package com.cihangul.firebasechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatOdasi extends AppCompatActivity {

    public static String odaId="Genel";
    public static String odaIsmi="Genel";
    ListView odaList;
    DatabaseReference odaRef = FirebaseDatabase.getInstance().getReference().child("Odalar");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_odasi);
        odaList =(ListView)findViewById(R.id.listviewoda);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseListAdapter<Oda> odaAdapter =new FirebaseListAdapter<Oda>(ChatOdasi.this,Oda.class,R.layout.oda_view,odaRef) {
            @Override
            protected void populateView(View v, final Oda model, int position) {
                odaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if(getRef(position).getKey()!=null){
                            odaId=getRef(position).getKey().toString();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        odaIsmi=model.getOdaIsmi();
                        finish();
                    }
                });
                TextView text_viewOda=(TextView)v.findViewById(R.id.oda_textview);
                text_viewOda.setText(model.getOdaIsmi());
            }
        };
        odaList.setAdapter(odaAdapter);
        odaAdapter.notifyDataSetChanged();
    }
}
