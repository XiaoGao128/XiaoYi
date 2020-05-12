package com.example.xiaoyi_test_2.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.xiaoyi_test_2.R;

public class Fragment_type_cloth extends Fragment {
    private send send;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_type_cloth,container,false);
        bindViews(view);
        return view;
    }

    private void bindViews(View view) {
        final Button[] buttons=new Button[8];
        for (int i=0;i<8;i++) {
            String tag = "button" + (i + 1);
            buttons[i] = view.findViewWithTag(tag);
            final int j=i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (j>=0&&j<=3)
                    send.send("男装",buttons[j].getText().toString());
                    else send.send("女装",buttons[j].getText().toString());
                }
            });
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof send){
            this.send= (Fragment_type_cloth.send) context;
        }else throw new RuntimeException("未生名send接口");
    }

    public interface send{
        public void send(String btext, String stext);
    }

}
