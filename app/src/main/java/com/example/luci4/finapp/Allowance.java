package com.example.luci4.finapp;

public class Allowance {

    String id;
    int amtallowance;

    public Allowance(){

    }

    public Allowance(String id, int amtallowance) {
        this.id = id;
        this.amtallowance = amtallowance;
    }


    public String getId() {
        return id;
    }

    public int getAmtallowance() {
        return amtallowance;
    }
}
