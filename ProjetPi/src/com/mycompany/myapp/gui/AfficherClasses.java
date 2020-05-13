/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.services.ServiceClasse;


/**
 *
 * @author dell
 */
public class AfficherClasses extends Form {

    Form current, list;
     private Resources theme;

    private Container addItem(Classe u) {
        Container cnt1 = new Container(BoxLayout.y());

        Button delete = new Button("supprimer");
        Button update = new Button("modifier");
        Button emp = new Button("Emploi de temps");
        SpanLabel sp = new SpanLabel();
        sp.setText("Libelle: " + u.getLibelle() + " Niveau: " + Integer.toString(u.getNiveau()) + " Capacité: " + Integer.toString(u.getCapacite()));
        Container btns = new Container(BoxLayout.x());
        btns.addAll(delete, update, emp);
        cnt1.addAll(sp, btns);
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
        Container cnt2 = new Container(BoxLayout.x());
        //Label lbimg = new Label();

        cnt2.add(cnt1);
        delete.addActionListener((e) -> {

            if (ServiceClasse.getInstance().deleteClasse(u)) {

                new AfficherClasses(current).show();

            } else {

                System.out.println("hhhhhhh");
            }

        }
        );

        update.addActionListener((e) -> {
            new ModifierClasse(current, u).show();
        }
        );

        emp.addActionListener((e) -> {
            new EmploiClasse(current, u).show();
        }
        );

        return cnt2;
    }

    public AfficherClasses(Form previous) {
         
        current = this;
        setLayout(BoxLayout.y());
        Button Ajouter = new Button("Ajouter une Classe ");
        Form list = new Form("Classes", BoxLayout.y());
        setTitle("Liste des classes ");

        for (Classe s : ServiceClasse.getInstance().getAllTasks()) {
            list.add(addItem(s));
        }

        Ajouter.addActionListener((e) -> {

            new AjouterClasse(current).show();
        }
        );

        addAll(Ajouter, list);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeScolarite().show());
    }

}
