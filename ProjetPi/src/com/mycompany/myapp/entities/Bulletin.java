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
public class Bulletin {
    private int id;
    private int eleve;
    private int trimestre;
    private int classe;
    private float moyenne;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEleve() {
        return eleve;
    }

    public void setEleve(int eleve) {
        this.eleve = eleve;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(float moyenne) {
        this.moyenne = moyenne;
    }

    @Override
    public String toString() {
        return "Bulletin{" + "id=" + id + ", eleve=" + eleve + ", trimestre=" + trimestre + ", classe=" + classe + ", moyenne=" + moyenne + '}';
    }

    public Bulletin() {
    }

    public Bulletin(int id, int eleve, int trimestre, int classe, float moyenne) {
        this.id = id;
        this.eleve = eleve;
        this.trimestre = trimestre;
        this.classe = classe;
        this.moyenne = moyenne;
    }

    public Bulletin(int eleve, int trimestre, int classe, float moyenne) {
        this.eleve = eleve;
        this.trimestre = trimestre;
        this.classe = classe;
        this.moyenne = moyenne;
    }
  
    
}
