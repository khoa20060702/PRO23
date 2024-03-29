/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swanmusic.dao;
import com.swanmusic.entity.Account;
import com.swanmusic.utils.XJdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author tran
 */
public  class AccountDAO extends DAO<Account, String>{

    @Override
    public void insert(Account entity){
        String sql= "INSERT  INTO  TAIKHOAN(TENTAIKHOAN,MATKHAU,EMAIL,SODIENTHOAI) VALUES  (?,?,?,?)";
        XJdbc.update(sql, entity.getTENTK(),entity.getMatkhau(),entity.getEmail(),entity.getSODIENTHOAI());
    }
      @Override
     public void update(Account entity) {
      String sql= "UPDATE TAIKHOAN SET TENTAIKHOAN=? ,MATKHAU=?,EMAIL=?,SODIENTHOAI=? ";
    XJdbc.update(sql, entity.getTENTK(),entity.getMatkhau(),entity.getEmail(),entity.getSODIENTHOAI());
   }     
     @Override
    public void delete(String key) {
        String sql= "DELETE FROM TAIKHOAN WHERE TENTAIKHOAN=?";
        XJdbc.update(sql, key);
    }
     @Override
    public List<Account> selectAll() {
        String sql= "SELECT * FROM TAIKHOAN";
        return selectBySql(sql);
    }

    @Override
    public Account selectByID(String keys) {
        String sql= "SELECT * FROM TAIKHOAN WHERE TENTAIKHOAN=?";
        return selectBySql(sql,keys).isEmpty()? null:selectBySql(sql,keys).get(0);
    }
     @Override
    protected List<Account> selectBySql(String sql, Object... args) {
        List<Account> list=new ArrayList<Account>();       
        try {
            ResultSet rs=null;
            try {
                rs=XJdbc.query(sql,args);
                while (rs.next()) {
                    Account e =new Account();
                    e.setTENTK(rs.getString(1));
                    e.setMatkhau(rs.getString(2));
                    e.setEmail(rs.getString(4));
              e.setVaiTro(rs.getBoolean(3));
                   e.setSODIENTHOAI(rs.getString(5)); 
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
    public List<Account> selectByKey(String keys) {
        String sql= "SELECT * FROM TAIKHOAN WHERE  TENTAIKHOAN LIKE ? ";
        keys= "%"+keys+"%";
        return selectBySql(sql, keys, keys);
    }
}