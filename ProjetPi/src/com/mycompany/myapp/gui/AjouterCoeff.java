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
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.entities.Coeff;
import com.mycompany.myapp.entities.Matiere;
import com.mycompany.myapp.services.ServiceClasse;
import com.mycompany.myapp.services.ServiceCoeff;
import com.mycompany.myapp.services.ServiceMatiere;

import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class AjouterCoeff extends Form {

    Form current;

    public boolean checkC(Coeff t) {
        for (int i = 0; i < ServiceCoeff.getInstance().getAllCoeff().size(); i++) {
            Coeff sc = ServiceCoeff.getInstance().getAllCoeff().get(i);
            if ((t.getNiveau().getNiveau() == sc.getNiveau().getNiveau()) && (t.getMatiere().getNom().equals(sc.getMatiere().getNom()))) {
                return true;
            }

        }
        return false;
    }

    public AjouterCoeff(Form previous) {
        setTitle("Ajouter un nouveau coefficient ");
        setLayout(BoxLayout.y());
        ComboBox ct = new ComboBox();
        ComboBox matieres = new ComboBox();
        Button ajouter = new Button("Ajouter");
        TextField valeur = new TextField("", "Valeur");
        Label Lc = new Label("Classe : ");
        Label Lm = new Label("Matière : ");
        Label Lv = new Label("Valeur du coefficicent : ");
        ArrayList<Matiere> m1 = new ArrayList<>();
        ArrayList<Matiere> mlist = new ArrayList<>();

        ArrayList<Classe> arr = new ArrayList<>();
        ArrayList<Classe> arr2 = new ArrayList<>();

        arr2 = ServiceClasse.getInstance().getAllTasks();
        m1 = ServiceMatiere.getInstance().getAllMatieres();

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

        addAll(Lc, ct, Lm, matieres, Lv, valeur, ajouter);

        ct.addActionListener((e) -> {
            System.out.println(ct.getSelectedItem());
        });

        ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((valeur.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Matiere MatiereCoeff;
                        Classe ClasseCoeff;
                        Coeff t = new Coeff();
                        for (Matiere mat : mlist) {
                            if (mat.getNom().equals(matieres.getSelectedItem())) {
                                MatiereCoeff = mat;
                                System.out.println(MatiereCoeff);
                                t.setMatiere(MatiereCoeff);
                            }
                        }

                        for (Classe us : arr) {
                            if (us.getLibelle().equals(ct.getSelectedItem())) {
                                ClasseCoeff = us;
                                System.out.println(ClasseCoeff);
                                t.setNiveau(ClasseCoeff);
                            }
                        }
                        // Classe t = new Classe(Integer.parseInt(niveau.getText()), libelle.getText(), Integer.parseInt(capacite.getText()));
                        t.setValeur(Integer.parseInt(valeur.getText()));
                        if (checkC(t) == false) {
                            if (ServiceCoeff.getInstance().addCoeff(t)) {
                                Dialog.show("Success", "Coefficient ajouté avec succès", new Command("OK"));
                            } //System.out.println(t);
                            else {
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                            }
                        } else {
                            Dialog.show("ERROR", "Un coefficient a été attribué a cette matière pour ce niveau d'étude", new Command("OK"));
                        }

                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }

            }

        });

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new AfficherCoeff(current).show());
    }
}
