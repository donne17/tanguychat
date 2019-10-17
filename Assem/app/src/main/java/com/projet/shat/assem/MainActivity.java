package com.projet.shat.assem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projet.shat.assem.vue.ConnexionActivity;
import com.projet.shat.assem.vue.InscriptionActivity;
import com.projet.shat.assem.vue.WorkActivity;

public class MainActivity extends AppCompatActivity {

    Button connexion,inscription;
    FirebaseUser user;

    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null){
            Intent intent = new Intent(MainActivity.this, WorkActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connexion = findViewById(R.id.connexion);
        inscription = findViewById(R.id.inscription);
        connection(connexion);
        inscript(inscription);
    }

    private void connection(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConnexionActivity.class);
            }
        });
    }

    private void inscript(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InscriptionActivity.class);
            }
        });
    }
}
