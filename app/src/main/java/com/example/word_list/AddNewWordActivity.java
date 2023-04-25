package com.example.word_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddNewWordActivity extends AppCompatActivity {

    private EditText wordEditText;
    private EditText meaningEditText;
    private EditText typeEditText;

    private boolean editMode;

    private int mID;

    public static final String EXTRA_ID = "com.example.wordlist.extraid";

    public static final String EXTRA_WORD = "com.example.wordlist.word";
    public static final String EXTRA_MEANING = "com.example.wordlist.meaning";
    public static final String EXTRA_TYPE = "com.example.wordlist.type";

    //veiw Model fo add new word Activity
    private AddNewWordViewModel mVeiwModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);

        wordEditText = findViewById(R.id.edit_text_word);
        meaningEditText = findViewById(R.id.edit_text_meaning);
        typeEditText = findViewById(R.id.edit_text_type);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_exit);

        Intent i = getIntent();
        if(i.hasExtra(EXTRA_ID))
        {
            //Update word
            setTitle("Edit Word");
            editMode = true;
            mID = i.getIntExtra(EXTRA_ID, -1);
            wordEditText.setText(i.getStringExtra(EXTRA_WORD).toString());
            meaningEditText.setText(i.getStringExtra(EXTRA_MEANING));
            typeEditText.setText(i.getStringExtra(EXTRA_TYPE));
        }else{
            //insert new word
            setTitle("Add new Word");
            editMode = false;
        }


        mVeiwModel = ViewModelProviders.of(this).get(AddNewWordViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_word:
                saveWord();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public void saveWord() {
        String word = wordEditText.getText().toString().trim();
        String meaning = meaningEditText.getText().toString().trim();
        String type = typeEditText.getText().toString().trim();

        Words wordObject = new Words(word, meaning, type);

        if (word.isEmpty() || type.isEmpty() || meaning.isEmpty()) {
            Toast.makeText(AddNewWordActivity.this, "please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        if(editMode){
            wordObject.setId(mID);
            mVeiwModel.update(wordObject);
        }else{
            mVeiwModel.insert(wordObject);
        }
        finish();
    }

}
