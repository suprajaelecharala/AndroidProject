package com.example.funfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AddFoodActivity extends AppCompatActivity {
    private TextInputLayout nameInput, descInput,rateInput;
    private Toolbar mToolbar;
    private DatabaseReference mDataBase;
    ImageView foodImage;

    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private Uri imageUri;
    String DownloadUrl="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        mToolbar = findViewById(R.id.cartToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Food");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDataBase= FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        nameInput = findViewById(R.id.foodNameInput);
        descInput = findViewById(R.id.foodDescriptionInput);
        rateInput = findViewById(R.id.foodRateInput);
        foodImage = findViewById(R.id.foodImageBtn);
        foodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Upload the selected image to Firebase Storage
            StorageReference imageRef = storageRef.child("images");
            UploadTask uploadTask = imageRef.putFile(imageUri);

            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        // Image uploaded successfully
                        // You can also get the download URL of the uploaded image
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                DownloadUrl=downloadUri.toString();
                                // Set the downloaded image URL to the ImageView
                                Picasso.get().load(downloadUri).into(foodImage);
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_done){
            AddFoodToDb();


        }
        return super.onOptionsItemSelected(item);
    }

    private void AddFoodToDb() {
        String name = nameInput.getEditText().getText().toString().trim();
        String desc = descInput.getEditText().getText().toString().trim();
        String rate = rateInput.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(rate)){
            Toast.makeText(AddFoodActivity.this, "All the fields are required", Toast.LENGTH_SHORT).show();
        }else{

            Map<String, Object> foodMap = new HashMap<>();
            foodMap.put("name",name);
            foodMap.put("desc",desc);
            foodMap.put("rate",rate);
            foodMap.put("image_url",DownloadUrl);
            mDataBase.child("foods").push().setValue(foodMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isComplete()){
                        Toast.makeText(AddFoodActivity.this, "Successfully added food", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(AddFoodActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }else{
                        Toast.makeText(AddFoodActivity.this, "Cannot add food. Please retry after sometime", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}