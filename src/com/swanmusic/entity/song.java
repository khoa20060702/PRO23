/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swanmusic.entity;

/**
 *
 * @author ACER
 */
public class song {
    String songname;
    String songdura;
    String artist;

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public void setSongdura(String songdura) {
        this.songdura = songdura;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongname() {
        return songname;
    }

    public String getSongdura() {
        return songdura;
    }

    public String getArtist() {
        return artist;
    }
}
