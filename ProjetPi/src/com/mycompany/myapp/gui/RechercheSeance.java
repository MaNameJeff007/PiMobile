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
public class RechercheSeance extends Form {

    Form current, list;

    private Container addItem(Seance u) {
        SpanLabel sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
        /*Label lbnom = new Label(u.getClasse().getLibelle());
        Label m = new Label(u.getMatiere().getNom());
        Label e = new Label(u.getEnseignant().getNom());
        Label s = new Label(u.getSalle().getLibelle());
        Label hdeb = new Label(u.getHdeb());
         */

        sp.setText("jour : " + u.getJour() + "  Heure : " + u.getHdeb() + " Classe : " + u.getClasse().getLibelle());

        Button delete = new Button("Supprimer");
        Button update = new Button("Modifier ");
        Button details = new Button("Détails ");

        // cnt1.addAll(hdeb, lbnom, s, e, m);
        Container btns = new Container(BoxLayout.x());
        btns.addAll(delete, update, details);
        cnt1.addAll(sp, btns);
        Container cnt2 = new Container(BoxLayout.x());
        cnt2.add(cnt1);

        delete.addActionListener((e) -> {

            if (ServiceSeance.getInstance().deleteSeance(u)) {

                new AfficherSeance(current).show();

            } else {

                System.out.println("hhhhhhh");
            }

        }
        );

        update.addActionListener((e) -> {
            new ModifierSeance(current, u).show();
        }
        );
        
          details.addActionListener((e) -> {
            new DetailsSeance(current, u).show();
        }
        );

        return cnt2;
    }

    public RechercheSeance(Form previous, String t) {
        setTitle("Liste des séances cours ");
        Button Ajouter = new Button("Ajouter une Séance");
        Form list = new Form("Séances", BoxLayout.y());
        ArrayList<Seance> arr = new ArrayList<>();
        //ArrayList<Seance> arr2 = new ArrayList<>();

        ArrayList<Classe> cl = new ArrayList<>();
        ComboBox ct = new ComboBox();
        for (int i = 0; i < ServiceClasse.getInstance().getAllTasks().size(); i++) {
            cl.add(ServiceClasse.getInstance().getAllTasks().get(i));

        }

        for (Classe us : cl) {
            ct.addItem(us.getLibelle());
        }

        //arr2 = ServiceSeance.getInstance().getAllSeances();
        for (int i = 0; i < ServiceSeance.getInstance().getSeancesClasse1(t).size(); i++) {
            arr.add(ServiceSeance.getInstance().getSeancesClasse1(t).get(i));

        }

        for (Seance us : arr) {
            list.add(addItem(us));
        }

        Ajouter.addActionListener((e) -> {

            new AjouterSeance(current).show();
        }
        );

        ct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new RechercheSeance(current, ct.getSelectedItem().toString()).show();
            }
        });

        //list.show();
        System.out.println(arr);
        addAll(Ajouter, ct, list);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeScolarite().show());
    }
}
