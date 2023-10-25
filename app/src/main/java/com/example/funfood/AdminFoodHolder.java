package com.example.funfood;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminFoodHolder extends RecyclerView.Adapter<AdminFoodHolder.ViewHolder> {

        private List<FoodModel> dataList;
        private DatabaseReference databaseReference; // Reference to your Firebase Realtime Database

        public AdminFoodHolder(List<FoodModel> dataList, DatabaseReference databaseReference) {
            this.dataList = dataList;
            this.databaseReference = databaseReference;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            FoodModel data = dataList.get(position);
            holder.bind(data);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView nameTextView;
            private TextView descriptionTextView;
            private TextView rateTextView;
            private ImageView singleLayoutImage;
            private Button updateButton;
            private Button deleteButton;

            public ViewHolder(View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
                rateTextView = itemView.findViewById(R.id.rateTextView);
                updateButton = itemView.findViewById(R.id.updateButton);
                deleteButton = itemView.findViewById(R.id.deleteButton);
                singleLayoutImage = itemView.findViewById(R.id.singleLayoutFoodImage);
            }

            public void bind(FoodModel data) {
                nameTextView.setText(data.getName());
                descriptionTextView.setText(data.getDesc());
                rateTextView.setText("Price: "+data.getRate().toString());

                updateButton.setOnClickListener(view -> {
                    // Handle update button click, e.g., navigate to an update activity
                });
                if(!TextUtils.isEmpty(data.getImage_url())) {
                    Picasso.get().load(data.getImage_url()).into(singleLayoutImage);
                }

                deleteButton.setOnClickListener(view -> {
                    // Handle delete button click
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        FoodModel itemToDelete = dataList.get(position);
                        String itemKey = itemToDelete.getId();

                        // Remove the item from the list and the database
                        dataList.remove(position);
                        notifyItemRemoved(position);

                        // Delete the item from the database
                        databaseReference.child("foods").child(itemKey).removeValue();
                    }
                });
            }
        }


}
