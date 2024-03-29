/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.swanmusic.entity;

/**
 *
 * @author phuon
 */
public class Nhac {
    String name;
    String theloai;
    String album;
    String nghesi;

    public Nhac() {
    }

    public Nhac(String name, String theloai, String album, String nghesi) {
        this.name = name;
        this.theloai = theloai;
        this.album = album;
        this.nghesi = nghesi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheloai() {
        return theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getNghesi() {
        return nghesi;
    }

    public void setNghesi(String nghesi) {
        this.nghesi = nghesi;
    }
    
    
    
    
    
    
    public static void main(String args[]) {
        // TODO code application logic here
    }
}
