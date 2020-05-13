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
public class ListePremiers extends Form {

    Form current;

    private Container addItem(User u) {
        SpanLabel sp = new SpanLabel();
        SpanLabel sp1 = new SpanLabel();
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
        sp.setText("Nom : " + u.getNom() + "  Prénom : " + u.getPrenom());
        double moy = (double) Math.round(u.getMoyG() * 100) / 100;
        sp1.setText("Moyenne générale : " + moy);

        cnt1.addAll(sp, sp1);
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
        Container cnt2 = new Container(BoxLayout.x());

        cnt2.add(cnt1);

        return cnt2;
    }

    public ListePremiers(Form previous, String t) {
        current = this;

        setLayout(BoxLayout.y());
        Form list = new Form("Les 3 premiers de la classe " + t + "", BoxLayout.y());

        // setTitle("Liste des matières ");
        for (User s : ServiceUser.getInstance().GetMaxMoyC(t)) {
            list.add(addItem(s));
        }

        addAll(list);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new PieChart(current).show());
    }
}
