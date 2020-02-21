package com.example.getsumgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.getsumgame.data.GameListItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameItemViewHolder> {
    private List<GameListItem> mGameData;
    private Context activity;

    public void updateGameData(List<GameListItem> gamedata){
        mGameData=gamedata;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mGameData != null ) {
            return mGameData.size();
        } else {
            return 0;
        }
    }

    public GameItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater infalter=LayoutInflater.from(parent.getContext());
        View itemView=infalter.inflate(R.layout.game_list_item,parent,false);
        return new GameItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GameItemViewHolder holder, int position) {
        holder.bind(mGameData.get(position));
    }

    class GameItemViewHolder extends RecyclerView.ViewHolder{
        private TextView mGameNameTV;
        private TextView mStreamerNumberTV;
        private TextView mViewNumberTV;
        private TextView mGameIdTV;

        public GameItemViewHolder(View ItemView){
            super(ItemView);
            mGameNameTV=ItemView.findViewById(R.id.game_name);
            mStreamerNumberTV=ItemView.findViewById(R.id.streamer_number);
            mViewNumberTV=ItemView.findViewById(R.id.view_number);
            mGameIdTV=ItemView.findViewById(R.id.game_id);
        }
        public void bind(GameListItem gameItem){
            String name=gameItem.name;
            String id=Integer.toString(gameItem.id);
            mGameNameTV.setText(name);
            mStreamerNumberTV.setText("0");
            mViewNumberTV.setText("0");
            mGameIdTV.setText(id);
        }

    }
}
