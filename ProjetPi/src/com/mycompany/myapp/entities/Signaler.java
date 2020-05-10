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
public class Signaler {

    private int signaler_id;
    private int sujet_id;
    private int commentaire_id;
    private int nombre;
    private String type;

    public Signaler(int signaler_id, int sujet_id, int commentaire_id, int nombre, String type) {
        this.signaler_id = signaler_id;
        this.sujet_id = sujet_id;
        this.commentaire_id = commentaire_id;
        this.nombre = nombre;
        this.type = type;
    }

    public Signaler(int sujet_id, int commentaire_id, int nombre, String type) {
        this.sujet_id = sujet_id;
        this.commentaire_id = commentaire_id;
        this.nombre = nombre;
        this.type = type;
    }

    public Signaler(int sujet_id, int nombre, String type) {
        this.sujet_id = sujet_id;
        this.nombre = nombre;
        this.type = type;
    }

    public Signaler(int nombre, String type, int commentaire_id) {
        this.commentaire_id = commentaire_id;
        this.nombre = nombre;
        this.type = type;
    }

    public int getSignaler_id() {
        return signaler_id;
    }

    public void setSignaler_id(int signaler_id) {
        this.signaler_id = signaler_id;
    }

    public int getSujet_id() {
        return sujet_id;
    }

    public void setSujet_id(int sujet_id) {
        this.sujet_id = sujet_id;
    }

    public int getCommentaire_id() {
        return commentaire_id;
    }

    public void setCommentaire_id(int commentaire_id) {
        this.commentaire_id = commentaire_id;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Signaler{" + "signaler_id=" + signaler_id + ", sujet_id=" + sujet_id + ", commentaire_id=" + commentaire_id + ", nombre=" + nombre + ", type=" + type + '}';
    }

}
