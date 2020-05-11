/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.services.ServiceSujet;
import com.mycompany.myapp.services.ServiceUser;

/**
 *
 * @author bhk
 */
public class ForumForm extends Form {

    Form current, list;
    /*Garder traçe de la Form en cours pour la passer en paramètres 
     aux interfaces suivantes pour pouvoir y revenir plus tard en utilisant
     la méthode showBack*/

    public ForumForm(Form previous) {
        setLayout(BoxLayout.y());
        setTitle("List sujets");
        System.out.println();
        //setScrollableY(true);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeForm().show());
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> new AddSujetForm().show());
        //System.out.println(new HomeForm().getcurrentUser().getIdentifiant());
        //sp.setText(ServiceSujet.getInstance().getAllSujet().toString());
        for (Sujet s : ServiceSujet.getInstance().getAllSujet()) {
            add(addItem(s));
        }
    }

    private void refresh() {
        removeAll();
        for (Sujet ss : ServiceSujet.getInstance().getAllSujet()) {
            add(addItem(ss));
        }
    }

    private Container addItem(Sujet s) {
        Container cnt1 = new Container(BoxLayout.y());
        Label l1 = new Label(Integer.toString(s.getSujet_id()));
        SpanLabel l2 = new SpanLabel(s.getTitre());
        SpanLabel l4 = new SpanLabel(ServiceUser.getInstance().getUser(s.getCreateur_id()));
        SpanLabel l3 = new SpanLabel(s.getDescription());
        Button btnmail = new Button(Integer.toString(s.getSujet_id()));
        Button btnsupprimer = new Button("delete");
        Button btnmodif = new Button("modif");
        cnt1.add(l1);
        cnt1.add(l2);
        cnt1.add(l4);
        cnt1.add(l3);
        Container cnt3 = new Container(BoxLayout.x());
        cnt3.add(btnmail);
        if (s.getCreateur_id() == new Login().getcurrentUser().getIdentifiant()) {
            cnt3.add(btnsupprimer);
            cnt3.add(btnmodif);
        }
        cnt1.add(cnt3);
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
        Container cnt2 = new Container(BoxLayout.y());
        cnt2.add(cnt1);

        btnmail.addActionListener((e) -> {
            System.out.println(s.getSujet_id());

        });
        btnsupprimer.addActionListener((e) -> {
            if (ServiceSujet.getInstance().deleteSujet(s.getSujet_id())) {
                refresh();
            } else {
                System.out.println(s.getTitre());
            }
        });
        btnmodif.addActionListener((e) -> {
            new ModifierSujetForm(s.getSujet_id()).show();
        });
        return cnt2;
    }
}
