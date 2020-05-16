/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;


/**
 *
 * @author rami2
 */

public class Sanction 
{
    private int id;
    private String raisonSanction;
    private String dateSanction;
    private String punition;
    private int etat;
    private String enseignant_id;
    private String eleve_id;
    private String eleve_nom;
    
    
    public Sanction()
    {
        
    }
    
    public Sanction(Sanction S)
    {
       this.id=S.id;
       this.raisonSanction = S.raisonSanction;
       this.dateSanction = S.dateSanction;
       this.punition = S.punition;
       this.etat = S.etat;
       this.enseignant_id = S.enseignant_id;
       this.eleve_id = S.eleve_id; 
    }
    
    public Sanction(int id, String raisonSanction, String dateSanction, String punition, int etat, String enseignant_id, String eleve_id, String eleve_nom) 
    {
        this.id=id;
        this.raisonSanction = raisonSanction;
        this.dateSanction = dateSanction;
        this.punition = punition;
        this.etat = etat;
        this.enseignant_id = enseignant_id;
        this.eleve_id = eleve_id;
        this.eleve_nom= eleve_nom;
    }
    
    public Sanction(String raisonSanction, String dateSanction, String punition, int etat, String enseignant_id, String eleve_id, String eleve_nom) 
    {
        this.raisonSanction = raisonSanction;
        this.dateSanction = dateSanction;
        this.punition = punition;
        this.etat = etat;
        this.enseignant_id = enseignant_id;
        this.eleve_id = eleve_id;
        this.eleve_nom=eleve_nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRaisonSanction() {
        return raisonSanction;
    }

    public void setRaisonSanction(String raisonSanction) {
        this.raisonSanction = raisonSanction;
    }

    public String getDateSanction() {
        return dateSanction;
    }

    public void setDateSanction(String dateSanction) {
        this.dateSanction = dateSanction;
    }

    public String getPunition() {
        return punition;
    }

    public void setPunition(String punition) {
        this.punition = punition;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getEnseignant_id() {
        return enseignant_id;
    }

    public void setEnseignant_id(String enseignant_id) {
        this.enseignant_id = enseignant_id;
    }

    public String getEleve_id() {
        return eleve_id;
    }

    public void setEleve_id(String eleve_id) {
        this.eleve_id = eleve_id;
    }

    public String getElevenom() {
        return eleve_nom;
    }

    public void setElevenom(String eleve_nom) {
        this.eleve_nom = eleve_nom;
    }
    
    
}
