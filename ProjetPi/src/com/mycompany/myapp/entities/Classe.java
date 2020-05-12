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
public class Classe {
    private String id;
 private String libelle;
    private int capacite;
    private int niveau ;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Classe() {
    }

    public Classe(String libelle) {
        this.libelle = libelle;
    }
   

   
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "Classe{" +"libelle = "+this.libelle+"id="+this.id +"capacite="+this.capacite+"niveau = "+this.niveau+'}';
    }

    public Classe(String id, String libelle, int capacite, int niveau) {
        this.id = id;
        this.libelle = libelle;
        this.capacite = capacite;
        this.niveau = niveau;
    }

    public Classe(int niveau,String libelle, int capacite) {
          this.niveau = niveau;
        this.libelle = libelle;
        this.capacite = capacite;
      
    }
    
    
    
}
