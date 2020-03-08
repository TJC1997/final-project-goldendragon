package com.example.getsumgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.getsumgame.models.GameInfo;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameItemViewHolder> {
    private List<GameInfo> mGameInfo;
    private Context activity;
    private OnClickListener viewHolderListener;

    public interface OnClickListener{
        void onClickForDetail(String gameId, String gameName, int index);
    }

    public GameAdapter(OnClickListener listener){
        this.viewHolderListener = listener;
    }

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
        LayoutInflater infalter = LayoutInflater.from(parent.getContext());
        View itemView = infalter.inflate(R.layout.game_list_item,parent,false);
        return new GameItemViewHolder(itemView, this.viewHolderListener);
    }

    @Override
    public void onBindViewHolder(GameItemViewHolder holder, int position) {
        holder.bind(mGameInfo.get(position));
        holder.setIndexOfBinding(position);
    }

    class GameItemViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener{
        private TextView mGameNameTV;
        private TextView mStreamerNumberTV;
        private TextView mViewNumberTV;
        private TextView mGameIdTV;
        private ImageView mGameIcon;

        private GameAdapter.OnClickListener callback;
        private int indexOfBinding;

        public GameItemViewHolder(View ItemView, GameAdapter.OnClickListener callback){
            super(ItemView);
            ItemView.setOnClickListener(this);
            mGameNameTV=ItemView.findViewById(R.id.game_name);
            mStreamerNumberTV=ItemView.findViewById(R.id.streamer_number);
            mViewNumberTV=ItemView.findViewById(R.id.view_number);
            mGameIdTV=ItemView.findViewById(R.id.game_id);
            mGameIcon=ItemView.findViewById(R.id.iv_game_icon);

            this.callback = callback;
            this.indexOfBinding = 0;
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
            setup_icon(name);
        }

        public void setIndexOfBinding(int index){
            this.indexOfBinding = index;
        }

        @Override
        public void onClick(View v) {
            // Just assume the View is the on this viewholder is holding.
            // We can go more advanced later if we want to with a switch statement

            // Get Information from Text Views
            String gameId = mGameIdTV.getText().toString();
            String gameName = mGameNameTV.getText().toString();
            int index = this.indexOfBinding;

            // Send to Intent Populator
            callback.onClickForDetail(gameId, gameName, index);

        }

        public void setup_icon(String name){
            switch(name){
                case "League of Legends":
                    mGameIcon.setImageResource(R.drawable.league_of_legend);
                    break;
                case "Just Chatting":
                    mGameIcon.setImageResource(R.drawable.just_chatting);
                    break;
                case "Counter-Strike: Global Offensive":
                    mGameIcon.setImageResource(R.drawable.csgo);
                    break;
                case "Escape From Tarkov":
                    mGameIcon.setImageResource(R.drawable.escape_from_tarkov);
                    break;
                case "Fortnite":
                    mGameIcon.setImageResource(R.drawable.fortnite);
                    break;
                case "Grand Theft Auto V":
                    mGameIcon.setImageResource(R.drawable.grand_theft_v);
                    break;
                case "Hearthstone":
                    mGameIcon.setImageResource(R.drawable.hearthstone);
                    break;
                case "Apex Legends":
                    mGameIcon.setImageResource(R.drawable.apex_legends);
                    break;
                case "Dota 2":
                    mGameIcon.setImageResource(R.drawable.dota2);
                    break;
                case "PLAYERUNKNOWN'S BATTLEGROUNDS":
                    mGameIcon.setImageResource(R.drawable.pubg);
                    break;
                case "World of Warcraft":
                    mGameIcon.setImageResource(R.drawable.world_of_warcraft);
                    break;
                case "Overwatch":
                    mGameIcon.setImageResource(R.drawable.overwatch);
                    break;
                case "Minecraft":
                    mGameIcon.setImageResource(R.drawable.minecraft);
                    break;
                case "Call of Duty: Modern Warfare":
                    mGameIcon.setImageResource(R.drawable.call_of_duty);
                    break;
                case "Tom Clancy's Rainbow Six: Siege":
                    mGameIcon.setImageResource(R.drawable.rainbow_six);
                    break;
                default:
                    mGameIcon.setImageResource(R.drawable.unknown_icon);
            }
        }
    }
}
