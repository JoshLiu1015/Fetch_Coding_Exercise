package com.example.fetchexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import com.example.fetchexercise.model.Item;
import com.example.fetchexercise.viewModel.MainViewModel;


import java.util.List;

import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference to the loading TextView
        TextView loadingText = findViewById(R.id.loadingText);

        // Initialize ViewModel
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        // Observe items data
        mainViewModel.getItems().observe(this, items -> {
            if (items != null) {
                // Hide loading text
                loadingText.setVisibility(TextView.GONE);

                populateScrollView(items);
            }
        });

        // Trigger data fetch
        mainViewModel.fetchItems();

    }



    private void populateScrollView(List<Item> items) {
        LinearLayout container = findViewById(R.id.container);

        // Track the current listId
        int currentListId = -1;


        for (Item item : items) {
            // Check if the listId has changed
            if (item.getListId() != currentListId) {
                currentListId = item.getListId();

                // Create a CardView for the group title
                CardView groupCardView = new CardView(this);
                groupCardView.setCardElevation(10);
                groupCardView.setRadius(15);
                groupCardView.setCardBackgroundColor(Color.parseColor("#F8F9FA"));

                // dynamically created views do not have layout parameters assigned,
                // so need to explicitly set them.
                // The LayoutParams ensure the TextView respects the layout constraints defined for the LinearLayout.
                groupCardView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                // Create a title for each group
                TextView textView = new TextView(this);
                textView.setText("Group " + item.getListId());
                textView.setTextColor(Color.parseColor("#1E88E5"));
                textView.setTextSize(30);
                textView.setPadding(30, 30, 30, 30);

                groupCardView.addView(textView);

                // Add the TextView to the container
                container.addView(groupCardView);
            }

            // Add a TextView for the item
            TextView textView = new TextView(this);
            textView.setText(item.getName());
            textView.setTextSize(20);
            textView.setPadding(15, 10, 15, 10);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            container.addView(textView);
        }
    }
}

