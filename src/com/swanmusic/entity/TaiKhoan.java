/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.swanmusic.entity;

/**
 *
 * @author phuon
 */
public class TaiKhoan {
    String Name;
    String matkhau;
    String vaitro;
    String email;
    String sodienthoai;

    public TaiKhoan() {
    }

    public TaiKhoan(String Name, String matkhau, String vaitro, String email, String sodienthoai) {
        this.Name = Name;
        this.matkhau = matkhau;
        this.vaitro = vaitro;
        this.email = email;
        this.sodienthoai = sodienthoai;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }
    
    
    
    
    

}
