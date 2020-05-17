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
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.Attestation;
import com.mycompany.myapp.services.ServiceAttestation;

/**
 * GUI builder created Form
 *
 * @author Selim Chikh Zaouali
 */
public class AttestationF extends com.codename1.ui.Form {

Form current, list;
int idConnecte=9;
    /*Garder traçe de la Form en cours pour la passer en paramètres 
     aux interfaces suivantes pour pouvoir y revenir plus tard en utilisant
     la méthode showBack*/
    
    

    public AttestationF() {
        setLayout(BoxLayout.y());
        setTitle("Liste attestations");
        setScrollableY(true);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeForm().show());
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> new AddAttestation().show());
        //System.out.println(new HomeForm().getcurrentUser().getIdentifiant());
        for (Attestation a : ServiceAttestation.getInstance().getAllAttestation(idConnecte)) {
            add(addItem(a));
        }
    }
    
    private void refresh() {
        /*removeAll();
        
        for (Attestation a : ServiceAttestation.getInstance().getAllAttestation()) {
            add(addItem(a));
        }*/
        new AttestationF().show();
    }
       private Container addItem(Attestation a) {
        Container cnt1 = new Container(BoxLayout.y());
        
        SpanLabel L1 = new SpanLabel(a.getEnfant());
        //SpanLabel L2 = new SpanLabel(a.getDate());
        SpanLabel L3 = new SpanLabel(a.getEtat());
        Button btnsupprimer = new Button("Supprimer");
        cnt1.add(L1);
        //cnt1.add(L2);
        cnt1.add(L3);
        Container cnt3 = new Container(BoxLayout.x());
        if (a.getEtat().equals("traitee")) {
            cnt3.add(btnsupprimer);
        }
        cnt1.add(cnt3);
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
        Container cnt2 = new Container(BoxLayout.y());
        cnt2.add(cnt1);

        btnsupprimer.addActionListener((e) -> {
            if (ServiceAttestation.getInstance().deleteAttestation(a.getId())) {
                refresh();
            } else {
                System.out.println("erreur dans la suppression");
            }
        });
        return cnt2;
    }
}


