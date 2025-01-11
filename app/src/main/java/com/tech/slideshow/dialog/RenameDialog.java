package com.tech.slideshow.dialog;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.tech.slideshow.R;
import com.tech.slideshow.customviews.rangeseekbar.Utils;
import com.tech.slideshow.listener.RenameListener;

import java.util.Objects;

public class RenameDialog extends Dialog {
    private final EditText edtRename;
    private final RenameListener listener;

    private final ConstraintLayout rootLayout;
    private final LinearLayout llError;
    private final TextView tvError;
    private final AppCompatImageView btnClear;

    public RenameDialog(@NonNull Context context, String oldName, RenameListener mListener) {
        super(context);
        setContentView(R.layout.dialog_rename);
        this.listener = mListener;
        btnClear = findViewById(R.id.iv_clear);

        rootLayout = findViewById(R.id.root);
        llError = findViewById(R.id.ll_error_tip);
        edtRename = findViewById(R.id.et_name);
        tvError = findViewById(R.id.tv_error_tip);
        edtRename.setText(oldName);
        edtRename.setSelectAllOnFocus(true);
        edtRename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() > 0) {
                    btnClear.setVisibility(View.VISIBLE);
                } else if (charSequence.length() == 0) {
                    btnClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnClear.setOnClickListener(view -> Objects.requireNonNull(edtRename.getText()).clear());
        findViewById(R.id.tv_bt_negative).setOnClickListener(view -> dismiss());

        findViewById(R.id.tv_bt_positive).setOnClickListener(view -> {
            if (TextUtils.equals(oldName, edtRename.getText().toString())) {
                llError.setVisibility(View.VISIBLE);
                tvError.setText(context.getString(R.string.dialog_rename_error));
                ObjectAnimator objectAnimator = Utils.startAnim(rootLayout);
                objectAnimator.start();
            } else if (Utils.isFileNameValid(edtRename.getText().toString())) {
                if (listener != null) {
                    listener.onRename(edtRename.getText().toString());
                    dismiss();
                }
            } else {
                ObjectAnimator objectAnimator = Utils.startAnim(rootLayout);
                objectAnimator.start();
                llError.setVisibility(View.VISIBLE);
                tvError.setText(context.getString(R.string.dialog_rename_error_2));
            }
        });
    }
}
