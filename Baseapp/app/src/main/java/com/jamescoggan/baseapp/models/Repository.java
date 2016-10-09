package com.jamescoggan.baseapp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Repository extends RealmObject {

    @PrimaryKey
    private long id;

    String name;
    String fullName;
    String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
