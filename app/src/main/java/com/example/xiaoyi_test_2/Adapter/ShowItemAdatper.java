package com.example.xiaoyi_test_2.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.xiaoyi_test_2.Activity.MainActivity;
import com.example.xiaoyi_test_2.Bean.ItemBean;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Utils.BitmapUtils;

import java.util.ArrayList;

public class ShowItemAdatper extends RecyclerView.Adapter<ShowItemAdatper.viewHolder> {

    private ArrayList<ItemBean> arrayList;
    private Context mcontext;
    private ShowDetail showDetail;

    public ShowItemAdatper(ArrayList<ItemBean> arrayList, Context context, ShowDetail showDetail) {
        this.arrayList = arrayList;
        mcontext = context;
        this.showDetail = showDetail;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mcontext).inflate(R.layout.layout_show_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        final ItemBean itemBean = arrayList.get(position);
        holder.name.setText(itemBean.getUsername());
        holder.describe.setText(itemBean.getDescribe());
        holder.price.setText(itemBean.getMoney());
        Glide.with(mcontext).load(itemBean.getHead()).into(holder.head);
        if (itemBean.getImgpath() != null) {
            String[] img = itemBean.getImgpath().split("\\|");
            for (int i = 0; i < img.length; i++) {
                if (img[i].length() > 2) {
                    Glide.with(mcontext).load(img[i]).into(holder.item);
                    break;
                }
            }
        }
//        Glide.with(mcontext).load()
        holder.describe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail.showDetail(itemBean);
            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail.showDetail(itemBean);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView head;
        private TextView name;
        private TextView describe;
        private ImageView item;
        private TextView price;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.show_img_head);
            name = itemView.findViewById(R.id.show_tv_name);
            describe = itemView.findViewById(R.id.show_tv_descr);
            item = itemView.findViewById(R.id.item_img_item);
            price = itemView.findViewById(R.id.item_money);
        }
    }

    public interface ShowDetail {
        public void showDetail(ItemBean itemBean);
    }
}
