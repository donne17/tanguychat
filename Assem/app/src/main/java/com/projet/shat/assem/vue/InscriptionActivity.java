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

import java.util.HashMap;

public class InscriptionActivity extends AppCompatActivity {

    EditText nom,email,pass,conf;
    Button creation;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        toolbar = findViewById(R.id.mytool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Creation de compte");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nom = findViewById(R.id.id_nom);
        email = findViewById(R.id.id_email);
        pass = findViewById(R.id.id_pass);
        conf = findViewById(R.id.id_conf);
        creation = findViewById(R.id.inscription);

        auth = FirebaseAuth.getInstance();

        controle(creation);



    }

    private void inscription(final String myname, String mymail, String mypass)
    {
          auth.signInWithEmailAndPassword(mymail,mypass)
                  .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()){
                              user = auth.getCurrentUser();
                              String userid = user.getUid();
                              reference = FirebaseDatabase.getInstance().getReference("User").child(userid);

                              HashMap<String,String> hashMap = new HashMap<>();
                              hashMap.put("id",userid);
                              hashMap.put("username",myname);
                              hashMap.put("imageURL","default");

                              reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                      Intent intent = new Intent(InscriptionActivity.this,WorkActivity.class);
                                      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                                      startActivity(intent);
                                      finish();
                                  }
                              });
                          }else {
                              Toast.makeText(InscriptionActivity.this,"Email ou mot de passe incorrecte",Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
    }

    private void controle(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String monnom = nom.getText().toString();
                String monmail = email.getText().toString();
                String monpasse = pass.getText().toString();
                String confirmation = conf.getText().toString();

                if (TextUtils.isEmpty(monnom)||TextUtils.isEmpty(monmail)||TextUtils.isEmpty(monpasse)||TextUtils.isEmpty(confirmation)){
                    Toast.makeText(InscriptionActivity.this,"Tous les champs sont obligatoire merci",Toast.LENGTH_SHORT).show();
                }else if (monpasse.length()<8||monpasse!=confirmation){
                    Toast.makeText(InscriptionActivity.this,"Le mot de passe est de 8 caracteres au minimum ou la confirmation incorrecte",Toast.LENGTH_SHORT).show();
                }else {
                    inscription(monnom,monmail,monpasse);
                }
            }
        });
    }
}
