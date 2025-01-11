package com.tech.slideshow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tech.slideshow.R;
import com.tech.slideshow.listener.TransitionListener;
import com.tech.slideshow.themes.THEMES;

import java.util.ArrayList;
import java.util.Arrays;

public class TransitionAdapter extends RecyclerView.Adapter<TransitionAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<THEMES> themesArrayList;
    private final TransitionListener mListener;
    private int selectedItem = 0;

    public TransitionAdapter(Context context, TransitionListener mListener) {
        this.mContext = context;
        this.themesArrayList = new ArrayList<>(Arrays.asList(THEMES.values()));
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transition, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        THEMES themes = this.themesArrayList.get(position);
        Glide.with(mContext).load(themes.getThemeDrawable()).into(holder.imgThumbnail);
        if (selectedItem == position) {
            holder.imgSelected.setVisibility(View.VISIBLE);
        } else {
            holder.imgSelected.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (holder.getAbsoluteAdapterPosition() != selectedItem) {
                if (mListener != null) {
                    mListener.onTransitionSelected(themes);
                    setSelectedItem(holder.getAbsoluteAdapterPosition());
                }
            }
        });
    }

    public void setSelectedItem(int position) {
        int previousSelected = selectedItem;
        selectedItem = position;
        notifyItemChanged(previousSelected); // Thông báo RecyclerView cập nhật item đã chọn trước đó
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        if (themesArrayList == null) {
            return 0;
        } else {
            return themesArrayList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView imgThumbnail;
        private final AppCompatImageView imgSelected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_transition);
            imgSelected = itemView.findViewById(R.id.img_selected);
        }
    }
}
