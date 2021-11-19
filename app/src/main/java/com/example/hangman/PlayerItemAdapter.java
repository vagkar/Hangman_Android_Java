package com.example.hangman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;



public class PlayerItemAdapter extends ArrayAdapter<Players> {

    private Context mContext;
    private ArrayList<Players> players;

    public PlayerItemAdapter(@NonNull Context context, ArrayList<Players> players) {
        super(context, 0, players);
        this.mContext = context;
        this.players = players;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }
        Players player = players.get(position);
        
        TextView name = v.findViewById(R.id.textView_name);
        TextView score = v.findViewById(R.id.textView_score);
        name.setText(player.getName());
        score.setText(String.valueOf(player.getScore()));
        return v;
    }


}
