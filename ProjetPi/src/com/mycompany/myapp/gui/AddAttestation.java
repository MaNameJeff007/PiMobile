/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextComponent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.myapp.entities.Attestation;
import com.mycompany.myapp.services.ServiceAttestation;
import com.mycompany.myapp.services.UserService;

/**
 * GUI builder created Form
 *
 * @author Selim Chikh Zaouali
 */
public class AddAttestation extends com.codename1.ui.Form {

    int idConnecte = 9; //Login().getcurrentUser().getIdentifiant()

    public AddAttestation() {
        TextModeLayout tm = new TextModeLayout(4, 2);
        setLayout(new BorderLayout());
        setTitle("Demander une attestation");
        Container cont1 = new Container();
        //TextComponent enf = new TextComponent().label("Enfant").multiline(true).rows(2);
        ComboBox<String> enf = new ComboBox<>();
        String prenomnom = "";
        for (int i = 0; i < UserService.getInstance().getEnfants(idConnecte).size(); i++) {
            prenomnom = UserService.getInstance().getEnfants(idConnecte).get(i).getPrenom() + " " + UserService.getInstance().getEnfants(idConnecte).get(i).getNom();
            enf.addItem(prenomnom);
        }

        enf.setSelectedIndex(0);

        cont1.add(enf);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new AttestationF().show());
        Button submit = new Button("Envoyer");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
        submit.addActionListener(e -> {
            Attestation a = new Attestation();
            a.setEtat("non traitee");
            //a.setParent(new Login().getcurrentUser().getIdentifiant());
            a.setParent(idConnecte);
            a.setEnfant(enf.getSelectedItem());
            LocalNotification n = new LocalNotification();
            System.out.println("1");
                n.setId("demo-notification");
                System.out.println("12");
                n.setAlertBody("Vous avez demandé une attestation");
                System.out.println("123");
                n.setAlertTitle("Succès !");
                System.out.println("1234");

                Display.getInstance().scheduleLocalNotification(
                        n,
                        System.currentTimeMillis() + 10 * 1000, // fire date/time
                        LocalNotification.REPEAT_MINUTE // Whether to repeat and what frequency
                );
            if (ServiceAttestation.getInstance().addAttestation(a)) {
                
                new AttestationF().show();
            } else {
                Dialog.show("Dialog Title", "mochekla", "OK", null);
            }
        });
        add(BorderLayout.NORTH, cont1);
        add(BorderLayout.SOUTH, submit);

    }
}
