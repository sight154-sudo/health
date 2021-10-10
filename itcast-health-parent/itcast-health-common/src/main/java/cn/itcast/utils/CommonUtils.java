package cn.itcast.utils;

import java.util.Random;

/**
 * @author: tang
 * @date: Create in 9:51 2021/8/23
 * @description:
 */
public class CommonUtils {

    /**
     * 产生num位的随机数
     * @return
     */
    public static String getRandByNum(int num){
        String length = "1";
        for(int i=0;i<num;i++){
            length += "0";
        }

        Random rad=new Random();

        String result  = rad.nextInt(Integer.parseInt(length)) +"";

        if(result.length()!=num){
            return getRandByNum(num);
        }
        return result;
    }

}
