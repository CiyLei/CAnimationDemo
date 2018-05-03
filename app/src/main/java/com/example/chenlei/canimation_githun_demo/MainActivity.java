package com.example.chenlei.canimation_githun_demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.canimation.CAnimationHelp;
import com.example.canimation.COnExecutionFinshListener;
import com.example.canimation.attr.CTextSizeViewAttr;

public class MainActivity extends AppCompatActivity {

    private CAnimationHelp cah = new CAnimationHelp(this);
    private Button mButton1,mButton2,mButton3,mButton4;
    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView1 = findViewById(R.id.textView1);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cah.startWithDuration(2000, new COnExecutionFinshListener() {
                    @Override
                    public void onExecution() {
                        ViewGroup.LayoutParams lp = mTextView1.getLayoutParams();
                        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        lp.height = 300;
                        mTextView1.setLayoutParams(lp);
                        mTextView1.setBackgroundColor(Color.RED);
                        mTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                    }
                });
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cah.startWithDuration(2000, new COnExecutionFinshListener() {
                    @Override
                    public void onExecution() {
                        ViewGroup.LayoutParams lp = mTextView1.getLayoutParams();
                        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        mTextView1.setLayoutParams(lp);
                        mTextView1.setBackgroundColor(Color.YELLOW);
                        mTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    }
                }, new BounceInterpolator());
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //卸载字体大小支持
                CAnimationHelp.mViewAttrClass.remove(CTextSizeViewAttr.class);
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载字体大小支持
                CAnimationHelp.mViewAttrClass.add(CTextSizeViewAttr.class);
            }
        });
    }
}
