/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template EmploiClasse, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author dell
 */
public class HomeScolarite extends Form {

    Form current;

    public HomeScolarite() {
        current = this;
        setTitle("Home");
        setLayout(BoxLayout.y());

        add(new Label("Choose an option"));
        //Button btnAddTask = new Button("Add Task");
        Button classe = new Button("Gestion des classes ");
        Button matiere = new Button("Gestion des matières");
        Button btn = new Button("Gestion des séances ");
        Button btn1 = new Button("Statistiques moyennes trimestrielles");
        Button coeff = new Button("Gestion des coefficients");
        Button Stat2 = new Button("Statistiques moyennes générales ");
        Button salle = new Button("Gestion des salles ");
        Button bul = new Button("Bulletins des élèves  ");
         Button file = new Button("mmmmm  ");

        //btnAddTask.addActionListener(e -> new AjouterClasse(current).show());
        classe.addActionListener(e -> new AfficherClasses(current).show());
        btn.addActionListener(e -> new AfficherSeance(current).show());
        salle.addActionListener(e -> new AfficherSalle(current).show());
        coeff.addActionListener(e -> new AfficherCoeff(current).show());
        matiere.addActionListener(e -> new AfficherMatiere(current).show());
        bul.addActionListener(e -> new BulletinsElv(current).show());
 
        btn1.addActionListener(e -> new StatCT(current).show());
        Stat2.addActionListener(e -> new PieChart(current).show());
        addAll(classe, matiere, coeff, btn, salle, bul, btn1, Stat2);
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeForm().show());
    }

}
