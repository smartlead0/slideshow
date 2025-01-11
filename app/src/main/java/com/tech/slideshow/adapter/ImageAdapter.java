package com.tech.slideshow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tech.slideshow.MyApplication;
import com.tech.slideshow.R;
import com.tech.slideshow.listener.OnImageListener;
import com.tech.slideshow.model.Photo;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final MyApplication myApplication;
    private final ArrayList<Photo> arrayList;
    private final OnImageListener mListener;
    private static final int FOOTER_ADD_IMAGE = 1;
    private static final int FOOTER_REORDER_IMAGE = 2;

    public ImageAdapter(OnImageListener mListener) {
        this.mListener = mListener;
        this.myApplication = MyApplication.getInstance();
        this.arrayList = MyApplication.getInstance().getSelectedImages();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == FOOTER_ADD_IMAGE) {
            view = inflater.inflate(R.layout.item_add_photo, parent, false);
            return new AddPhotoViewHolder(view);
        } else if (viewType == FOOTER_REORDER_IMAGE) {
            view = inflater.inflate(R.layout.item_reoder, parent, false);
            return new ReorderViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_transition, parent, false);
            return new ImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder) {
            Photo photo = arrayList.get(position);
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            Glide.with(myApplication).load(photo.getFilePath()).into(imageViewHolder.imgThumbnail);
        } else if (holder instanceof AddPhotoViewHolder) {
            AddPhotoViewHolder addPhotoViewHolder = (AddPhotoViewHolder) holder;
            addPhotoViewHolder.itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onAddImage();
                }
            });
        } else if (holder instanceof ReorderViewHolder) {
            ReorderViewHolder reorderViewHolder = (ReorderViewHolder) holder;
            reorderViewHolder.itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onReorder();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return arrayList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {


        if (position == arrayList.size()) {
            return FOOTER_ADD_IMAGE;
        } else if (position == arrayList.size() + 1) {
            return FOOTER_REORDER_IMAGE;
        }

        return super.getItemViewType(position);
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgThumbnail;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_transition);
        }
    }

    public static class AddPhotoViewHolder extends RecyclerView.ViewHolder {

        public AddPhotoViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

    public static class ReorderViewHolder extends RecyclerView.ViewHolder {
        public ReorderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
