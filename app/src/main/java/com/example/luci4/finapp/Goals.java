package com.example.luci4.finapp;

public class Goals {

    private String id;
    private String name;
    private Double amount;
    private String setdate;
    private String goaldate;

    public Goals(){

    }

    public Goals(String id, String name, Double amount, String setdate, String goaldate) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.setdate = setdate;
        this.goaldate = goaldate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public String getSetdate() {
        return setdate;
    }

    public String getGoaldate() {
        return goaldate;
    }
}
