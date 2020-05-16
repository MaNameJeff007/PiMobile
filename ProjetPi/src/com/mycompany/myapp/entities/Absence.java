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
public class Absence 
{
    private int id;
    private String justification;
    private String dateAbsence;
    private int heureDebut;
    private int heureFin;
    private int etat;
    private String enseignant_id;
    private String eleve_id;
    private String eleve_nom;
    
    public Absence()
    {
        
    }
    
    public Absence(Absence A)
    {
      this.id=A.id;
      this.justification = A.justification;
      this.dateAbsence=A.dateAbsence;
      this.heureDebut = A.heureDebut;
      this.heureFin = A.heureFin;
      this.etat = A.etat;
      this.enseignant_id = A.enseignant_id;
      this.eleve_id = A.eleve_id;  
    }
    
     public Absence(int id, String justification, String dateAbsence, int heureDebut, int heureFin, int etat, String enseignant_id, String eleve_id, String eleve_nom) 
     {
        this.id=id;
        this.justification = justification;
        this.dateAbsence=dateAbsence;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.etat = etat;
        this.enseignant_id = enseignant_id;
        this.eleve_id = eleve_id;
        this.eleve_nom= eleve_nom;
    }

    public Absence(String justification, String dateAbsence, int heureDebut, int heureFin, int etat, String enseignant_id, String eleve_id) 
    {
        this.justification = justification;
        this.dateAbsence=dateAbsence;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.etat = etat;
        this.enseignant_id = enseignant_id;
        this.eleve_id = eleve_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
    
    public String getDateAbsence() {
        return dateAbsence;
    }

    public void setDateAbsence(String dateAbsence) {
        this.dateAbsence = dateAbsence;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(int heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(int heureFin) {
        this.heureFin = heureFin;
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

    public String getEleve_nom() {
        return eleve_nom;
    }

    public void setEleve_nom(String eleve_nom) {
        this.eleve_nom = eleve_nom;
    }
    
    
}
