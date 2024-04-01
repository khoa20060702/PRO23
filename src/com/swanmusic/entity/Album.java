/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swanmusic.entity;

/**
 *
 * @author tran
 */
public class Album {
    String AlbumName;
    String Category;
     String Artist;
     String ReleaseTime;
     String Image;

    public Album() {
    }

    public Album(String AlbumName, String Category, String Artist, String ReleaseTime, String Image) {
        this.AlbumName = AlbumName;
        this.Category = Category;
        this.Artist = Artist;
        this.ReleaseTime = ReleaseTime;
        this.Image = Image;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String AlbumName) {
        this.AlbumName = AlbumName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String Artist) {
        this.Artist = Artist;
    }

    public String getReleaseTime() {
        return ReleaseTime;
    }

    public void setReleaseTime(String ReleaseTime) {
        this.ReleaseTime = ReleaseTime;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
}
