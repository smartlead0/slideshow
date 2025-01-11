package com.tech.slideshow.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;
import com.tech.slideshow.Utils;
import com.tech.slideshow.activities.ViewFullActivity;
import com.tech.slideshow.listener.OnDialogListener;
import com.tech.slideshow.model.MyVideo;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private final Context mContext;
    private final ArrayList<MyVideo> mListFile;

    public GalleryAdapter(Context mContext, ArrayList<MyVideo> mListFile) {
        this.mContext = mContext;
        this.mListFile = mListFile;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyVideo myVideo = mListFile.get(position);
        holder.tvNameFile.setText(myVideo.getName());
        holder.tvDate.setText(myVideo.getLastModified());
        holder.tvFileSize.setText(Formatter.formatFileSize(mContext, myVideo.getLength()));
        holder.tvFilePath.setText(GlobalConstant.RootDirectoryMyVideoSaved.toString());
        Glide.with(mContext).load(myVideo.getAbsolutePath()).thumbnail(0.1f).into(holder.imgThumbnail);
        holder.imgMore.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(mContext, holder.imgMore);
            try {
                Field[] fields = popupMenu.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if ("mPopup".equals(field.getName())) {
                        field.setAccessible(true);
                        Object menuPopupHelper = field.get(popupMenu);
                        assert menuPopupHelper != null;
                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                        setForceIcons.invoke(menuPopupHelper, true);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            popupMenu.inflate(R.menu.menu_my_video);

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                int idMenu = menuItem.getItemId();
                if (idMenu == R.id.navigation_delete) {
                    Utils.showConfirmDialog(mContext, GlobalConstant.DIALOG_CONFIRM_DELETE, new OnDialogListener() {
                        @Override
                        public void onPositiveClick() {
                            File fileToDelete = new File(myVideo.getAbsolutePath());
                            if (fileToDelete.exists()) {
                                if (fileToDelete.delete()) {
                                    MediaScannerConnection.scanFile(mContext, new String[]{myVideo.getAbsolutePath()}, null, null);

                                    int position2 = holder.getAbsoluteAdapterPosition();
                                    try {
                                        mListFile.remove(position2);
                                        notifyItemRemoved(position2);
                                        notifyItemRangeChanged(position2, mListFile.size());
//                                    mContext.runOnUiThread(() -> mContext.tvCountFile.setText(mContext.getString(R.string.all_count, String.valueOf(originalData.size()))));
                                    } catch (IndexOutOfBoundsException e) {
                                        Log.e(GlobalConstant.TAG_LOG, "Error message", e);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                } else if (idMenu == R.id.navigation_share) {
                    Utils.shareVideo(mContext, myVideo.getAbsolutePath());
                } else if (idMenu == R.id.navigation_rename) {
                    Utils.showRenameDialog(mContext, myVideo.getName(), newName -> {
                        final File fileOldPdfName = new File(myVideo.getAbsolutePath());
                        final String replaceName = myVideo.getAbsolutePath().replace(Utils.removeExtension(myVideo.getName()), newName);
                        if (fileOldPdfName.renameTo(new File(replaceName))) {

                            File newFile = new File(replaceName);
                            MyVideo documentNew = new MyVideo();
                            documentNew.setName(newFile.getName());
                            documentNew.setAbsolutePath(newFile.getAbsolutePath());
                            documentNew.setLength(newFile.length());
                            long timestamp = newFile.lastModified();

                            documentNew.setLastModified(Utils.formatDateToHumanReadable(timestamp));
                            int position1 = holder.getAbsoluteAdapterPosition();
                            if (position1 != RecyclerView.NO_POSITION && position1 < mListFile.size()) {
                                mListFile.set(holder.getAbsoluteAdapterPosition(), documentNew);
                                notifyItemChanged(holder.getAbsoluteAdapterPosition());
                            } else {
                                Log.e("AdapterError", "Invalid position or arraylist size");
                            }
                        } else {
                            Toast.makeText(mContext, R.string.dialog_rename_fail, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                return false;
            });
            popupMenu.show();
        });
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ViewFullActivity.class);
            intent.putExtra(GlobalConstant.VIDEO_PATH, myVideo.getAbsolutePath());
            intent.putExtra(GlobalConstant.VIDEO_NAME, myVideo.getName());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mListFile.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView imgThumbnail;
        private final AppCompatTextView tvNameFile;
        private final AppCompatTextView tvDate;
        private final AppCompatTextView tvFileSize;
        private final AppCompatTextView tvFilePath;
        private final AppCompatImageView imgMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgThumbnail = itemView.findViewById(R.id.item_icon);
            tvNameFile = itemView.findViewById(R.id.item_name);
            tvDate = itemView.findViewById(R.id.item_date);
            tvFileSize = itemView.findViewById(R.id.item_size);
            tvFilePath = itemView.findViewById(R.id.item_source);
            imgMore = itemView.findViewById(R.id.item_pdf_more);

        }
    }
}
