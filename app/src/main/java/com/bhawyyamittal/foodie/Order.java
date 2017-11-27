package com.bhawyyamittal.foodie;

/**
 * Created by BHAWYYA MITTAL on 26-11-2017.
 */

public class Order {
    String username,itemname;
    public Order(){

    }

    public Order(String user, String item){
        this.itemname = item;
        this.username = user;

    }

    public String getUsername() {
        return username;
    }

    public String getItemname() {
        return itemname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}
