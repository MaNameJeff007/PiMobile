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
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.list.DefaultListModel;
import com.mycompany.myapp.entities.Permutation;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServicePermutation;
import com.mycompany.myapp.services.UserService;
import com.mycompany.myapp.services.ServiceClasse;

/**
 * GUI builder created Form
 *
 * @author Selim Chikh Zaouali
 */
public class AddPermutation extends com.codename1.ui.Form {

    int idConnecte = 9; //Login().getcurrentUser().getIdentifiant()

    public AddPermutation() {
        TextModeLayout tm = new TextModeLayout(4, 2);
        setLayout(new BorderLayout());
        setTitle("Demander une permutation");
        Container cont1 = new Container();

        cont1.add(new Label("Enfant"));
        ComboBox<String> enf = new ComboBox<>();
        // Affiche les enfants du parent connecté
        String idprenomnom = "";
        String identif = "";
        int ident = 0;
        System.out.println(UserService.getInstance().getEnfants(idConnecte).size());
        for (User u : UserService.getInstance().getEnfants(idConnecte)) {
            //for (int i = 0; i < UserService.getInstance().getEnfants(idConnecte).size(); i++) {
            //ident = UserService.getInstance().getEleves(idConnecte).get(i).getIdentifiant();
            ident = u.getIdentifiant();
            System.out.println(ident);
            identif = String.valueOf(ident);
            //idprenomnom = identif + " " + UserService.getInstance().getEnfants(idConnecte).get(i).getPrenom() + " " + UserService.getInstance().getEnfants(idConnecte).get(i).getNom();
            idprenomnom = identif + " " + u.getPrenom() + " " + u.getNom();

            enf.addItem(idprenomnom);
        }
        enf.setSelectedIndex(0);
        cont1.add(enf);

        // Récuperer l'identifiant de l'élève pour remplir la ComboBox des classes
        //int j=enf.getSelectedIndex();
        cont1.add(new Label("Classe souhaitée"));
        ComboBox<String> classe = new ComboBox<>();

        enf.addActionListener(e -> {
            String s = enf.getSelectedItem();
            String str = s.substring(0, s.indexOf(" "));
            int id = Integer.parseInt(str);
            String libelle = "";
            String idclasse = UserService.getInstance().getClasseIDEleve(id).get(0).getClasseeleve_id();
            int niveau = ServiceClasse.getInstance().getNiveauClasseParID(Integer.parseInt(idclasse)).get(0).getNiveau();
            DefaultListModel dlm = (DefaultListModel) classe.getModel();
            dlm.removeAll();
            for (int i = 0; i < ServiceClasse.getInstance().getClasseParNiveau(niveau).size(); i++) {
                libelle = ServiceClasse.getInstance().getClasseParNiveau(niveau).get(i).getLibelle();
                classe.addItem(libelle);
            }
        });

        String s = enf.getSelectedItem();
        String str = s.substring(0, s.indexOf(" "));
        int id = Integer.parseInt(str);

        // Affiche les claases du même niveau
        classe.setSelectedIndex(0);
        cont1.add(classe);

        cont1.add(new Label("Raison"));
        TextComponent raison = new TextComponent().multiline(true).rows(2);
        cont1.add(raison);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new PermutationF().show());
        Button submit = new Button("Envoyer");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
        submit.addActionListener(e -> {
            Permutation p = new Permutation();
            p.setEtat("non traitee");
            //a.setParent(new Login().getcurrentUser().getIdentifiant());
            p.setParent(idConnecte);
            p.setClasse_s(classe.getSelectedItem());
            p.setEnfant(enf.getSelectedItem());
            p.setEleve_id(id);
            p.setRaison(raison.getText());
            if (ServicePermutation.getInstance().addPermutation(p)) {
                LocalNotification n = new LocalNotification();
                n.setId("demo-notification");
                n.setAlertBody("Vous avez demandé une permutation");
                n.setAlertTitle("Succès !");

                Display.getInstance().scheduleLocalNotification(
                        n,
                        System.currentTimeMillis() + 10 * 1000, // fire date/time
                        LocalNotification.REPEAT_MINUTE // Whether to repeat and what frequency
                );
                new PermutationF().show();
            } else {
                Dialog.show("Dialog Title", "mochekla", "OK", null);
            }
        });
        add(BorderLayout.NORTH, cont1);
        add(BorderLayout.SOUTH, submit);
    }

    public AddPermutation(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
    }

//-- DON'T EDIT BELOW THIS LINE!!!
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
    }
//-- DON'T EDIT ABOVE THIS LINE!!!
}
