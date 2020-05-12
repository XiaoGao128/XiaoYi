package com.example.xiaoyi_test_2.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoyi_test_2.Bean.SayToItemBean;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.CommentToItem.SayToCommentClass;
import com.example.xiaoyi_test_2.CommentToItem.SayToReplyClass;
import com.example.xiaoyi_test_2.Activity.ShowDetailActivity;

import java.util.ArrayList;

public class ReplyToItemAdapter extends RecyclerView.Adapter<ReplyToItemAdapter.viewHolder> implements SayToCommentClass.sendAdd,SayToReplyClass.sendAdd{
    private Context context;
    private ArrayList<SayToItemBean> list;
    private int commentID;

    public ReplyToItemAdapter(Context context, ArrayList<SayToItemBean> list, int CommentID) {
        this.context = context;
        this.list = list;
        this.commentID = CommentID;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.layout_reply_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        if (list.get(position) != null) {
//            holder.name.setText(list.get(position).getUsername());
            if (list.get(position).getDate()!=null)
            holder.time.setText(list.get(position).getDate().toString());
            else holder.time.setText("刚刚");
            holder.name.setText(list.get(position).getUsername());
            holder.describ.setText(list.get(position).getSaywhat());
            //评论的回复
            if (list.get(position).getAbovecomment()==(commentID)) {

            } else //回复的回复
            {
                holder.huifu.setVisibility(View.VISIBLE);
//                holder.replyname.setText(list.get(position).getTousername());
            }
            holder.describ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog mCameraDialog = new Dialog(context, R.style.BottomDialog);
                    LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(
                            R.layout.layout_saysmoetoitem, null);
                    Button button=(root.findViewById(R.id.saytoitem_btn));
                    button.setText("回复");
                    SayToReplyClass sayToReplyClass=new SayToReplyClass(root,context,list.get(position),mCameraDialog,ReplyToItemAdapter.this,position);
//                root = sayToItemClass.bindandset(root);
                    mCameraDialog.setContentView(root);
                    Window dialogWindow = mCameraDialog.getWindow();
                    dialogWindow.setGravity(Gravity.BOTTOM);
                    dialogWindow.setWindowAnimations(R.style.DialogAnimation); // 添加动画
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                    lp.x = 0; // 新位置X坐标
                    lp.y = 0; // 新位置Y坐标
                    lp.width = (int) context.getResources().getDisplayMetrics().widthPixels; // 宽度
                    root.measure(0, 0);
                    lp.height = root.getMeasuredHeight();
//              lp.alpha = 9f; // 透明度
                    dialogWindow.setAttributes(lp);
                    mCameraDialog.show();
                    ((ShowDetailActivity)context).showKeyboard(root.findViewById(R.id.saytoitem_et));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void addReply(SayToItemBean sayToItemBean,int posi){
        list.add(list.size(),sayToItemBean);
        notifyItemChanged(posi);
    }

    @Override
    public void send(SayToItemBean itemBean, int posi) {
        addReply(itemBean,posi);
        Log.d("--------Reply","---"+posi);
    }

    @Override
    public void sendl(SayToItemBean itemBean, int posi) {
        addReply(itemBean,posi);
        Log.d("------------------Reply","---"+posi);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView name, time, describ, huifu, replyname;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.reply_tv_name);
            time = itemView.findViewById(R.id.reply_tv_time);
            describ = itemView.findViewById(R.id.reply_tv_describ);
            huifu=itemView.findViewById(R.id.reply_tv_huifu);
            replyname=itemView.findViewById(R.id.reply_tv_replyname);
        }
    }
}
