package com.example.xiaoyi_test_2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoyi_test_2.Bean.MsgBean;
import com.example.xiaoyi_test_2.Activity.MainActivity;
import com.example.xiaoyi_test_2.Activity.MessageActivity;
import com.example.xiaoyi_test_2.R;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.viewHolder> implements View.OnClickListener {
    private Context context;
    private int screenWidth,screenHeight;
    public MsgAdapter(Context context, List<MsgBean> msgBeans) {
        this.context=context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DisplayMetrics dm=context.getResources().getDisplayMetrics();
        screenWidth=dm.widthPixels;
        screenHeight=dm.heightPixels-50;
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_msg, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        holder.btn_num.setOnTouchListener(new View.OnTouchListener(){
            //上次view的坐标位置
            int lastX, lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int ea=event.getAction();
                Log.i("TAG", "Touch:"+ea);

                switch(ea){
                    case MotionEvent.ACTION_DOWN:
                        //按下记录view坐标
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:

                        //移动时记录相对上次的坐标
                        int dx =(int)event.getRawX() - lastX;
                        int dy =(int)event.getRawY() - lastY;

                        //相对于parent 的View上下左右位置
                        int left = v.getLeft() + dx;
                        int top = v.getTop() + dy;
                        int right = v.getRight() + dx;
                        int bottom = v.getBottom() + dy;

                        //如果left < 0，则是左移，右边框上次位置加上左移部分
                        if(left < 0){
                            left = 0;
                            right = left + v.getWidth();
                        }

                        //
                        if(right > screenWidth){
                            right = screenWidth;
                            left = right - v.getWidth();
                        }

                        //如果top < 0，则是上移，下边框上次位置加上移部分
                        if(top < 0){
                            top = 0;
                            bottom = top + v.getHeight();
                        }

                        if(bottom > screenHeight){
                            bottom = screenHeight;
                            top = bottom - v.getHeight();
                        }

                        //重新layout
                        v.layout(left, top, right, bottom);

                        Log.i("", "position" + left +", " + top + ", " + right + ", " + bottom);

                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();

                        break;
                    case MotionEvent.ACTION_UP:
                        holder.btn_num.setVisibility(View.INVISIBLE);
                        break;
                }
                return false;
            }});
        holder.msgliner.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.msg_liner:{
                Intent intent=new Intent(context, MessageActivity.class);
                ((MainActivity)context).startActivityForResult(intent,1);
                break;
            }
        }
    }

    class viewHolder extends RecyclerView.ViewHolder {
        private ImageView head;
        private TextView name, content;
        private Button btn_num;
        private LinearLayout msgliner;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            btn_num=itemView.findViewById(R.id.msg_num);
            content=itemView.findViewById(R.id.msg_content);
            msgliner=itemView.findViewById(R.id.msg_liner);
        }
    }
}
