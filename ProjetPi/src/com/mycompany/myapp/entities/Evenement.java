/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mohamed Turki
 */
public class Evenement {

    private int id;
    private int club_id;
    private String nom_club;
    private String nom_evenement;
    private String image_evenement;
    private Date heure_debut;
    private Date heure_fin;

    public Evenement() {
    }


    public Evenement(int id, String nom_club, String nom_evenement, Date heure_debut, Date heure_fin) {
        this.id = id;
        this.nom_club = nom_club;
        this.nom_evenement = nom_evenement;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
    }

    public String getImage_evenement() {
        return image_evenement;
    }

    public void setImage_evenement(String image_evenement) {
        this.image_evenement = image_evenement;
    }

    public String getNom_club() {
        return nom_club;
    }

    public void setNom_club(String nom_club) {
        this.nom_club = nom_club;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }

    public String getNom_evenement() {
        return nom_evenement;
    }

    public void setNom_evenement(String nom_evenement) {
        this.nom_evenement = nom_evenement;
    }

    public Date getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(Date heure_debut) {
        this.heure_debut = heure_debut;
    }

    public Date getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(Date heure_fin) {
        this.heure_fin = heure_fin;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", club_id=" + club_id + ", nom_club=" + nom_club + ", nom_evenement=" + nom_evenement + ", image_evenement=" + image_evenement + ", heure_debut=" + heure_debut + ", heure_fin=" + heure_fin + '}';
    }

    
}
