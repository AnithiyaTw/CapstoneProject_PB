package com.example.capstoneproject_pb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteList.class}, version = 2,exportSchema = true)

public abstract class FavoriteDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();
}