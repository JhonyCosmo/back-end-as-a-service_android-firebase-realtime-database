package com.cosmo.firebaserealtime;

public class Person {

    private String name;
    private String lastName;
    private String key;

    public Person(){

    }

    public Person(String name,  String lastName,String key) {
        this.name = name;
        this.lastName = lastName;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setFullName(String fullName) {
        this.name = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
