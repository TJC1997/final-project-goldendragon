package com.example.getsumgame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getsumgame.models.SavedInfo;

import java.util.List;

public class SavedReposAdapter extends RecyclerView.Adapter<SavedReposAdapter.SavedInfoViewHolder> {

    private List<SavedInfo> mSavedInfo;
    private OnSavedInfoClickListener monSavedInfoClickListener;

    public interface OnSavedInfoClickListener {
        void OnSavedInfoClick(SavedInfo savedInfo);
    }

    public SavedReposAdapter(OnSavedInfoClickListener clickListener){
        monSavedInfoClickListener = clickListener;
    }

    public void updateSavedInfo(List<SavedInfo> savedInfos){
        mSavedInfo = savedInfos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavedInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.saved_game_name_list, parent, false);
        return new SavedInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedInfoViewHolder holder, int position) {
        holder.bind(mSavedInfo.get(position));
    }

    @Override
    public int getItemCount() {
        if (mSavedInfo != null){
            return mSavedInfo.size();
        } else {
            return 0;
        }
    }

    class SavedInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mInfo_gameName;
        public SavedInfoViewHolder(@NonNull View itemView){
            super(itemView);
            mInfo_gameName = itemView.findViewById(R.id.tv_saved_repo);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            SavedInfo s = mSavedInfo.get(getAdapterPosition());
            monSavedInfoClickListener.OnSavedInfoClick(s);

        }

        public void bind(SavedInfo savedInfo) {
            mInfo_gameName.setText(savedInfo.gameName);
        }

    }
}
