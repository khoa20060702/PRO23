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
    String category;
    String album;
    String artist;
    String image;
    String dura;
    public Nhac() {
    }

    public Nhac(String name, String category, String album, String artist, String image, String dura) {
        this.name = name;
        this.category = category;
        this.album = album;
        this.artist = artist;
        this.image = image;
        this.dura = dura;
    }

    public void setDura(String dura) {
        this.dura = dura;
    }

    public String getDura() {
        return dura;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
   
    
    
    
    
    public static void main(String args[]) {
        // TODO code application logic here
    }
}
