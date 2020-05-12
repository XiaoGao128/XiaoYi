package com.example.xiaoyi_test_2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xiaoyi_test_2.R;

public class ADActivity extends AppCompatActivity {
    private TextView time;
    private Button btn_jump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        getWindow().setExitTransition(new Fade().setDuration(500));
        time=findViewById(R.id.ad_time);
        final CountDownTimer timer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                time.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Intent intent = new Intent(ADActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
        timer.start();
        btn_jump=findViewById(R.id.ad_jump);
        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ADActivity.this, MainActivity.class);
                startActivity(intent);
                timer.cancel();
                finish();
            }
        });
    }
}
