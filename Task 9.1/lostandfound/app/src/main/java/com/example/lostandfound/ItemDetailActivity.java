package com.example.lostandfound;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {
    private TextView tvTitle, tvDate, tvLocation, tvDescription, tvPhone;
    private Button btnRemove;
    private DatabaseHelper dbHelper;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        dbHelper = new DatabaseHelper(this);
        itemId = getIntent().getIntExtra("ITEM_ID", -1);

        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvLocation = findViewById(R.id.tvLocation);
        tvDescription = findViewById(R.id.tvDescription);
        tvPhone = findViewById(R.id.tvPhone);
        btnRemove = findViewById(R.id.btnRemove);

        loadItemDetails();

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleted = dbHelper.deleteItem(itemId);
                if (deleted) {
                    Toast.makeText(ItemDetailActivity.this, "Item removed", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ItemDetailActivity.this, "Failed to remove item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadItemDetails() {
        Item item = dbHelper.getItem(itemId);
        if (item != null) {
            tvTitle.setText(item.getPostType() + ": " + item.getName());
            tvDate.setText("Date: " + item.getDate());
            tvLocation.setText("Location: " + item.getLocation());
            tvDescription.setText("Description: " + item.getDescription());
            tvPhone.setText("Contact: " + item.getPhone());
        }
    }
}