package com.example.word_list;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wordTable")
public class Words {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String wordName;
    private String wordMeaning;
    @ColumnInfo(name = "Type")
    private String wordType;


    public Words(String wordName, String wordMeaning, String wordType) {
        this.wordName = wordName;
        this.wordMeaning = wordMeaning;
        this.wordType = wordType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWordName() {
        return wordName;
    }

    public String getWordMeaning() {
        return wordMeaning;
    }

    public String getWordType() {
        return wordType;
    }
}
