/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author dell
 */
public class Coeff {
   private int id;
   private int valeur;
   private Matiere matiere;
    private Classe niveau;

    public Coeff(int id, int valeur, Matiere matiere, Classe niveau) {
        this.id = id;
        this.valeur = valeur;
        this.matiere = matiere;
        this.niveau = niveau;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Classe getNiveau() {
        return niveau;
    }

    public void setNiveau(Classe niveau) {
        this.niveau = niveau;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    
    
   

    

    public Coeff() {
    }

   
   
   
}
