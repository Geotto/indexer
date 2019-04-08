/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dipper.indexer.model;

import java.util.Objects;

/**
 * 索引入口
 * @author nbwu
 */
public class IndexEntry implements Comparable{
    /**
     * 索引数据
     */
    protected Comparable data;
    
    /**
     * 关键词
     */
    protected String key;
    
    /**
     * 权重
     */
    protected int weight;
    
    /**
     * 当前搜索位置
     */
    protected int index;
    
    /**
     * 哈希值
     */
    protected int hashcode = 0;
    
    @Override
    public int compareTo(Object o){
        if(o == null)
            return 0;
        
        if(o instanceof IndexEntry){
            IndexEntry other = (IndexEntry)o;
            return this.data.compareTo(other.data);
        }
        
        return 0;
    }
    
    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        
        if(o instanceof IndexEntry){
            IndexEntry other = (IndexEntry)o;
            if(this.weight == other.weight & this.key.equals(other.key)){
                return this.data.compareTo(other.data) == 0;
            }else{
                return false;
            }
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        if(this.hashcode == 0){
            String code = String.format("%d,%s,%d", this.data.hashCode(), this.key, this.weight);
            this.hashcode = code.hashCode();
        }
        
        return hashcode;
    }
    
    public Comparable getData(){
        return this.data;
    }
}
