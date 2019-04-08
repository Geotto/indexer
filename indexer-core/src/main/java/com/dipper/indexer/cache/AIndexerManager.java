/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dipper.indexer.cache;

import com.dipper.indexer.model.IndexEntry;
import com.dipper.indexer.model.Indexer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 索引管理器
 * @author nbwu
 */
public abstract class AIndexerManager {
    protected Map<Integer, Indexer> indexerMap;
    
    public AIndexerManager(){
        indexerMap = new HashMap<Integer, Indexer>();
    }
    
    /**
     * 获取类型列表
     * @return 类型列表
     */
    public abstract List<Integer> getTypes();
    
    /**
     * 获取索引器大小
     * @return 索引器大小
     */
    public abstract int getIndexerSize();
    
    /**
     * 加载索引
     * @param commonIndexer 通用索引，用于加载时将需要进行通用索引的数据添加到当中
     * @param type 索引的数据类型
     * @return 创建的索引，如果为空则该类型的数据不填加到索引管理器当中
     */
    public abstract Indexer loadType(Indexer commonIndexer, int type);
    
    public void load(){
        Indexer commonIndexer = new Indexer(getIndexerSize());
        for(Integer type : getTypes()){
            Indexer indexer = loadType(commonIndexer, type);
            if(indexer != null){
                indexerMap.put(type, indexer);
            }
        }
        
        indexerMap.put(0, commonIndexer);
    }

    /**
     * 执行搜索
     * @param keyword 关键词
     * @param type 搜索类型，0表示执行全局搜索
     * @param limit 数量限制
     * @return 搜索结果。如果为空则表示没有找打需要查找的数据类型
     */
    public Set<IndexEntry> find(String keyword, int type, int limit){
        if(indexerMap.containsKey(type)){
            return indexerMap.get(type).find(keyword, limit);
        }
        
        return null;
    }
}
