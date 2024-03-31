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

    public Nhac() {
    }

    public Nhac(String name, String category, String album, String artist, String image) {
        this.name = name;
        this.category = category;
        this.album = album;
        this.artist = artist;
        this.image = image;
    }
    String name;
    String category;
    String album;
    String artist;
    String image;
   
    
    
    
    
    public static void main(String args[]) {
        // TODO code application logic here
    }
}
