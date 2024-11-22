package com.example.fetchexercise.model;

import com.google.gson.annotations.SerializedName;
public class Item {
    @SerializedName("id")
    private int id;

    @SerializedName("listId")
    private int listId;

    @SerializedName("name")
    private String name;


    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

}
