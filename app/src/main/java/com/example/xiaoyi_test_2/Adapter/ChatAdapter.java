package com.example.xiaoyi_test_2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoyi_test_2.Bean.ChatBean;
import com.example.xiaoyi_test_2.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ChatBean> list;
    private Context context;
    public ChatAdapter(List<ChatBean> list,Context context){
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==ChatBean.TYPE_ME){
            return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_recy_item_msg_my,parent,false));
        }else {
            return new hisViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_recy_item_msg,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof myViewHolder){
            ((myViewHolder) holder).head.setImageResource(list.get(position).getHead());
            ((myViewHolder) holder).textView.setText(list.get(position).getContent());
        }else {
            ((hisViewHolder) holder).head.setImageResource(list.get(position).getHead());
            ((hisViewHolder) holder).textView.setText(list.get(position).getContent());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private ImageView head;
        private TextView textView;
        private ProgressBar progressBar;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.mychat_im_head);
            textView=itemView.findViewById(R.id.mychat_tv_say);
        }
    }
    public class hisViewHolder extends RecyclerView.ViewHolder {
        private ImageView head;
        private TextView textView;
        private ProgressBar progressBar;
        public hisViewHolder(@NonNull View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.hischat_im_head);
            textView=itemView.findViewById(R.id.hischat_tv_say);
        }
    }

    public void addMessage(ChatBean chatBean){
        list.add(chatBean);
        notifyDataSetChanged();
    }

}
