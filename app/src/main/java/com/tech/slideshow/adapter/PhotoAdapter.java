package com.tech.slideshow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.tech.slideshow.R;
import com.tech.slideshow.customviews.CountView;
import com.tech.slideshow.listener.OnPhotoClick;
import com.tech.slideshow.model.Photo;


import java.util.ArrayList;
import java.util.Collections;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>
        implements DraggableItemAdapter<PhotoAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<Photo> arrayListPhoto;
    private final OnPhotoClick mListener;

    public PhotoAdapter(Context mContext, ArrayList<Photo> arrayListPhoto, OnPhotoClick listener) {
        this.mContext = mContext;
        this.arrayListPhoto = arrayListPhoto;
        this.mListener = listener;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return arrayListPhoto.get(position).getId();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = arrayListPhoto.get(position);

        holder.tvNumber.setCheckedNum(holder.getAbsoluteAdapterPosition()+1);
        Glide.with(mContext).load(photo.getFilePath()).into(holder.imgPhoto);
        holder.imgRemove.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onRemove(holder.getAbsoluteAdapterPosition());
            }
        });
        holder.imgEdit.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onCrop(holder.getAbsoluteAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrayListPhoto == null) {
            return 0;
        } else {
            return arrayListPhoto.size();
        }
    }

    @Override
    public boolean onCheckCanStartDrag(@NonNull ViewHolder holder, int position, int x, int y) {
        return true;
    }

    @Nullable
    @Override
    public ItemDraggableRange onGetItemDraggableRange(@NonNull ViewHolder holder, int position) {
        return null;
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        Collections.swap(arrayListPhoto, fromPosition, toPosition);
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        return true;
    }

    @Override
    public void onItemDragStarted(int position) {
        notifyDataSetChanged();
    }

    @Override
    public void onItemDragFinished(int fromPosition, int toPosition, boolean result) {
        notifyDataSetChanged();
    }

    public static class ViewHolder extends AbstractDraggableItemViewHolder {
        AppCompatImageView imgPhoto;
        AppCompatImageView imgRemove;
        AppCompatImageView imgEdit;
        FrameLayout loadingView;
        CountView tvNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.media_thumbnail);
            imgRemove = itemView.findViewById(R.id.removeIv);
            tvNumber = itemView.findViewById(R.id.check_view);
            imgEdit = itemView.findViewById(R.id.btnEdit);
//            loadingView = itemView.findViewById(R.id.progressFl);
        }
    }


}
