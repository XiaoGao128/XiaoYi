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

public class Fragment_type_sentic extends Fragment {
    private send send;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_type_sentic,container,false);
        bindViews(view);
        return view;
    }

    private void bindViews(View view) {
        final Button[] buttons=new Button[11];
        for (int i=0;i<11;i++) {
            String tag = "button" + (i + 1);
            buttons[i] = view.findViewWithTag(tag);
            final int j=i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  send.send("数码",buttons[j].getText().toString());

                }
            });
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof send){
            this.send= (Fragment_type_sentic.send) context;
        }else throw new RuntimeException("未生名send接口");
    }

    public interface send{
        public void send(String btext, String stext);
    }

}
