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
    String AlbumCategory;
     String AlbumArtist;
     String AlbumReleaseTime;
     String AlbumImage;

    public Album() {
    }

    public Album(String AlbumName, String AlbumCategory, String AlbumArtist, String AlbumReleaseTime, String AlbumImage) {
        this.AlbumName = AlbumName;
        this.AlbumCategory = AlbumCategory;
        this.AlbumArtist = AlbumArtist;
        this.AlbumReleaseTime = AlbumReleaseTime;
        this.AlbumImage = AlbumImage;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String AlbumName) {
        this.AlbumName = AlbumName;
    }

    public String getAlbumCategory() {
        return AlbumCategory;
    }

    public void setAlbumCategory(String AlbumCategory) {
        this.AlbumCategory = AlbumCategory;
    }

    public String getAlbumArtist() {
        return AlbumArtist;
    }

    public void setAlbumArtist(String AlbumArtist) {
        this.AlbumArtist = AlbumArtist;
    }

    public String getAlbumReleaseTime() {
        return AlbumReleaseTime;
    }

    public void setAlbumReleaseTime(String AlbumReleaseTime) {
        this.AlbumReleaseTime = AlbumReleaseTime;
    }

    public String getAlbumImage() {
        return AlbumImage;
    }

    public void setAlbumImage(String AlbumImage) {
        this.AlbumImage = AlbumImage;
    }
}