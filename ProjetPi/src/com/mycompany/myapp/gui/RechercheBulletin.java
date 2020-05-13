/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceClasse;
import com.mycompany.myapp.services.ServiceUser;
import com.mycompany.myapp.utils.Statics;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class RechercheBulletin extends Form {

    Form current;

    private Container addItem(User u) {
        SpanLabel sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
        Classe cl = new Classe();

        ArrayList<Classe> arr = new ArrayList<>();
        for (int i = 0; i < ServiceClasse.getInstance().getSelectedClasse(u).size(); i++) {
            arr.add(ServiceClasse.getInstance().getSelectedClasse(u).get(i));

        }

        for (Classe us : arr) {
            cl.setNiveau(us.getNiveau());
            cl.setLibelle(us.getLibelle());
        }
        sp.setText("Nom : " + u.getNom() + "  Prénom : " + u.getPrenom() + " Classe:" + cl.getLibelle());

        Button b1 = new Button("Bulletin S1");
        Button b2 = new Button("Bulletin S2");
        Button b3 = new Button("Bulletin S3");
        Container btns = new Container(BoxLayout.x());
        btns.addAll(b1, b2, b3);
        cnt1.addAll(sp, btns);
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
        Container cnt2 = new Container(BoxLayout.x());

        b1.addActionListener(e -> {
            FileSystemStorage fs = FileSystemStorage.getInstance();
            String fileName = fs.getAppHomePath() + "Bulletin.pdf";

            Util.downloadUrlToFile("" + Statics.BASE_URL + "moyenne1/" + u.getIdentifiant() + "/" + 1 + "/" + cl.getNiveau() + "", fileName, true);

            Display.getInstance().execute(fileName);
        });

        b2.addActionListener(e -> {
            FileSystemStorage fs = FileSystemStorage.getInstance();
            String fileName = fs.getAppHomePath() + "Bulletin.pdf";

            Util.downloadUrlToFile("" + Statics.BASE_URL + "moyenne1/" + u.getIdentifiant() + "/" + 2 + "/" + cl.getNiveau() + "", fileName, true);

            Display.getInstance().execute(fileName);
        });

        b3.addActionListener(e -> {
            FileSystemStorage fs = FileSystemStorage.getInstance();
            String fileName = fs.getAppHomePath() + "Bulletin.pdf";

            Util.downloadUrlToFile("" + Statics.BASE_URL + "moyenne1/" + u.getIdentifiant() + "/" + 3 + "/" + cl.getNiveau() + "", fileName, true);

            Display.getInstance().execute(fileName);
        });

        cnt2.add(cnt1);

        return cnt2;
    }

    public RechercheBulletin(Form previous, String t) {
        current = this;
        ArrayList<Classe> arr = new ArrayList<>();
        ComboBox ct = new ComboBox();
        for (int i = 0; i < ServiceClasse.getInstance().getAllTasks().size(); i++) {
            arr.add(ServiceClasse.getInstance().getAllTasks().get(i));

        }

        for (Classe us : arr) {
            ct.addItem(us.getLibelle());
        }

        setLayout(BoxLayout.y());
        Form list = new Form(" Elèves et bulletins", BoxLayout.y());

       
        for (User s : ServiceUser.getInstance().getElevesClasse(t)) {
            list.add(addItem(s));
        }

        ct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                /* list.removeAll();
            for (User s : ServiceUser.getInstance().getAllEleves()) {
            list.add(addItem(s));
        }*/
                new RechercheBulletin(current, ct.getSelectedItem().toString()).show();
            }
        });

        addAll(ct, list);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeScolarite().show());
    }

}
