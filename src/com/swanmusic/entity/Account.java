/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swanmusic.entity;

/**
 *
 * @author tran
 */
public class Account {

    public Account(String iduser, boolean vaitro, String username, String MatKhau) {
        this.iduser = iduser;
        this.vaitro = vaitro;
        this.username = username;
        this.MatKhau = MatKhau;
    }

    public Account() {
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public boolean isVaitro() {
        return vaitro;
    }

    public void setVaitro(boolean vaitro) {
        this.vaitro = vaitro;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }
     String iduser ;
     boolean vaitro ;
    String username ;
    String MatKhau ;
      public Object[] toObjects(boolean isManager){
       return new Object[]{
           iduser,
           isManager?MatKhau:"••••••••",
           username,
           vaitro?"Người dùng":"Nhân viên"
       };
    }
}
