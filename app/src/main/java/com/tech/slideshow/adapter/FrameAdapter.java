package com.tech.slideshow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;
import com.tech.slideshow.SharedPreferenceUtils;
import com.tech.slideshow.listener.FrameListener;

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.ViewHolder> {
    int row_selected = 0;
    private final FrameListener mListener;
    private final Context mContext;
    private final int[] frameList;

    public FrameAdapter(Context context, FrameListener mListener) {
        this.mListener = mListener;
        this.mContext = context;
        this.frameList = getFrameList();
    }

    private int[] getFrameList() {
        int caseFrame = SharedPreferenceUtils.getInstance(mContext).getInt(GlobalConstant.RATIO_FRAME, 11);
        if (caseFrame == 43) {
            return GlobalConstant.getFrameList43();
        } else if (caseFrame == 11) {
            return GlobalConstant.getFrameList11();
        } else if (caseFrame == 169) {
            return GlobalConstant.getFrameList169();
        } else {
            return GlobalConstant.getFrameList916();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_frame, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resourceId = frameList[position];
        holder.imgThumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this.mContext).load(resourceId).into(holder.imgThumbnail);
        if (position == 0) {
            holder.imgNone.setVisibility(View.VISIBLE);
        } else {
            holder.imgNone.setVisibility(View.GONE);
        }
        if (row_selected == position) {
            holder.imgSelected.setVisibility(View.VISIBLE);

        } else {
            holder.imgSelected.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (holder.getAbsoluteAdapterPosition() != row_selected) {
                if (mListener != null) {
                    if (holder.getAbsoluteAdapterPosition()!=0){
                        mListener.onFrameSelected(resourceId);
                        setSelectedItem(holder.getAbsoluteAdapterPosition());
                    }else {
                        mListener.onFrameSelected(0);
                        setSelectedItem(holder.getAbsoluteAdapterPosition());
                    }


                }
            }

        });
    }

    public void setSelectedItem(int position) {
        int previousSelected = row_selected;
        row_selected = position;
        notifyItemChanged(previousSelected); // Thông báo RecyclerView cập nhật item đã chọn trước đó
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return frameList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imgThumbnail;
        AppCompatImageView imgNone;
        AppCompatImageView imgSelected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_transition);
            imgNone = itemView.findViewById(R.id.iv_none);
            imgSelected = itemView.findViewById(R.id.img_selected);
        }
    }
}
