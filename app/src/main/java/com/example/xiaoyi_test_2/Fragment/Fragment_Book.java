package com.example.xiaoyi_test_2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoyi_test_2.R;

public class Fragment_Book extends Fragment {
    private RadioGroup rg;
    private RecyclerView recyclerView;
    private PopupMenu pop_book;

    public static Fragment_Book newInstance() {
        
        Bundle args = new Bundle();
        
        Fragment_Book fragment = new Fragment_Book();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_frag_book,container,false);
        initViews(view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pop_book = new PopupMenu(getContext(),rg.getChildAt(0));
        pop_book.getMenuInflater().inflate(R.menu.menu_book, pop_book.getMenu());
        pop_book.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
    }

    private void initViews(View view) {
        rg=view.findViewById(R.id.book_rg);
        recyclerView=view.findViewById(R.id.book_recy);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.book_book){
                    pop_book.show();
                }
            }
        });
        rg.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((v).getId()==R.id.book_book){
                    pop_book.show();
                }
            }
        });
    }
}
