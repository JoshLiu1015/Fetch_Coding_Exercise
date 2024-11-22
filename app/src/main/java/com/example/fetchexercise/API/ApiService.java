package com.example.fetchexercise.API;

import com.example.fetchexercise.model.Item;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ApiService {

    // Retrofit instance
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            // Specifies how to convert JSON responses into Java objects using
            .addConverterFactory(GsonConverterFactory.create())
            // Creates the Retrofit instance.
            .build();

    // Service interface
    public interface fetchApi {
        // Defines an endpoint (@GET("hiring.json")) that retrieves the list of items.
        @GET("hiring.json")
        Call<List<Item>> fetchItems();
    }

    // Method to create the service
    // Returns an object (fetchApi apiService) that can use to call the API methods.
    public static fetchApi create() {
        // Uses the Retrofit instance to create an implementation of the fetchApi interface.
        return retrofit.create(fetchApi.class);
    }
}
