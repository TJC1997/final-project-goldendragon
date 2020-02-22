package com.example.getsumgame;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.getsumgame.data.GameInfo;
import com.example.getsumgame.data.GameListItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameItemViewHolder> {
    private List<GameInfo> mGameInfo;
    private Context activity;

    public void updateGameData(List<GameInfo> gameInfo){
        mGameInfo=gameInfo;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mGameInfo != null ) {
            return mGameInfo.size();
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
        holder.bind(mGameInfo.get(position));
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
        public void bind(GameInfo gameItem){
            String name=gameItem.Game_name;
            String id=Integer.toString(gameItem.Game_id);
            String viewer_count=Integer.toString(gameItem.view_number);
            String streamer_count=Integer.toString(gameItem.streamer_count);
            mGameNameTV.setText(name);
            mStreamerNumberTV.setText(streamer_count);
            mViewNumberTV.setText(viewer_count);
            mGameIdTV.setText(id);
        }
    }
}
