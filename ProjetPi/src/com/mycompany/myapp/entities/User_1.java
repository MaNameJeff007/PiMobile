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
public class User_1 {

    private int id;
    private String nom;
    private String prenom;
    private String libC;// libelle de la classe
    private int classe;
    private int parent;
    private String username;
    private String password;
    int enabled = 1;
    private String email;

    private String dateInscription;

    private String dateEmbauche;

    public User_1(int id, String nom, String prenom, String email, String roles) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.roles = roles;
    }

    
    
    
    
    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(String dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    private String roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getLibC() {
        return libC;
    }

    public void setLibC(String libC) {
        this.libC = libC;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", libC=" + libC + ", classe=" + classe + ", parent=" + parent + ", username=" + username + ", password=" + password + ", enabled=" + enabled + ", email=" + email + ", dateInscription=" + dateInscription + ", dateEmbauche=" + dateEmbauche + ", roles=" + roles + '}';
    }

    public User_1(int id, String nom, String prenom, String libC, int classe, int parent, String username, String password, String email, String dateInscription, String dateEmbauche, String roles) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.libC = libC;
        this.classe = classe;
        this.parent = parent;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateInscription = dateInscription;
        this.dateEmbauche = dateEmbauche;
        this.roles = roles;
    }

    public User_1(int id, String nom, String prenom, String libC) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.libC = libC;
    }

    public User_1(int id, String nom, String prenom, int classe, int parent, String username, String password, String email, String roles) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
        this.parent = parent;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public User_1(String nom, String prenom, int classe, int parent, String username, String password, String email, String roles) {
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
        this.parent = parent;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public User_1() {
    }

}
