/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;


public class User {

    private int identifiant;
    private String username;
    private String email;
    private int enabled;
    private String password;
    private String last_login;
    private String roles;
    private String nom;
    private String prenom;
    private String Date_Embauche;
    private String Date_Inscription;
    private String parent_id;
    private String classeeleve_id;
    private String classeenseignant_id;
    private float MoyG;
    private static int workload = 5;
    private int code;

    public User() {

    }

    public User(int identifiant, String prenom, String nom, String id_classe) {
        this.identifiant = identifiant;
        this.prenom = prenom;
        this.nom = nom;
        this.classeeleve_id = id_classe;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, int enabled, String password, String last_login, String roles, String nom, String prenom, String Date_Embauche, String Date_Inscription, String parent_id, String classeeleve_id, String classeenseignant_id) {
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.password = password;
        this.last_login = last_login;
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
        this.Date_Embauche = Date_Embauche;
        this.Date_Inscription = Date_Inscription;
        this.parent_id = parent_id;
        this.classeeleve_id = classeeleve_id;
        this.classeenseignant_id = classeenseignant_id;
    }

    public User(int id, String nom, String prenom, String classeeleve_id, String classeenseignant_id, int parent, String username, String password, String email, String dateInscription, String dateEmbauche, String roles) {
        this.identifiant = id;
        this.nom = nom;
        this.prenom = prenom;
        this.classeeleve_id = classeeleve_id;
        this.classeenseignant_id = classeenseignant_id;
        this.parent_id = Integer.toString(parent);
        this.username = username;
        this.password = password;
        this.email = email;
        this.Date_Inscription = dateInscription;
        this.Date_Embauche = dateEmbauche;
        this.roles = roles;
    }

    public User(User U) {
        this.identifiant = U.identifiant;
        this.username = null;
        this.email = null;
        this.enabled = 0;
        this.password = null;
        this.last_login = null;
        this.roles = null;
        this.nom = null;
        this.prenom = null;
        this.Date_Embauche = null;
        this.Date_Inscription = null;
        this.parent_id = null;
        this.classeeleve_id = null;
        this.classeenseignant_id = null;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public float getMoyG() {
        return MoyG;
    }

    public void setMoyG(float MoyG) {
        this.MoyG = MoyG;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
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

    public String getDate_Embauche() {
        return Date_Embauche;
    }

    public void setDate_Embauche(String Date_Embauche) {
        this.Date_Embauche = Date_Embauche;
    }

    public String getDate_Inscription() {
        return Date_Inscription;
    }

    public void setDate_Inscription(String Date_Inscription) {
        this.Date_Inscription = Date_Inscription;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getClasseeleve_id() {
        return classeeleve_id;
    }

    public void setClasseeleve_id(String classeeleve_id) {
        this.classeeleve_id = classeeleve_id;
    }

    public String getClasseenseignant_id() {
        return classeenseignant_id;
    }

    public void setClasseenseignant_id(String classeenseignant_id) {
        this.classeenseignant_id = classeenseignant_id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
