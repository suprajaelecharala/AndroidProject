package com.example.funfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserMainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Toolbar mToolbar;

    private RecyclerView recyclerView;
    private UserFoodAdapter adapter;
    private List<FoodModel> dataList = new ArrayList<>();
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        mAuth= FirebaseAuth.getInstance();
        mToolbar= findViewById(R.id.cartToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("FunFood");
        mDatabase= FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.userFoodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserFoodAdapter(dataList);
        recyclerView.setAdapter(adapter);

        DatabaseReference foodDatabase =mDatabase.child("foods");
        foodDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataList.clear();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FoodModel data = snapshot.getValue(FoodModel.class);
                        data.setId(snapshot.getKey());
                        dataList.add(data);
                    }
                } catch (DatabaseException e) {
                    Log.e("FirebaseError", "Error parsing data: " + e.getMessage());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.user_cart){
            startActivity(new Intent(UserMainActivity.this, CartActivity.class));

        }
        if(item.getItemId()==R.id.menu_user_logout){
            mAuth.signOut();
            Intent loginIntent = new Intent(UserMainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}