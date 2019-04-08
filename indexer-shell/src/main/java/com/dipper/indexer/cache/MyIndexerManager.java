/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dipper.indexer.cache;

import com.dipper.indexer.helper.PinyinUtils;
import com.dipper.indexer.helper.WordPartUtils;
import com.dipper.indexer.model.District;
import com.dipper.indexer.model.Indexer;
import com.github.stuxuhai.jpinyin.PinyinException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 我的索引管理器
 * @author nbwu
 */
public class MyIndexerManager extends AIndexerManager{
    /**
     * 日志工具
     */
    private static final Logger logger = LogManager.getLogger(MyIndexerManager.class);
    
    /**
     * 行政区索引类型
     */
    public static final int TYPE_DISTRICT = 1;
    
    /**
     * 子目项目大小
     */
    private final int INDEXER_SIZE = 10;
    
    /**
     * 数据类型列表
     */
    private final List<Integer> types = new ArrayList<Integer>();
    
    private MyIndexerManager(){
        types.add(TYPE_DISTRICT);
    }
    
    public static MyIndexerManager instance(){
        return InstanceHolder.instance;
    }

    @Override
    public List<Integer> getTypes() {
        return types;
    }

    @Override
    public int getIndexerSize() {
        return INDEXER_SIZE;
    }

    @Override
    public Indexer loadType(Indexer commonIndexer, int type) {
        Indexer idx = null;
        if(type == TYPE_DISTRICT){
            idx = new Indexer(getIndexerSize());
            
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            try{
                is = MyIndexerManager.class.getResourceAsStream("/com/dipper/indexer/districts.txt");
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                
                String line = br.readLine();
                while(line != null){
                    String[] args = line.split("\t");
                    if(args.length == 4){
                        District district = new District();
                        district.province = args[0];
                        district.city = args[1];
                        district.code = args[2];
                        district.district = args[3];
                        
                        String pinyin = PinyinUtils.toPinyin(district.province, PinyinUtils.MODE_FULL);
                        List<String> words = WordPartUtils.splitStart(pinyin, 24);
                        for(String word : words){
                            idx.add(word, district, word.length());
                        }
                        
                        pinyin = PinyinUtils.toPinyin(district.city, PinyinUtils.MODE_FULL);
                        words = WordPartUtils.splitStart(pinyin, 24);
                        for(String word : words){
                            idx.add(word, district, word.length());
                        }
                        
                        pinyin = PinyinUtils.toPinyin(district.district, PinyinUtils.MODE_FULL);
                        words = WordPartUtils.splitStart(pinyin, 24);
                        for(String word : words){
                            idx.add(word, district, word.length());
                        }
                    }
                    
                    line = br.readLine();
                }
            } catch (IOException ex) {
                logger.error("读取文件时发生异常", ex);
            } catch (PinyinException ex) {
                logger.error("解析拼音时发生异常", ex);
            }finally{
                if(br != null){
                    try {
                        br.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if(isr != null){
                    try {
                        isr.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if(is != null){
                    try {
                        is.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        
        return idx;
    }
    
    private static class InstanceHolder{
        public static MyIndexerManager instance = new MyIndexerManager();
    }
}
