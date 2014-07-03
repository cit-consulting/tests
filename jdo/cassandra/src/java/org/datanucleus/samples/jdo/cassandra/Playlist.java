/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.datanucleus.samples.jdo.cassandra;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 
 * @author bergun
 */
@PersistenceCapable
public class Playlist
{

    @PrimaryKey    
    private int id;

    @PrimaryKey
    @Column(name = "song_order")
    private int songOrder;

    @Persistent
    @Column(name = "song_id")
    private int songId;

    private String title;

    private String album;

    private String artist;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getSongOrder()
    {
        return songOrder;
    }

    public void setSongOrder(int songOrder)
    {
        this.songOrder = songOrder;
    }

    public int getSongId()
    {
        return songId;
    }

    public void setSongId(int songId)
    {
        this.songId = songId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

}
