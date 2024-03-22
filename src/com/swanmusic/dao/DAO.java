/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swanmusic.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author TRAN
 */
public abstract class DAO<E,K> {
    public abstract void insert(E entity);
    public abstract void update(E entity);
    public abstract void delete(K key);
    public abstract List<E> selectAll();
    public abstract E selectByID(K keys);
    public abstract List<E> selectByKey(String keys);
    protected abstract List<E> selectBySql(String sql , Object... args);
}
