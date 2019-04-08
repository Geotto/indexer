/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dipper.indexer.model;

/**
 * 通用存储器
 * @author nbwu
 */
public interface CommonStore extends Comparable{
    int getType();
    int getId();
    String getKey();
    String getName();
    int getParent();
    byte getStatus();
}
