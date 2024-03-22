/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swanmusic.dao;
import com.swanmusic.entity.Category;
import com.swanmusic.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author tran
 */
public  class CategoryDAO extends DAO<Category, String>{

    @Override
    public void insert(Category entity){
        String sql= "INSERT  INTO  THELOAI(TENTL) VALUES  (?)";
        XJdbc.update(sql, entity.getTheloai());
    }

    @Override
    public void update(Category entity){
        String sql= "UPDATE  THELOAI  SET  TENTL=? WHERE  ID=?";
        XJdbc.update(sql,entity.getTheloai());
    }

    @Override
    public void delete(String key){
        String sql= "DELETE FROM THELOAI WHERE ID=?";
        XJdbc.update(sql, key);
    }
  @Override
    public List<Category> selectAll() {
        String sql= "SELECT * FROM THELOAI";
        return selectBySql(sql);
    }

    @Override
    public Category selectByID(String keys) {
        String sql= "SELECT * FROM THELOAI WHERE ID=?";
        return selectBySql(sql,keys).isEmpty()? null:selectBySql(sql,keys).get(0);
    }

    @Override
    protected List<Category> selectBySql(String sql, Object... args) {
        List<Category> list=new ArrayList<Category>();       
        try {
            ResultSet rs=null;
            try {
                rs=XJdbc.query(sql,args);
                while (rs.next()) {
                    Category e =new Category();
                    e.setTheloai(rs.getString(1));
                 
                    list.add(e);
                }
            }finally{
            rs.getStatement().getConnection().close();
            }
        } catch (Exception ex) {
            throw new RuntimeException();
        }
        return list;
    }

    @Override
    public List<Category> selectByKey(String keys) {
        String sql= "SELECT * FROM THELOAI WHERE ID LIKE ? OR TENTL LIKE ? ";
        keys= "%"+keys+"%";
        return selectBySql(sql, keys, keys);
    }}
