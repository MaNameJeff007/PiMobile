/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Selim Chikh Zaouali
 */
public class Reclamation {

    private int id;
    private String date;
    private String etat;
    private int note;
    private int parent;
    private String details;

    
    public Reclamation() {
        
    }
    public Reclamation(String date, String etat, int note, int parent) {
        this.date = date;
        this.etat = etat;
        this.note = note;
        this.parent = parent;
    }

    public Reclamation(String date, String etat, int note, int parent, String details) {
        this.date = date;
        this.etat = etat;
        this.note = note;
        this.parent = parent;
        this.details = details;
    }

    public Reclamation(int id, String date, String etat, int note, int parent, String details) {
        this.date = date;
        this.etat = etat;
        this.note = note;
        this.parent = parent;
        this.details = details;
        this.id = id;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getEtat() {
        return etat;
    }

    public int getNote() {
        return note;
    }

    public int getParent() {
        return parent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

}