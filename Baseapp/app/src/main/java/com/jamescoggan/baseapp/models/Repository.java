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

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }
}
