/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.services.ServiceReclamation;

/**
 * GUI builder created Form
 *
 * @author Selim Chikh Zaouali
 */
public class ReclamationF extends com.codename1.ui.Form {

    int idConnecte = 9;

    public ReclamationF() {
        setLayout(BoxLayout.y());
        setTitle("Liste réclamations");
        setScrollableY(true);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeForm().show());
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> new AddReclamation().show());
        //System.out.println(new HomeForm().getcurrentUser().getIdentifiant());
        for (Reclamation r : ServiceReclamation.getInstance().getAllReclamation(idConnecte)) {
            add(addItem(r));
        }
    }

    private void refresh() {
        new ReclamationF().show();
    }

    private Container addItem(Reclamation r) {
        Container cnt1 = new Container(BoxLayout.y());
        SpanLabel L1 = new SpanLabel(r.getDetails());
        //SpanLabel L2 = new SpanLabel(r.getDate());
        SpanLabel L3 = new SpanLabel(r.getEtat());
        Button btnsupprimer = new Button("Supprimer");
        cnt1.add(L1);
        //cnt1.add(L2);
        cnt1.add(L3);
        Container cnt3 = new Container(BoxLayout.x());
        if (r.getEtat().equals("traitee")) {
            cnt3.add(btnsupprimer);
        }
        cnt1.add(cnt3);
        cnt1.getStyle().setBorder(Border.createLineBorder(0));
        Container cnt2 = new Container(BoxLayout.y());
        cnt2.add(cnt1);

        btnsupprimer.addActionListener((e) -> {
            if (ServiceReclamation.getInstance().deleteReclamation(r.getId())) {
                refresh();
            } else {
                System.out.println("erreur dans la suppression");
            }
        });
        return cnt2;
    }
}
