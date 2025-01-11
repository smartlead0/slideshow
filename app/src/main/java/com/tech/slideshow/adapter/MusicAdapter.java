package com.tech.slideshow.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;
import com.tech.slideshow.listener.MusicListener;
import com.tech.slideshow.model.Music;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<Music> musicArrayList;
    private final Context mContext;
    private static final int FOOTER_ADD_MUSIC = 1;
    int row_selected = 0;
    private final MusicListener mListener;

    public MusicAdapter(Context mContext, MusicListener mListener) {
        this.musicArrayList = GlobalConstant.getDataMusic();
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == FOOTER_ADD_MUSIC) {
            view = inflater.inflate(R.layout.item_add_music, parent, false);
            return new AddMusicViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_music, parent, false);
            return new MusicViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MusicViewHolder) {
            Music music = musicArrayList.get(position);
            MusicViewHolder musicViewHolder = (MusicViewHolder) holder;
            Glide.with(mContext).load(music.getRestImage()).into(musicViewHolder.imgThumbnail);
            musicViewHolder.tvName.setText(music.getNameMusic());
            if (row_selected == position) {
                musicViewHolder.imgSelected.setVisibility(View.VISIBLE);
                musicViewHolder.tvName.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                musicViewHolder.tvName.setTypeface(null, Typeface.BOLD);

            } else {
                musicViewHolder.imgSelected.setVisibility(View.INVISIBLE);
                musicViewHolder.tvName.setTextColor(ContextCompat.getColor(mContext, R.color.text_headline_color));
                musicViewHolder.tvName.setTypeface(null, Typeface.NORMAL);
            }
            musicViewHolder.itemView.setOnClickListener(v -> {
                if (holder.getAbsoluteAdapterPosition() != row_selected) {
                    if (mListener != null) {
                        mListener.onMusicSelected(music.getResId());
                    }
                    setSelectedItem(holder.getAbsoluteAdapterPosition());
                }

            });
        } else if (holder instanceof AddMusicViewHolder) {
            AddMusicViewHolder addMusicViewHolder = (AddMusicViewHolder) holder;
            addMusicViewHolder.itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onAddMusic();

                }
            });
        }
    }

    public void setSelectedItem(int position) {
        int previousSelected = row_selected;
        row_selected = position;
        notifyItemChanged(previousSelected); // Thông báo RecyclerView cập nhật item đã chọn trước đó
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return musicArrayList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == musicArrayList.size()) {
            return FOOTER_ADD_MUSIC;
        }
        return super.getItemViewType(position);
    }

    public static class MusicViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgSelected;
        AppCompatImageView imgThumbnail;
        TextView tvName;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSelected = itemView.findViewById(R.id.img_selected);
            imgThumbnail = itemView.findViewById(R.id.img_transition);
            tvName = itemView.findViewById(R.id.tvMusicName);

        }
    }

    public static class AddMusicViewHolder extends RecyclerView.ViewHolder {

        public AddMusicViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
