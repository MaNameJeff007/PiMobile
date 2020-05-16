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

public class Note 
{
    private int id;
    private String  type;
    private int trimestre;
    private String enseignant_id;
    private String eleve_id;
    private String matiere_id;
    private String matiere_nom;
    private String eleve_nom;
    private double valeur;
    
    public Note()
    {
        
    }
    
    public Note(Note N)
    {
      this.id=N.id;
      this.type = N.type;
      this.trimestre = N.trimestre;
      this.enseignant_id = N.enseignant_id;
      this.eleve_id = N.eleve_id;
      this.eleve_nom=N.eleve_nom;
      this.matiere_id = N.matiere_id;
      this.matiere_nom= N.matiere_nom;
      this.valeur = N.valeur;  
    }

    public Note(String type, int trimestre, String enseignant_id, String eleve_id, String eleve_nom, String matiere_id, String matiere_nom, double valeur) 
    {
        this.type = type;
        this.trimestre = trimestre;
        this.enseignant_id = enseignant_id;
        this.eleve_id = eleve_id;
        this.eleve_nom=eleve_nom;
        this.matiere_id = matiere_id;
        this.matiere_nom= matiere_nom;
        this.valeur = valeur;
    }
    
    public Note(int id, String type, int trimestre, String enseignant_id, String eleve_id, String eleve_nom, String matiere_id, String matiere_nom, double valeur) 
    {
        this.id=id;
        this.type = type;
        this.trimestre = trimestre;
        this.enseignant_id = enseignant_id;
        this.eleve_id = eleve_id;
        this.eleve_nom=eleve_nom;
        this.matiere_id = matiere_id;
        this.matiere_nom= matiere_nom;
        this.valeur = valeur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
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

    public String getMatiereid() {
        return matiere_id;
    }

    public void setMatiereid(String matiereid) {
        this.matiere_id = matiereid;
    }

    public String getMatierenom() {
        return matiere_nom;
    }

    public void setMatierenom(String matiere_nom) {
        this.matiere_nom = matiere_nom;
    }

    public String getElevenom() {
        return eleve_nom;
    }

    public void setElevenom(String eleve_nom) {
        this.eleve_nom = eleve_nom;
    }
    
    

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return "Note{" + "id=" + id + ", type=" + type + ", trimestre=" + trimestre + ", enseignant_id=" + enseignant_id + ", eleve_id=" + eleve_id + ", matiere_id=" + matiere_id + ", matiere_nom=" + matiere_nom + ", eleve_nom=" + eleve_nom + ", valeur=" + valeur + '}';
    }

}
