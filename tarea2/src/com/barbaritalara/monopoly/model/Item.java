package com.barbaritalara.monopoly.model;

public class Item {
    private int id;
    private String name;
    public Item(int i, String name) {
        this.id = i;
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
