/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dipper.indexer.helper;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * 拼音帮助类
 * @author nbwu
 */
public class PinyinUtils {
    /**
     * 全拼
     */
    public static final int MODE_FULL = 1;
    
    /**
     * 简拼
     */
    public static final int MODE_PART = 2;
    
    /**
     * 分隔符
     */
    public static final char SEPERATOR = ',';
    
    /**
     * 分隔符
     */
    public static final String SEP = new String(new char[]{ SEPERATOR });
    
    /**
     * 将字符串转为拼音
     * @param origin 原始字符串
     * @param mode 转换模式，1-全拼，2-简拼
     * @return 转换后的拼音字符串
     * @throws com.github.stuxuhai.jpinyin.PinyinException
     */
    public static String toPinyin(String origin, int mode) throws PinyinException{
        boolean flag = true;
        StringBuilder builder = new StringBuilder();
        String result = PinyinHelper.convertToPinyinString(origin, SEP, PinyinFormat.WITHOUT_TONE);
        for(int i=0; i<result.length(); i++){
            char ch = result.charAt(i);
            switch(ch){
                case SEPERATOR:
                    flag = true;
                    break;
                    
                default:
                    switch(mode){
                        case MODE_FULL:
                            builder.append(ch);
                            break;
                            
                        case MODE_PART:
                            if(flag){
                                builder.append(ch);
                                flag = false;
                            }
                            break;
                    }
                    break;
            }
        }
        
        return builder.toString();
    }
}
