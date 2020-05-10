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
public class Sujet {

    private int sujet_id;
    private int createur_id;
    private String titre;
    private String description;
    private String date;
    private int score;
    private int vues;

    public Sujet() {

    }

    public Sujet(int sujet_id, int createur_id, String titre, String description, String date, int score, int vues) {
        this.sujet_id = sujet_id;
        this.createur_id = createur_id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.score = score;
        this.vues = vues;
    }

    public Sujet(int createur_id, String titre, String description, String date, int score, int vues) {
        this.createur_id = createur_id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.score = score;
        this.vues = vues;
    }

    public int getSujet_id() {
        return sujet_id;
    }

    public int getCreateur_id() {
        return createur_id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getVues() {
        return vues;
    }

    public void setVues(int vues) {
        this.vues = vues;
    }

    public void setSujet_id(int sujet_id) {
        this.sujet_id = sujet_id;
    }

    public void setCreateur_id(int createur_id) {
        this.createur_id = createur_id;
    }

    @Override
    public String toString() {
        return "Sujet{" + "sujet_id=" + sujet_id + ", createur_id=" + createur_id + ", titre=" + titre + ", description=" + description + ", date=" + date + ", score=" + score + ", vues=" + vues + '}';
    }

}
