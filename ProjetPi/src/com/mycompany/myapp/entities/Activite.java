/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;



/**
 *
 * @author Mohamed Turki
 */
public class Activite {

    private int id;
    private int user_id;
    private String nomActivite;
    private String typeActivite;
    private String NomClub;
    private String Responsable;
    private int vote;

    public String getResponsable() {
        return Responsable;
    }

    public void setResponsable(String Responsable) {
        this.Responsable = Responsable;
    }
    

    public Activite(int id, int user_id, String nomActivite, String typeActivite, int vote) {
        this.id = id;
        this.user_id = user_id;
        this.nomActivite = nomActivite;
        this.typeActivite = typeActivite;
        this.vote = vote;
    }

    public String getNomClub() {
        return NomClub;
    }

    public void setNomClub(String NomClub) {
        this.NomClub = NomClub;
    }

    public Activite(int id, int user_id, String nomActivite, String typeActivite, String NomClub, int vote) {
        this.id = id;
        this.user_id = user_id;
        this.nomActivite = nomActivite;
        this.typeActivite = typeActivite;
        this.NomClub = NomClub;
        this.vote = vote;
    }

    public Activite() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNomActivite() {
        return nomActivite;
    }

    public void setNomActivite(String nomActivite) {
        this.nomActivite = nomActivite;
    }

    public String getTypeActivite() {
        return typeActivite;
    }

    public void setTypeActivite(String typeActivite) {
        this.typeActivite = typeActivite;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "Activite{" + "id=" + id + ", user_id=" + user_id + ", nomActivite=" + nomActivite + ", typeActivite=" + typeActivite + ", vote=" + vote + '}';
    }
}
