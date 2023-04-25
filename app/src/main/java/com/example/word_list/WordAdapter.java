package com.example.word_list;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private List<Words> mWordList = new ArrayList<>();

    private OnItemCliclListener mListener;
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Words currentWord = mWordList.get(position);
        holder.textViewWord.setText(currentWord.getWordName());
        holder.textViewType.setText(currentWord.getWordType());
        holder.textViewMeaning.setText(currentWord.getWordMeaning());
    }

    public  void setWords(List<Words> words)
    {
        mWordList = words;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewWord;
        public TextView textViewMeaning;
        public TextView textViewType;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWord = itemView.findViewById(R.id.word_text_view);
            textViewMeaning = itemView.findViewById(R.id.meaning_text_view);
            textViewType = itemView.findViewById(R.id.type_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();

                    if(true)
                    {
                        mListener.onItemClick(mWordList.get(index));
                    }
                }
            });
        }
    }

    public interface OnItemCliclListener
    {
        void onItemClick(Words word);
    }

    public void OnItemClickListener(OnItemCliclListener listener)
    {
        mListener = listener;
    }

    public Words getWordAt(int pos)
    {
        return mWordList.get(pos);
    }
}