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

    public Account(String TENTK, String matkhau, boolean vaiTro, String email, String SODIENTHOAI) {
        this.TENTK = TENTK;
        this.matkhau = matkhau;
        this.vaiTro = vaiTro;
        this.email = email;
        this.SODIENTHOAI = SODIENTHOAI;
    }

    public Account() {
    }

    public String getTENTK() {
        return TENTK;
    }

    public void setTENTK(String TENTK) {
        this.TENTK = TENTK;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSODIENTHOAI() {
        return SODIENTHOAI;
    }

    public void setSODIENTHOAI(String SODIENTHOAI) {
        this.SODIENTHOAI = SODIENTHOAI;
    }
   public Object[] toObjects(boolean isManager){
       return new Object[]{
           TENTK,
           isManager?matkhau:"••••••••",
           email,SODIENTHOAI,
           vaiTro?"Quản lý":" Người dùng"
       };
    }
    
     
    String   TENTK;
    String matkhau ;
    boolean vaiTro;
    String email;
    String SODIENTHOAI;

}
