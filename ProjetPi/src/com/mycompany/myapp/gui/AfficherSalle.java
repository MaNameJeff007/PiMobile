/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.Salle;
import com.mycompany.myapp.services.ServiceSalle;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class AfficherSalle extends Form {

    Form current, list;

    private Container addItem(Salle u) {
        Container cnt1 = new Container(BoxLayout.y());
        Label lbnom = new Label(u.getLibelle());

        Button delete = new Button("supprimer");
        Button update = new Button("modifier");
        Container btns = new Container(BoxLayout.x());
        btns.addAll(delete, update);
        cnt1.addAll(lbnom, btns);
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
        Container cnt2 = new Container(BoxLayout.x());
        //Label lbimg = new Label();

        cnt2.add(cnt1);
        delete.addActionListener((e) -> {

            if (ServiceSalle.getInstance().deleteSalle(u)) {

                new AfficherSalle(current).show();

            } else {
                //Dialog.show("ERROR", "Server error", new Command("OK"));
                System.out.println("hhhhhhh");
            }

        }
        );

        update.addActionListener((e) -> {
            new ModifierSalle(current, u).show();
        }
        );

        return cnt2;
    }

    public AfficherSalle(Form previous) {
        current = this;
        setLayout(BoxLayout.y());
        setTitle("List Salles");
        Button Ajouter = new Button("Ajouter une Salle ");
        Form list = new Form("Salles", BoxLayout.y());
        for (Salle s : ServiceSalle.getInstance().getAllSalles()) {
            list.add(addItem(s));
        }

        Ajouter.addActionListener((e) -> {

            new AjouterSalle(current).show();
        }
        );

        addAll(Ajouter, list);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeScolarite().show());
    }

}
