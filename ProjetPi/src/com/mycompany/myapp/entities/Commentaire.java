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
public class Commentaire {

    private int commentaire_id;
    private int sujet_id;
    private int createur_id;
    private String texte;
    private String date;
    private int score;

    public Commentaire(int commentaire_id, int sujet_id, int createur_id, String texte, String date, int score) {
        this.commentaire_id = commentaire_id;
        this.sujet_id = sujet_id;
        this.createur_id = createur_id;
        this.texte = texte;
        this.date = date;
        this.score = score;
    }

    public Commentaire(int sujet_id, int createur_id, String texte, String date, int score) {
        this.sujet_id = sujet_id;
        this.createur_id = createur_id;
        this.texte = texte;
        this.date = date;
        this.score = score;
    }

    public int getCommentaire_id() {
        return commentaire_id;
    }

    public int getSujet_id() {
        return sujet_id;
    }

    public void setCommentaire_id(int commentaire_id) {
        this.commentaire_id = commentaire_id;
    }

    public void setSujet_id(int sujet_id) {
        this.sujet_id = sujet_id;
    }

    public int getCreateur_id() {
        return createur_id;
    }

    public void setCreateur_id(int createur_id) {
        this.createur_id = createur_id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
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

    @Override
    public String toString() {
        return "Commentaire{" + "commentaire_id=" + commentaire_id + ", sujet_id=" + sujet_id + ", createur_id=" + createur_id + ", texte=" + texte + ", date=" + date + ", score=" + score + '}';
    }

}
