/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author admin
 */
public class Likes {

    private int like_id;
    private int sujet_id;
    private int commentaire_id;
    private int createur_id;
    private String type;

    public Likes(int sujet_id, int createur_id, String type) {
        this.like_id = like_id;
        this.sujet_id = sujet_id;
        this.createur_id = createur_id;
        this.type = type;
    }

    public Likes(int createur_id, String type, int commentaire_id) {
        this.like_id = like_id;
        this.commentaire_id = commentaire_id;
        this.createur_id = createur_id;
        this.type = type;
    }

    public int getLike_id() {
        return like_id;
    }

    public int getSujet_id() {
        return sujet_id;
    }

    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }

    public void setSujet_id(int sujet_id) {
        this.sujet_id = sujet_id;
    }

    public void setCommentaire_id(int commentaire_id) {
        this.commentaire_id = commentaire_id;
    }

    public void setCreateur_id(int createur_id) {
        this.createur_id = createur_id;
    }

    public int getCommentaire_id() {
        return commentaire_id;
    }

    public int getCreateur_id() {
        return createur_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Likes{" + "like_id=" + like_id + ", sujet_id=" + sujet_id + ", commentaire_id=" + commentaire_id + ", createur_id=" + createur_id + ", type=" + type + '}';
    }

}
