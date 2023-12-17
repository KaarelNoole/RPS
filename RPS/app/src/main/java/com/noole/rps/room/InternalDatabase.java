package com.noole.rps.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Score.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class InternalDatabase extends RoomDatabase {
    public abstract ScoreDao scoreDao();
}