package com.tech.slideshow.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;
import com.tech.slideshow.listener.OnDialogListener;

public class ConfirmDialog extends Dialog {
    private final OnDialogListener mListener;
    private final int typeDialog;
    private AppCompatTextView tvTittle;
    private AppCompatTextView tvContent;
    private AppCompatTextView btnPositive;
    private AppCompatTextView btnNegative;

    public ConfirmDialog(@NonNull Context context, int mTypeDialog, OnDialogListener listener) {
        super(context);
        setContentView(R.layout.dialog_confirm);
        this.mListener = listener;
        this.typeDialog = mTypeDialog;
        initViews();
        initData();
        btnPositive.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onPositiveClick();
                dismiss();
            }
            dismiss();
        });
        btnNegative.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onNegativeClick();
                dismiss();
            }
        });
    }

    private void initData() {
        if (typeDialog == GlobalConstant.DIALOG_CONFIRM_EXIT) {
            tvTittle.setText(R.string.exit);
            tvContent.setText(R.string.dialog_exit_body);
            btnNegative.setText(R.string.str_action_cancel);
            btnPositive.setText(R.string.str_go_back);
        } else if (typeDialog == GlobalConstant.DIALOG_CONFIRM_NO_SAVE) {
            tvTittle.setText(R.string.leave_without_saving);
            tvContent.setText(R.string.leave_edit);
            btnNegative.setText(R.string.str_action_cancel);
            btnPositive.setText(R.string.exit);
        } else if (typeDialog == GlobalConstant.DIALOG_CONFIRM_STOP_RENDER) {
            tvTittle.setText(R.string.quit_rendering);
            tvContent.setText(R.string.cancel_render_content);
            btnNegative.setText(R.string.keep_rendering);
            btnPositive.setText(R.string.exit);
        } else if (typeDialog == GlobalConstant.DIALOG_CONFIRM_DELETE) {
            tvTittle.setText(R.string.str_action_delete);
            tvContent.setText(R.string.dialog_delete_body);
            btnNegative.setText(R.string.str_action_cancel);
            btnPositive.setText(R.string.str_action_delete);
        }

    }

    private void initViews() {
        tvTittle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_des);
        btnNegative = findViewById(R.id.tv_bt_negative);
        btnPositive = findViewById(R.id.tv_bt_positive);
    }
}
