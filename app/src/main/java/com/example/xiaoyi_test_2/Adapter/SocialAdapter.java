package com.example.xiaoyi_test_2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xiaoyi_test_2.Activity.CompDetailActivity;
import com.example.xiaoyi_test_2.Activity.MainActivity;
import com.example.xiaoyi_test_2.Bean.SocialBean;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Service.Component;
import com.example.xiaoyi_test_2.View.GoodView;

import java.util.ArrayList;

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.viewHolder> {
    private Context context;
    private ArrayList<SocialBean> list;
    public SocialAdapter(Context context, ArrayList<SocialBean> list) {
        this.context = context;
        this.list=list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("----------aaa","1");
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_social, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        Log.d("----------aaa","2");
        holder.tv_good.setText(list.get(position).getGoodnum());
        holder.tv_time.setText(list.get(position).getTime());
        holder.tv_describ.setText(list.get(position).getDescrib());
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_cm.setText(list.get(position).getCompnum());
        Glide.with(context).load(list.get(position).getHead()).into(holder.im_head);
//        String[] imgs=list.get(position).getImgpath().split("|");
//        ImageView[] imageView={holder.im_1,holder.im_2,holder.im_3};
//        for (int i=0;i<imgs.length;i++){
//            if (!imgs[i].equals("")&&imgs[i]!=null&&imgs[i].length()>7){
//                Log.d("SocialAdapter-------",""+i);
//                LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) imageView[1].getLayoutParams();
//                layoutParams.height= 90;
//                layoutParams.width=90;
//                imageView[1].setLayoutParams(layoutParams);
//                Glide.with(context).load(imgs[i]).into(imageView[1]);
//                imageView[1].setVisibility(View.VISIBLE);
//            }
//        }

        holder.im_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.im_good.getBackground().getConstantState().equals(holder.itemView.getResources().getDrawable(R.drawable.dz_gray).getConstantState())) {
                    holder.im_good.setBackground(holder.itemView.getResources().getDrawable(R.drawable.dz_pink));
                    holder.goodView.setImage(R.drawable.dz_pink);
                    holder.goodView.setText("+1");
                    holder.goodView.show(holder.im_good);
                    holder.tv_good.setText("" + (Integer.parseInt(holder.tv_good.getText().toString()) +1));
                    Component.socGood(list.get(position).getId(),"1");
                }   else
                {
                    holder.im_good.setBackground(holder.itemView.getResources().getDrawable(R.drawable.dz_gray));
                    holder.goodView.setImage(R.drawable.dz_gray);
                    holder.goodView.setText("-1");
                    holder.goodView.show(holder.im_good);
                    Component.socGood(list.get(position).getId(),"0");
                    holder.tv_good.setText("" + (Integer.parseInt(holder.tv_good.getText().toString()) -1));
                }


            }
        });
        holder.tv_describ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, CompDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("soc",list.get(position));
                intent.putExtras(bundle);
                Log.d("----------aaa","4");
                ((MainActivity)context).startActivityForResult(intent,2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        public ImageView im_good, im_cm,im_head,im_1,im_2,im_3;
        public TextView tv_good, tv_cm,tv_title,tv_describ,tv_name,tv_time;
        public GoodView goodView = new GoodView(itemView.getContext());

        public viewHolder(@NonNull final View itemView) {
            super(itemView);
            im_good = itemView.findViewById(R.id.social_im_good);
            im_cm = itemView.findViewById(R.id.social_im_cm);
            tv_good = itemView.findViewById(R.id.social_tv_good);
            tv_cm = itemView.findViewById(R.id.social_tv_cm);
            im_head=itemView.findViewById(R.id.item_social_head);
            tv_title=itemView.findViewById(R.id.social_tv_title);
            tv_name=itemView.findViewById(R.id.social_tv_name);
            tv_describ=itemView.findViewById(R.id.social_describ);
            tv_time=itemView.findViewById(R.id.social_tv_time);
            im_1=itemView.findViewById(R.id.social_img_1);
            im_2=itemView.findViewById(R.id.social_img_2);
            im_3=itemView.findViewById(R.id.social_img_3);
            im_1.setVisibility(View.INVISIBLE);
            im_2.setVisibility(View.INVISIBLE);
            im_3.setVisibility(View.INVISIBLE);

        }
    }
}
