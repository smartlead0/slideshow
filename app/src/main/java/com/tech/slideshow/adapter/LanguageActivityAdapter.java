package com.tech.slideshow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;
import com.tech.slideshow.SharedPreferenceUtils;
import com.tech.slideshow.listener.ItemClickListener;
import com.tech.slideshow.model.Language;


import java.util.ArrayList;

public class LanguageActivityAdapter extends RecyclerView.Adapter<LanguageActivityAdapter.ViewHolder> {
    private final ArrayList<Language> languages;
    private int selectedItem;
    private final Context mContext;
    private final ItemClickListener listener;

    public LanguageActivityAdapter(Context mContext, ItemClickListener listener) {
        this.mContext = mContext;
        this.languages = GlobalConstant.createArraylistLanguage();
        this.selectedItem = SharedPreferenceUtils.getInstance(mContext).getInt(GlobalConstant.LANGUAGE_KEY_NUMBER, 0);
        this.listener = listener;
    }

    public void setSelectedItem(int position) {
        int previousSelected = selectedItem;
        selectedItem = position;
        notifyItemChanged(previousSelected); // Thông báo RecyclerView cập nhật item đã chọn trước đó
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (languages == null) {
            return 0;
        } else {
            return languages.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvLang;
        ImageView imgChoice;
        ImageView imgFlag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFlag = itemView.findViewById(R.id.img_flag);
            tvLang = itemView.findViewById(R.id.tv_name);
            imgChoice = itemView.findViewById(R.id.iv_selected_state);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            Language language = languages.get(position);
            tvLang.setText(language.getNameLanguage());
            Glide.with(mContext).load(language.getImgResource()).into(imgFlag);
            if (selectedItem == position) {
                Glide.with(mContext).load(R.drawable.ic_choice).into(imgChoice);
            } else {
                Glide.with(mContext).load(R.drawable.ic_not_choice).into(imgChoice);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAbsoluteAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                setSelectedItem(position);
                if (listener != null) {
                    listener.onItemSelected(position);
                }
            }
        }
    }
}
