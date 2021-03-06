/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import static com.codename1.ui.CN.CENTER;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.entities.Salle;
import com.mycompany.myapp.services.ServiceSalle;

/**
 *
 * @author dell
 */
public class ModifierSalle extends Form {

    Form current;

    public boolean checkSalle(Salle t) {
        for (int i = 0; i < ServiceSalle.getInstance().getAllSalles().size(); i++) {
            Salle sc = ServiceSalle.getInstance().getAllSalles().get(i);
            if (t.getLibelle().equals(sc.getLibelle())) {
                return true;
            }
        }
        return false;
    }

    public ModifierSalle(Form previous, Salle t) {

        setTitle("Modifier salle");
        //setLayout(BoxLayout.y());
        setLayout(new FlowLayout(CENTER, CENTER));
        TextField libelle = new TextField();
        Button btnValider = new Button("Valider");

         Label Lc = new Label("Nom salle : ");
         libelle.setText(t.getLibelle());
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((libelle.getText().length() == 0)) {
                    Dialog.show("Alert", "Veuillez saisir tous les champs", new Command("OK"));
                } else {
                    try {
                        t.setLibelle(libelle.getText());
                        System.out.println(t);
                        if(checkSalle(t)== false){
                        if (ServiceSalle.getInstance().updateSalle(t)) {
                            Dialog.show("Success", "Salle modifiée avec succès", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                        }else
                        {
                           Dialog.show("ERROR", "Modifictaion echouée.Cette Salle existe déjà", new Command("OK")); 
                        }

                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Libelle ne doit pas etre vide", new Command("OK"));
                    }

                }

            }
        });

        addAll(Lc,libelle, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new AfficherSalle(current).show());
    }
}
