package com.noole.rps.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Score {
    public Score(@NonNull String name, int humanScore, int aiScore, Date date)
    {
        this.name = name;
        this.humanScore = humanScore;
        this.aiScore = aiScore;
        this.date = date;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "human_score")
    public int humanScore;

    @ColumnInfo(name = "ai_score")
    public int aiScore;

    @ColumnInfo(name = "date")
    public Date date;
}
