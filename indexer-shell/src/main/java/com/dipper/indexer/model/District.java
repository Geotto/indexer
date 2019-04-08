/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dipper.indexer.model;

import com.dipper.indexer.cache.MyIndexerManager;
import java.util.Objects;

/**
 * 行政区
 * @author nbwu
 */
public class District implements CommonStore{
    /**
     * 省份
     */
    public String province;
    
    /**
     * 地级市
     */
    public String city;
    
    /**
     * 县级市
     */
    public String district;
    
    /**
     * 代码
     */
    public String code;

    public int getType() {
        return MyIndexerManager.TYPE_DISTRICT;
    }

    public int getId() {
        return Integer.parseInt(code);
    }

    public String getKey() {
        return this.code;
    }

    public String getName() {
        return this.district;
    }

    public int getParent() {
        return Integer.parseInt(this.code) / 100 * 100;
    }

    public byte getStatus() {
        return 0;
    }

    @Override
    public int compareTo(Object o) {
        if(o == null){
            return 0;
        }
        
        if(o instanceof District){
            District other = (District)o;
            return Integer.parseInt(this.code) - Integer.parseInt(other.code);
        }
        
        return 0;
    }
    
    @Override
    public int hashCode(){
        return this.code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.compareTo(obj) == 0;
    }
}
