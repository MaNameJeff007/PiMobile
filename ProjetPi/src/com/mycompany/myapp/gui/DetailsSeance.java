/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.entities.Seance;
import com.mycompany.myapp.services.ServiceClasse;
import com.mycompany.myapp.services.ServiceSeance;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class DetailsSeance extends Form {

    Form current, list;

    private Container addItem(Seance u) {
        SpanLabel sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
        // cnt1.getStyle().setBorder(Border.createLineBorder(1));
        Label cl = new Label("Classe : " + u.getClasse().getLibelle());
        Label m = new Label("Matière : " + u.getMatiere().getNom());
        Label e = new Label("Nom Enseigant: " + u.getEnseignant().getNom());
        Label e1 = new Label("Prenom Enseigant: " + u.getEnseignant().getPrenom());
        Label s = new Label("Salle : " + u.getSalle().getLibelle());
        Label hdeb = new Label("Heure début :" + u.getHdeb());
        Label hfin = new Label("Heure fin :" + u.getHfin());
        Label jour = new Label("Jour : " + u.getJour());

        
        cnt1.addAll(jour, hdeb, hfin, cl, m, s, e, e1);
        Container cnt2 = new Container(BoxLayout.x());
        cnt2.add(cnt1);

        return cnt2;
    }

    public DetailsSeance(Form previous, Seance u) {
        setTitle("Seance ");

        add(addItem(u));
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new AfficherSeance(current).show());
    }

}
