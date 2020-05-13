/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.services.ServiceClasse;

/**
 *
 * @author dell
 */
public class ModifierClasse extends Form {

    Form current;

    public boolean checkC(Classe t) {
        for (int i = 0; i < ServiceClasse.getInstance().getAllTasks().size(); i++) {
            Classe sc = ServiceClasse.getInstance().getAllTasks().get(i);
            if (t.getLibelle().equals(sc.getLibelle())) {
                return true;
            }
        }
        return false;
    }

    public ModifierClasse(Form previous, Classe t) {
        setTitle("Modifier classe ");
        setLayout(BoxLayout.y());

        TextField libelle = new TextField();

        TextField capacite = new TextField();
        TextField niveau = new TextField();
        Button btnValider = new Button("Valider");

        Label Lc = new Label("Libelle : ");
        Label Lm = new Label("capacite : ");
        Label Lv = new Label("Niveau : ");

        libelle.setText(t.getLibelle());
        capacite.setText(Integer.toString(t.getCapacite()));
        niveau.setText(Integer.toString(t.getNiveau()));

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((libelle.getText().length() == 0) || (capacite.getText().length() == 0) || (niveau.getText().length() == 0)) {
                    Dialog.show("Alert", "Veuillez saisir tous les champs ", new Command("OK"));
                } else {
                    try {
                        t.setLibelle(libelle.getText());
                        t.setCapacite(Integer.parseInt(capacite.getText()));
                        t.setNiveau(Integer.parseInt(niveau.getText()));
                        if (checkC(t) == false) {
                            if (ServiceClasse.getInstance().updateClasse(t)) {
                                Dialog.show("Success", "Classe modifiée avec succès", new Command("OK"));
                            } //System.out.println(t);
                            else {
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                            }

                        } else {
                            Dialog.show("ERROR", "Modification échoué. cette Classe existe déja ", new Command("OK"));
                        }

                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }

            }
        });

        addAll(Lc, libelle, Lm, capacite, Lv, niveau, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new AfficherClasses(current).show());

    }

}
