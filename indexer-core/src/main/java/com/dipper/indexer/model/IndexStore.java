/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dipper.indexer.model;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * storing index data
 * @author nbwu
 */
public class IndexStore {
    /**
     * 开始位置
     */
    public static final char IDX_START = 0x20;
    
    /**
     * 停止位置
     */
    public static final char IDX_STOP = 0x7F;
    
    /**
     * 开始位置（包含）
     */
    protected char start;
    
    /**
     * 结束位置
     */
    protected char stop;
    
    /**
     * 索引入口集合
     */
    protected Set<IndexEntry> set;
    
    /**
     * 子项目
     */
    protected IndexStore[] children;
    
    /**
     * 子项目数量
     */
    protected int childcount;
    
    public IndexStore(char start, char stop, int childcount){
        this.start = start;
        this.stop = stop;
        this.set = new HashSet<>();
        this.children = null;
        this.childcount = childcount;
    }
    
    /**
     * 添加索引
     * @param entry 索引入口
     */
    public void add(IndexEntry entry){
        if(entry.index >= entry.key.length())
            return;
        
        char ch = entry.key.charAt(entry.index);
        
        //是最后一个字符
        if(entry.index == entry.key.length() - 1){
            if(this.start <= ch && this.stop > ch){
                this.set.add(entry);
            }
        }else{
            entry.index++;
            ch = entry.key.charAt(entry.index);
            
            //生成子项目
            if(this.children == null){
                char current = IDX_START;
                int step = (int)Math.ceil((double)(IDX_STOP - IDX_START) / this.childcount);
                this.children = new IndexStore[this.childcount];
                for(int i=0; i<this.childcount; i++){
                    this.children[i] = new IndexStore(current, (char)(current + step), this.childcount);
                    if(current <= ch && (char)(current + step) > ch){
                        this.children[i].add(entry);
                    }
                    
                    current = (char)(current + step);
                }
            }else{
                for(int i=0; i<this.childcount; i++){
                    if(this.children[i].start <= ch && this.children[i].stop > ch){
                        this.children[i].add(entry);
                        break;
                    }
                }
            }
        } 
    }

    /**
     * 搜索索引项目
     * @param key 关键词
     * @param index 当前查找位置
     * @param limit 限制数量
     * @return 查找结果
     */
    public Set<IndexEntry> find(String key, int index, int limit){
        Set<IndexEntry> rt = new TreeSet<IndexEntry>();
        if(index >= key.length())
            return rt;
        
        char ch = key.charAt(index);
        
        //如果是最后一个字符
        if(index == key.length() - 1){
            if(ch >= this.start && ch < this.stop){
                for(IndexEntry entry : this.set){
                    if(entry.key.startsWith(key)){
                        rt.add(entry);
                        if(rt.size() >= limit)
                            return rt;
                    }
                }
            }
            
            //添加所有子项目中的入口
            appendChildItems(rt, key, limit);
        }else{
            index++;
            ch = key.charAt(index);
            
            //存在子项目
            if(this.children != null){
                for(IndexStore store : this.children){
                    if(ch >= store.start && ch < store.stop){
                        return store.find(key, index, limit);
                    }
                }
            }
        }
        
        return rt;
    }

    /**
     * 将子项目中的所有入口添加到rt
     * @param rt 入口集合
     * @param limit 大小限制
     */
    private void appendChildItems(Set<IndexEntry> rt, String key, int limit) {
        if(this.children == null)
            return;
        
        for(IndexStore store : this.children){
            for(IndexEntry entry : store.set){
                if(entry.key.startsWith(key)){
                    rt.add(entry);
                    if(rt.size() >= limit)
                        return;
                }
            }
            
            store.appendChildItems(rt, key, limit);
        }
    }
}
