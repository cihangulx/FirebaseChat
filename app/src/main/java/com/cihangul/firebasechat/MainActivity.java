package com.cihangul.firebasechat;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private static int RC_SIGN_IN =1;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth mAuth;
    Button gonder;
    EditText mesajGonder;
    ListView listView;
    DatabaseReference mesajRef= FirebaseDatabase.getInstance().getReference().child("Odalar").child(ChatOdasi.odaId).child("Mesajlar");
    float x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                   Toast.makeText(getApplicationContext(),"Giriş Yapıldı",Toast.LENGTH_LONG).show();
                } else {

                }
            }
        };
        mAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(),"Giriş Başarısız",Toast.LENGTH_LONG).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mesajGonder=(EditText)findViewById(R.id.mesajGonder) ;
        gonder=(Button)findViewById(R.id.gonder);
        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(mesajGonder.getText())){
                    String mesaj =mesajGonder.getText().toString();
                    DatabaseReference yeniMesaj =mesajRef.push();
                    yeniMesaj.child("mesajMetni").setValue(mesaj);
                    if(mAuth.getCurrentUser().getDisplayName()!=null){
                        yeniMesaj.child("mesajSahibi").setValue(mAuth.getCurrentUser().getDisplayName());
                        yeniMesaj.child("mesajSahibiResmi").setValue(mAuth.getCurrentUser().getPhotoUrl().toString());
                        mesajGonder.setText("");
                    }

                }
            }
        });
        listView=(ListView)findViewById(R.id.listview);
        x =((getWindowManager().getDefaultDisplay().getWidth()-100));

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseListAdapter<Mesaj> listAdapter = new FirebaseListAdapter<Mesaj>(MainActivity.this,Mesaj.class,R.layout.mesaj_view,mesajRef) {
            @Override
            protected void populateView(View v, Mesaj model, int position) {

                ImageView userImage=(ImageView)v.findViewById(R.id.user_image);
                TextView mesajMetni =(TextView)v.findViewById(R.id.mesaj_metni);
                TextView mesajSaati =(TextView)v.findViewById(R.id.mesaj_saati);

                if(mAuth.getCurrentUser().getDisplayName()!=null){
               if(model.getMesajSahibi().toString().equals(mAuth.getCurrentUser().getDisplayName())){
                    mesajSaati.setX(10);
                    userImage.setX(x);
                }}
                Picasso.with(MainActivity.this).load(model.getMesajSahibiResmi()).into(userImage);
                mesajMetni.setText(model.getMesajMetni());
                mesajSaati.setText(model.getMesajSaati());
            }
        };
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.ekle) {
            startActivity(new Intent(getApplicationContext(),OdaEkle.class));
        } else if (id == R.id.odalar) {
            startActivity(new Intent(getApplicationContext(),ChatOdasi.class));
        }else if(id == R.id.giris){
            signIn();
        }else if(id==R.id.cikis){
            FirebaseAuth.getInstance().signOut();
        }


        return super.onOptionsItemSelected(item);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {

            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
       AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Giriş Başarısız",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public static void odaEkle(String odaIsmi){
        DatabaseReference odaEkleRoot = FirebaseDatabase.getInstance().getReference().child("Odalar");
        DatabaseReference odaEkle=odaEkleRoot.push();
        odaEkle.child("odaIsmi").setValue(odaIsmi.toString());
    }
}
