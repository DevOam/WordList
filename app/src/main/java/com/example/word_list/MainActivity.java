package com.example.word_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //veiw Model
    private WordViewModel mWordViewModel;

    //RecyclerView
    private RecyclerView mRecyclerView;
    private WordAdapter mWordAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //floating button
        FloatingActionButton floatingActionButton = findViewById(R.id.button_add_word);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to the add activity
                Intent i = new Intent(MainActivity.this, AddNewWordActivity.class);
                startActivityForResult(i, 1);
            }
        });

        //recycler view
        mRecyclerView = findViewById(R.id.words_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        //connect Recyclerview With adapter
        mWordAdapter = new WordAdapter();
        mRecyclerView.setAdapter(mWordAdapter);

        //View Model
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Words>>() {
            @Override
            public void onChanged(List<Words> words) {
                //Update UI
                //RecyclerView
                mWordAdapter.setWords(words);
                // Toast.makeText(MainActivity.this, "on Changed worked", Toast.LENGTH_LONG).show();
            }

        });

        mWordAdapter.OnItemClickListener(new WordAdapter.OnItemCliclListener() {
            @Override
            public void onItemClick(Words word) {
                Intent i = new Intent(MainActivity.this, AddNewWordActivity.class);
                i.putExtra(AddNewWordActivity.EXTRA_ID, word.getId());
                i.putExtra(AddNewWordActivity.EXTRA_WORD, word.getWordName());
                i.putExtra(AddNewWordActivity.EXTRA_TYPE, word.getWordType());
                i.putExtra(AddNewWordActivity.EXTRA_MEANING, word.getWordMeaning());
                startActivity(i);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //delete item
                int position = viewHolder.getAdapterPosition();
                mWordViewModel.delete(mWordAdapter.getWordAt(position));
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_words:
                //delete all words
                mWordViewModel.deleteAllWords();
                Toast.makeText(this, "All words deleted", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}