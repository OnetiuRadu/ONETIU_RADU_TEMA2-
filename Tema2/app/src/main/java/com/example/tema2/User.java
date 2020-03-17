package com.example.tema2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "mark")
    public int mark;

    public User(String firstName, String lastName, int mark) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mark = mark;
    }
}