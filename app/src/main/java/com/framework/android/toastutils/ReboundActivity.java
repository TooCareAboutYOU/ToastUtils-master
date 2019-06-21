package com.framework.android.toastutils;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import java.util.List;

public class ReboundActivity extends AppCompatActivity {

    private static final String TAG = "ReboundActivity";
    
    ConstraintLayout clGroup;
    AppCompatImageView img;
    boolean isStartSingle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebound);
        img = findViewById(R.id.acImg_View);
        singleReboundView();

        clGroup = findViewById(R.id.cl_Group);
        clGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiReboundView();
            }
        });

    }

    /**
     * 单个动画
     */
    private void singleReboundView() {
        SpringSystem springSystem = SpringSystem.create();
        final Spring spring = springSystem.createSpring();

        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                img.setScaleX(scale);
                img.setScaleY(scale);
            }
        });
        /**
         * 弹性动画
         * @param qcTension 拉力值
         * @param qcFriction 摩擦力值
         */
        SpringConfig springConfig = SpringConfig.fromOrigamiTensionAndFriction(100, 1);
        spring.setSpringConfig(springConfig);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleReboundView();
                spring.setEndValue(isStartSingle ? 1.0f : 0.0f);
                isStartSingle = !isStartSingle;
            }
        });
    }

    /**
     * 多动画
     */
    private void multiReboundView(){
        SpringChain springChain=SpringChain.create(40,6,50,7);
        int childCount=clGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View view=clGroup.getChildAt(i);
            springChain.addSpring(new SimpleSpringListener(){
                @Override
                public void onSpringUpdate(Spring spring) {
                    super.onSpringUpdate(spring);
                    Log.i(TAG, "onSpringUpdate: ");
                    view.setTranslationY((float) spring.getCurrentValue());
                }

                @Override
                public void onSpringAtRest(Spring spring) {
                    super.onSpringAtRest(spring);
                    Log.i(TAG, "onSpringAtRest: ");
                }

                @Override
                public void onSpringActivate(Spring spring) {
                    super.onSpringActivate(spring);
                    Log.i(TAG, "onSpringActivate: ");
                }

                @Override
                public void onSpringEndStateChange(Spring spring) {
                    Log.i(TAG, "onSpringEndStateChange: ");
                }
            });
        }
        List<Spring> springs=springChain.getAllSprings();
        for (int i = 0; i < springs.size(); i++) {
            springs.get(i).setCurrentValue(400);
        }

        /**
         * 从第几个子view开始
         */
        springChain.setControlSpringIndex(0);
        /**
         * 设置结束的位置
         */
        springChain.getControlSpring().setEndValue(0.0f);
    }
}
