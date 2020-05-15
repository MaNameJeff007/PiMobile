/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.entities.Matiere;
import com.mycompany.myapp.entities.Salle;
import com.mycompany.myapp.entities.Seance;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceClasse;
import com.mycompany.myapp.services.ServiceMatiere;
import com.mycompany.myapp.services.ServiceSalle;
import com.mycompany.myapp.services.ServiceSeance;
import com.mycompany.myapp.services.ServiceUser;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class ModifierSeance extends Form {

    Form current;

    public boolean checkSeances(Seance t) {
        for (int i = 0; i < ServiceSeance.getInstance().getAllSeances().size(); i++) {
            Seance sc = ServiceSeance.getInstance().getAllSeances().get(i);
            if ((t.getSalle().getLibelle().equals(sc.getSalle().getLibelle())) && (t.getHdeb().equals(sc.getHdeb()))
                    && (t.getJour().equals(sc.getJour()))  ) {
                return true;
            }

            if ((t.getEnseignant().getNom().equals(sc.getEnseignant().getNom())) && (t.getHdeb().equals(sc.getHdeb()))
                    && (t.getJour().equals(sc.getJour()))) {
                return true;

            }

        }
        return false;
    }

    public ModifierSeance(Form previous, Seance t) {
        setTitle("Modifier séance ");
        setLayout(BoxLayout.y());
        ComboBox ct = new ComboBox();
        ComboBox matieres = new ComboBox();
        ComboBox enseignants = new ComboBox();
        ComboBox salles = new ComboBox();
        ComboBox jours = new ComboBox();
        ComboBox hdeb = new ComboBox();

        ct.setSelectCommandText(t.getClasse().getLibelle());

        Button ajouter = new Button("Valider");
        //TextField valeur = new TextField();

        jours.addItem("lundi");
        jours.addItem("mardi");
        jours.addItem("mercredi");
        jours.addItem("jeudi");
        jours.addItem("vendredi");
        jours.addItem("samedi");

        hdeb.addItem("08:00:00");
        hdeb.addItem("10:15:00");
        hdeb.addItem("12:00:00");
        hdeb.addItem("13:00:00");
        hdeb.addItem("15:15:00");
        ArrayList<Matiere> m1 = new ArrayList<>();
        ArrayList<Matiere> mlist = new ArrayList<>();

        ArrayList<Classe> arr = new ArrayList<>();
        ArrayList<Classe> arr2 = new ArrayList<>();

        ArrayList<User> listUser = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        ArrayList<Salle> listsalles = new ArrayList<>();
        ArrayList<Salle> sallesc = new ArrayList<>();

        arr2 = ServiceClasse.getInstance().getAllTasks();
        m1 = ServiceMatiere.getInstance().getAllMatieres();

        listUser = ServiceUser.getInstance().getAllEnseigants();
        listsalles = ServiceSalle.getInstance().getAllSalles();

        for (int i = 0; i < ServiceClasse.getInstance().getAllTasks().size(); i++) {
            arr.add(arr2.get(i));

        }
        for (Classe us : arr) {
            ct.addItem(us.getLibelle());
        }

        for (int i = 0; i < ServiceMatiere.getInstance().getAllMatieres().size(); i++) {
            mlist.add(m1.get(i));

        }
        for (Matiere mat : mlist) {
            matieres.addItem(mat.getNom());

        }

        for (int i = 0; i < ServiceSalle.getInstance().getAllSalles().size(); i++) {
            sallesc.add(listsalles.get(i));

        }
        for (Salle mat : sallesc) {
            salles.addItem(mat.getLibelle());

        }

        for (int i = 0; i < ServiceUser.getInstance().getAllEnseigants().size(); i++) {
            users.add(listUser.get(i));

        }
        for (User mat : users) {
            enseignants.addItem(mat.getNom());

        }

        Label Lc = new Label("Classe : ");
        Label Lm = new Label("Matière : ");
        Label Lv = new Label("Heure Début : ");
        Label Le = new Label("Enseignant : ");
        Label Ls = new Label("Salle : ");
        Label Lj = new Label("Jour : ");
        

        addAll(Lj, jours, Lv, hdeb, Lc, ct,Lm, matieres, Le, enseignants, Ls, salles, ajouter);

        ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                /* if ((valeur.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else { */
                try {

                    for (Matiere mat : mlist) {
                        if (mat.getNom().equals(matieres.getSelectedItem())) {

                            System.out.println(mat);
                            t.setMatiere(mat);
                        }
                    }

                    for (Classe us : arr) {
                        if (us.getLibelle().equals(ct.getSelectedItem())) {

                            System.out.println(us);
                            t.setClasse(us);
                        }
                    }

                    for (Salle us : sallesc) {

                        if (us.getLibelle().equals(salles.getSelectedItem())) {

                            System.out.println(us);
                            t.setSalle(us);
                        }

                    }
                    for (User us : users) {
                        if (us.getNom().equals(enseignants.getSelectedItem())) {

                            System.out.println(us);
                            t.setEnseignant(us);
                        }

                    }
                    t.setHdeb(hdeb.getSelectedItem().toString());

                    if (hdeb.getSelectedItem().equals("08:00:00")) {
                        t.setHfin("10:00:00");
                    } else if (hdeb.getSelectedItem().equals("10:15:00")) {
                        t.setHfin("12:00:00");
                    } else if (hdeb.getSelectedItem().equals("12:00:00")) {
                        t.setHfin("13:00:00");
                    } else if (hdeb.getSelectedItem().equals("13:00:00")) {
                        t.setHfin("15:00:00");
                    } else {
                        t.setHfin("17:00:00");
                    }

                    // t.setHfin(hdeb.getSelectedItem().toString());
                    t.setJour((String) jours.getSelectedItem());
                    if (checkSeances(t) == false) {
                        if (ServiceSeance.getInstance().updateSeance(t)) {
                            Dialog.show("Success", "Séance modifiée avec succès", new Command("OK"));
                        } //System.out.println(t);
                        else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } else {
                        Dialog.show("ERROR", "Modification échouée.Veuillez vérifier la disponibilté de la salle et de l'enseignant", new Command("OK"));
                    }

                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                }

            }
        });
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new AfficherSeance(current).show());
    }
}
