package com.eos.caffeinproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeActivity extends Fragment {
    ViewFlipper v_fllipper;
    View v;

    int images[] = {
            R.drawable.copy_image,
            R.drawable.copy_image,
            R.drawable.copy_image
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = inflater.inflate(R.layout.activiy_home,container, false);
        v_fllipper = v.findViewById(R.id.image_slide);

        for(int image : images) {
            fllipperImages(image);
        }
        return v;
    }
    public void fllipperImages(int image) {
        ImageView imageView = new ImageView(v.getContext());
        imageView.setBackgroundResource(image);

        v_fllipper.addView(imageView);      // 이미지 추가
        v_fllipper.setFlipInterval(4000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper.setInAnimation(v.getContext(),android.R.anim.slide_in_left);
        v_fllipper.setOutAnimation(v.getContext(),android.R.anim.slide_out_right);
    }
}
