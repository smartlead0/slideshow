package com.tech.slideshow.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;
import com.tech.slideshow.SharedPreferenceUtils;
import com.tech.slideshow.Utils;

public class RateDialog extends Dialog implements View.OnClickListener {
    private final Context mContext;
    ImageView ivStart1, ivStart2, ivStart3, ivStart4, ivStart5, ivOffer;
    TextView tvTip, tvMessage, tvBtn;
    boolean optionRate = false;

    public RateDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        setContentView(R.layout.dialog_rate);
        initViews();
        updateStarRating(5);
    }

    private void initViews() {
        tvMessage = findViewById(R.id.tv_feedback_tip_gone);
        tvTip = findViewById(R.id.tv_feedback_tip);
        tvBtn = findViewById(R.id.v_continue);
        ivStart1 = findViewById(R.id.ivStar1);
        ivStart2 = findViewById(R.id.ivStar2);
        ivStart3 = findViewById(R.id.ivStar3);
        ivStart4 = findViewById(R.id.ivStar4);
        ivStart5 = findViewById(R.id.ivStar5);
        ivOffer = findViewById(R.id.ivOffer);

        ivStart1.setOnClickListener(this);
        ivStart2.setOnClickListener(this);
        ivStart3.setOnClickListener(this);
        ivStart4.setOnClickListener(this);
        ivStart5.setOnClickListener(this);
        tvBtn.setOnClickListener(this);
    }

    private void updateStarRating(int rating) {
        optionRate = rating >= 4;
        tvBtn.setEnabled(true);
//        updateTextWithAnimation(tvTip, optionRate ?
//                getContext().getString(R.string.dialog_rate_tip_4_5_star) :
//                getContext().getString(R.string.dialog_rate_tip_3_star));
//
//        updateTextWithAnimation(tvMessage, optionRate ?
//                getContext().getString(R.string.dialog_rate_tip_4_5_message) :
//                getContext().getString(R.string.dialog_rate_tip_3_messege));
        ivOffer.setVisibility(rating == 5 ? View.VISIBLE : View.GONE);

        ivStart1.setImageResource(rating >= 1 ? R.drawable.ic_star_up : R.drawable.ic_un_star_up);
        ivStart2.setImageResource(rating >= 2 ? R.drawable.ic_star_up : R.drawable.ic_un_star_up);
        ivStart3.setImageResource(rating >= 3 ? R.drawable.ic_star_up : R.drawable.ic_un_star_up);
        ivStart4.setImageResource(rating >= 4 ? R.drawable.ic_star_up : R.drawable.ic_un_star_up);
        ivStart5.setImageResource(rating >= 5 ? R.drawable.ic_star_up : R.drawable.ic_un_star_up);
    }

    @Override
    public void onClick(View view) {
        int idView = view.getId();

        if (idView == R.id.v_continue) {
            rateApp();
        } else if (idView == R.id.ivStar1) {
            updateStarRating(1);
        } else if (idView == R.id.ivStar2) {
            updateStarRating(2);
        } else if (idView == R.id.ivStar3) {
            updateStarRating(3);
        } else if (idView == R.id.ivStar4) {
            updateStarRating(4);
        } else if (idView == R.id.ivStar5) {
            updateStarRating(5);
        }
    }

    private void updateTextWithAnimation(TextView textView, String newText) {
        textView.animate()
                .alpha(0f) // Làm mờ nội dung cũ
                .setDuration(200) // Thời gian hiệu ứng
                .withEndAction(() -> {
                    textView.setText(newText); // Cập nhật nội dung mới
                    textView.animate()
                            .alpha(1f) // Hiển thị lại nội dung
                            .setDuration(200)
                            .start();
                })
                .start();
    }

    private void rateApp() {
        if (optionRate) {
            Utils.rateApp(mContext);
        }
        SharedPreferenceUtils.getInstance(mContext).setBoolean(GlobalConstant.IS_RATED_APP, true);
        dismiss();
    }
}