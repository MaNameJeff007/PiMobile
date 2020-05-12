/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;



/**
 *
 * @author dell
 */
public class Seance implements Externalizable  {

    private int id;
    private String jour;
    private String hdeb;
    private String hfin;
    private User enseignant;
    private Classe classe;
    private Salle salle;
    private Matiere matiere;

    public User getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(User enseignant) {
        this.enseignant = enseignant;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    

   

   

  

    public Seance() {
    }

    public Seance(int id, String jour, String hdeb, String hfin, User enseignant, Classe classe, Salle salle, Matiere matiere) {
        this.id = id;
        this.jour = jour;
        this.hdeb = hdeb;
        this.hfin = hfin;
        this.enseignant = enseignant;
        this.classe = classe;
        this.salle = salle;
        this.matiere = matiere;
    }

    public Seance(String jour, String hdeb, String hfin, User enseignant, Classe classe, Salle salle, Matiere matiere) {
        this.jour = jour;
        this.hdeb = hdeb;
        this.hfin = hfin;
        this.enseignant = enseignant;
        this.classe = classe;
        this.salle = salle;
        this.matiere = matiere;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHdeb() {
        return hdeb;
    }

    public void setHdeb(String hdeb) {
        this.hdeb = hdeb;
    }

    public String getHfin() {
        return hfin;
    }

    public void setHfin(String hfin) {
        this.hfin = hfin;
    }

   

    @Override
    public String toString() {
        return "Seance{" + "id=" + id + ", jour=" + jour + ", hdeb=" + hdeb + ", hfin=" + hfin + ", enseignant=" + enseignant + ", classe=" + classe + ", salle=" + salle + ", matiere=" + matiere + '}';
    }

    @Override
    public int getVersion() {
   return(1);
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
         Util.writeUTF("" + hdeb, out);
         Util.writeUTF("" + hfin, out);
    //Util.writeObject(classe, out);
    }
    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        
        this.hdeb = Util.readUTF(in);
        this.hfin = Util.readUTF(in);
       //this.classe = (Classe) Util.readObject(in);
    //this.hfin = Integer.parseInt(Util.readUTF(in));
    }

    @Override
    public String getObjectId() {
        return("Seance");
    }

   
    
}
