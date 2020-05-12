package com.example.xiaoyi_test_2.Utils;

import java.util.Random;

public class RangeUtil {
    public static String getRange() {
        char rang[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String s="";
        Random random=new Random();
        for (int i=0;i<20;i++){
            s+=rang[random.nextInt(32)];
        }
        return  s;
    }
}
