package com.lvndk.barnner.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lvndk.barnner.R;
import com.lvndk.barnner.view.PieView;

/**
 * Created by mac on 17/3/9.
 */

public class SplashActivity extends Activity {

    private PieView mPieView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPieView = (PieView) findViewById(R.id.pie_view);

        /**
         * 必须设置的
         */
        mPieView.setArcA(60);
        mPieView.setArcB(10);
        mPieView.setArcC(20);
        mPieView.setArcD(10);
        mPieView.setText("60");
        mPieView.setTextSize(50);
        mPieView.setTextTips("收入");
        mPieView.setTextPercentSize(16);

        /**
         * 可以不设置的
         */
        mPieView.setColorA(Color.RED);
        mPieView.setColorB(Color.YELLOW);
        mPieView.setColorC(Color.GREEN);
        mPieView.setColorD(Color.BLUE);

        /**
         * 动画效果的启动
         */
        mPieView.startAnim();

    }
}
