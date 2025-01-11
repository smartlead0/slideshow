package com.tech.slideshow.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tech.slideshow.R;
import com.tech.slideshow.Utils;
import com.tech.slideshow.ads.NativeAdAdmob;

public class ExitBottomDialog extends BottomSheetDialogFragment {
    private Activity mContext;

    public ExitBottomDialog() {
    }
    public static ExitBottomDialog newInstance() {
        return new ExitBottomDialog();
    }
    public ExitBottomDialog(Activity mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_exit_bottom, container, false);
        if (mContext == null) {
            dismiss();
            return view;
        }
        NativeAdAdmob.showNativeBig7(mContext, view);
        view.findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
                dismiss();
            }
        });
        view.findViewById(R.id.layoutQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.feedbackApp(mContext);
            }
        });
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mContext = (Activity) context;
        }
    }
}
