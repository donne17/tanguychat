package com.projet.shat.assem.vue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projet.shat.assem.MainActivity;
import com.projet.shat.assem.R;
import com.projet.shat.assem.model.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkActivity extends AppCompatActivity {

    CircleImageView profile;
    TextView username;
    FirebaseUser user;
    DatabaseReference reference;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        toolbar = findViewById(R.id.mytool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        profile = findViewById(R.id.profile);
        username = findViewById(R.id.id_username);



        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if(user.getImageURL().equals("defauil")){
                    profile.setImageResource(R.mipmap.ic_launcher);
                }else {

                    Glide.with(WorkActivity.this).load(user.getImageURL()).into(profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        TableLayout layout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(WorkActivity.this, MainActivity.class));
                finish();
                return true;
        }

        return false;
    }

}
