/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dipper.indexer.model;

import java.util.Set;

/**
 * 索引器
 * @author nbwu
 */
public class Indexer {
    /**
     * 数据存储
     */
    IndexStore store;
    
    public Indexer(int count){
        this.store = new IndexStore(IndexStore.IDX_START, IndexStore.IDX_STOP, count);
    }
    
    public void add(String key, Comparable data, int weight){
        IndexEntry entry = new IndexEntry();
        entry.key = key;
        entry.data = data;
        entry.weight = weight;
        entry.index = 0;
        
        this.store.add(entry);
    }
    
    public Set<IndexEntry> find(String key, int limit){
        return this.store.find(key, 0, limit);
    }
}
