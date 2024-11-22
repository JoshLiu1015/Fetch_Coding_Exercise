package com.example.fetchexercise.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fetchexercise.API.ApiService;
import com.example.fetchexercise.model.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData<>();

    public LiveData<List<Item>> getItems() {
        return itemsLiveData;
    }


    public void fetchItems() {
        // Returns an object (fetchApi apiService) that can use to call the API methods.
        ApiService.fetchApi apiService = ApiService.create();
        apiService.fetchItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> items = response.body();

                    // filteredItems stores valid data
                    List<Item> filteredItems = new ArrayList<>();

                    for (Item item : items) {
                        // Filter out any items where "name" is blank or null
                        if (item.getName() == null || item.getName().equals("")) {
                            continue;
                        }
                        filteredItems.add(item);
                    }

                    // Sort by listId, then by name
                    Collections.sort(filteredItems, Comparator.comparingInt(Item::getListId)
                            .thenComparingInt(item -> {
                                // Extract numeric part from the name
                                String name = item.getName();
                                // split "Item" and number
                                String[] splitList = name.split(" ");
                                try {
                                    // second element is the number
                                    Integer num = Integer.parseInt(splitList[1]);
                                    return num;
                                } catch (NumberFormatException e) {
                                    // If the number extraction fails, fallback to 0
                                    return 0;
                                }
                            }));
                    itemsLiveData.setValue(filteredItems);
                } else {
                    // Empty list on failure
                    itemsLiveData.setValue(new ArrayList<>());
                }

            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                // Empty list on failure
                itemsLiveData.setValue(new ArrayList<>());

            }
        });
    }
}
