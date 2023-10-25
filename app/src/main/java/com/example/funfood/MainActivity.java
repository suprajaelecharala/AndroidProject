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

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Toolbar mToolbar;

    private RecyclerView recyclerView;
    private AdminFoodHolder adapter;
    private List<FoodModel> dataList = new ArrayList<>();
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();
        mToolbar= findViewById(R.id.cartToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("FunFood");
        mDatabase= FirebaseDatabase.getInstance().getReference();


        recyclerView = findViewById(R.id.foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminFoodHolder(dataList, mDatabase);
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
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_admin_logout){
            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId()==R.id.menu_add){
            Intent addIntent= new Intent(MainActivity.this, AddFoodActivity.class);
            startActivity(addIntent);
        }

        if(item.getItemId()==R.id.menu_orders){
            Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity( orderIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser==null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }else{
            String uid= currentUser.getUid();
            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userType=snapshot.child("type").getValue().toString();
                    if (userType.equals("user")){
                        Intent userIntent = new Intent(MainActivity.this,UserMainActivity.class);
                        startActivity(userIntent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}