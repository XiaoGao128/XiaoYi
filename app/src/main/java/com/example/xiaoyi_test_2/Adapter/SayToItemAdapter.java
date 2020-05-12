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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xiaoyi_test_2.Bean.SayToItemBean;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.CommentToItem.SayToCommentClass;
import com.example.xiaoyi_test_2.Activity.ShowDetailActivity;

import java.util.ArrayList;

public class SayToItemAdapter extends RecyclerView.Adapter<SayToItemAdapter.SayViewHolder> implements SayToCommentClass.sendAdd {
    private String Clas="SattoItemAdapter-------------";
    private ArrayList<SayToItemBean> list;
    private ArrayList<ArrayList<SayToItemBean>> list_reply;
    private Context context;
    private ReplyToItemAdapter replyToItemAdapter;
    public SayToItemAdapter(Context context, ArrayList<SayToItemBean> list,ArrayList<ArrayList<SayToItemBean>> list_reply) {
    this.list=list;
    this.list_reply=list_reply;
    this.context=context;
    }

    @NonNull
    @Override
    public SayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SayViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_showde_sayto_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SayViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getUsername());
        if (list.get(position).getHead()!=null&&!list.get(position).getHead().equals("")){
            Glide.with(context).load(list.get(position).getHead()).into(holder.head);
        }
        holder.describ.setText(list.get(position).getSaywhat());
        if (list.get(position).getDate()!=null)
        holder.time.setText(list.get(position).getDate().toString());
        else holder.time.setText("刚刚");
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.describ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog mCameraDialog = new Dialog(context, R.style.BottomDialog);
                LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(
                        R.layout.layout_saysmoetoitem, null);
                Button button=(root.findViewById(R.id.saytoitem_btn));
                button.setText("回复");
                SayToCommentClass sayToCommentClass=new SayToCommentClass(root,context,list.get(position),mCameraDialog,SayToItemAdapter.this,position);
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
            }
        });
        replyToItemAdapter=new ReplyToItemAdapter(context,list_reply.get(position),list.get(position).getId());
        holder.recyclerView.setAdapter(replyToItemAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addComment(SayToItemBean sayToItemBean){
        list.add(0,sayToItemBean);
        list_reply.add(0,new ArrayList<SayToItemBean>());
        notifyItemChanged(0);

    }
    public void deleteComment(int position){
        list.remove(position);
        list_reply.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public void sendl(SayToItemBean itemBean, int posi) {
        replyToItemAdapter.addReply(itemBean,posi);
    }

    public class SayViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private ImageView head;
        private TextView name;
        private TextView time;
        private TextView describ;
        private ImageView item;
        public SayViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.saytoitem_recy);
            head=itemView.findViewById(R.id.saytoitem_img_head);
            name=itemView.findViewById(R.id.saytoitem_tv_name);
            time=itemView.findViewById(R.id.saytoitem_tv_time);
            describ=itemView.findViewById(R.id.saytoitem_tv_describ);
            item=itemView.findViewById(R.id.saytoitem_img_item);
        }
    }

}
