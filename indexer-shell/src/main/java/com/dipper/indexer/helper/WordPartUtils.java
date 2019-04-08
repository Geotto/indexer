/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dipper.indexer.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * 分词工具
 * @author nbwu
 */
public class WordPartUtils {
    public static List<String> split(String origin, int limit){
        List<String> rt = new ArrayList<String>();
        int max = Math.min(limit, origin.length());
        for(int len=1; len <= max; len++){
            for(int index = 0; index + len <= origin.length(); index++){
                rt.add(origin.substring(index, index + len));
            }
        }
        
        return rt;
    }
    
    public static List<String> splitStart(String origin, int limit){
        List<String> rt = new ArrayList<>();
        int max = Math.min(limit, origin.length());
        for(int len=1; len <= max; len++){
            rt.add(origin.substring(0, len));
        }
        
        return rt;
    }
}
