/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.swanmusic.entity;

/**
 *
 * @author phuon
 */
public class Nghesi {

    String name;
    int sl_album;
    int sl_dsnhac;
    String anh;

    public Nghesi() {
    }

    public Nghesi(String name, int sl_album, int sl_dsnhac, String anh) {
        this.name = name;
        this.sl_album = sl_album;
        this.sl_dsnhac = sl_dsnhac;
        this.anh = anh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSl_album() {
        return sl_album;
    }

    public void setSl_album(int sl_album) {
        this.sl_album = sl_album;
    }

    public int getSl_dsnhac() {
        return sl_dsnhac;
    }

    public void setSl_dsnhac(int sl_dsnhac) {
        this.sl_dsnhac = sl_dsnhac;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
            
   
}
