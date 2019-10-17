package com.projet.shat.assem.vue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projet.shat.assem.R;

public class ConnexionActivity extends AppCompatActivity {

    EditText email,pass;
    Button conexion;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        toolbar = findViewById(R.id.mytool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Connexion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = findViewById(R.id.id_email);
        pass = findViewById(R.id.id_pass);
        conexion = findViewById(R.id.connexion);
        auth = FirebaseAuth.getInstance();

        controle(conexion);
    }

    private void connect(String myemail,String mypass){
       auth.signInWithEmailAndPassword(myemail,mypass).addOnCompleteListener(
               new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Intent intent = new Intent(ConnexionActivity.this,WorkActivity.class);
                           intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                           startActivity(intent);
                           finish();
                       }else {
                           Toast.makeText(ConnexionActivity.this,"Connexion imposible",Toast.LENGTH_SHORT).show();
                       }
                   }
               }
       );
    }

    private void controle(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monemail = email.getText().toString();
                String monpass = pass.getText().toString();

                if (TextUtils.isEmpty(monemail)|| TextUtils.isEmpty(monpass)){
                    Toast.makeText(ConnexionActivity.this,"Tous les champs sont obligatoire",Toast.LENGTH_SHORT).show();
                }else {
                    connect(monemail,monpass);
            }
        }
    });
}
}