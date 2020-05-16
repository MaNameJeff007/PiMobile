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
public class Moyenne 
{
   private int id;
   private int trimestre;
   private double moyenne;
   private String eleve_id;
   private String eleve_nom;
   private String matiere;
   private String matiere_nom;

    public Moyenne(int trimestre, double moyenne, String eleve_id, String matiere) 
    {
        this.trimestre = trimestre;
        this.moyenne = moyenne;
        this.eleve_id = eleve_id;
        this.matiere = matiere;
    }
    
    public Moyenne()
    {
    }
    
    public Moyenne(int id, int trimestre, double moyenne, String eleve_id, String matiere, String eleve_nom, String matiere_nom) 
    {
        this.id=id;
        this.trimestre = trimestre;
        this.moyenne = moyenne;
        this.eleve_id = eleve_id;
        this.matiere = matiere;
        this.eleve_nom= eleve_nom;
        this.matiere_nom= matiere_nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public String getEleve_id() {
        return eleve_id;
    }

    public void setEleve_id(String eleve_id) {
        this.eleve_id = eleve_id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getEleve_nom() {
        return eleve_nom;
    }

    public void setEleve_nom(String eleve_nom) {
        this.eleve_nom = eleve_nom;
    }

    public String getMatiere_nom() {
        return matiere_nom;
    }

    public void setMatiere_nom(String matiere_nom) {
        this.matiere_nom = matiere_nom;
    }

    @Override
    public String toString() {
        return "Moyenne'{'trimestre=" + trimestre + ", moyenne=" + moyenne + ", eleve_nom=" + eleve_nom + ", matiere_nom=" + matiere_nom + '}';
    }
    
    
    
    
}
