package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ShowItemsActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);

        loadItems();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                Intent intent = new Intent(ShowItemsActivity.this, ItemDetailActivity.class);
                intent.putExtra("ITEM_ID", item.getId());
                startActivity(intent);
            }
        });
    }

    private void loadItems() {
        List<Item> items = dbHelper.getAllItems();
        ItemAdapter adapter = new ItemAdapter(this, items);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }
}