/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;

import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Matiere;
import com.mycompany.myapp.services.ServiceMatiere;
import java.io.IOException;



/**
 *
 * @author dell
 */
public class AfficherMatiere extends Form {
   Form current, list;
    

    private Container addItem(Matiere u) {
        SpanLabel  sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
   sp.setText("Nom : "+u.getNom()+"  Nombre des heures : "+u.getNbH());
                
        Button delete = new Button("supprimer");
        Button update = new Button("modifier");
        Container btns = new Container(BoxLayout.x());
        btns.addAll(delete, update);
        cnt1.addAll(sp,btns);
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
        Container cnt2 = new Container(BoxLayout.x());
        //Label lbimg = new Label();

        cnt2.add(cnt1);
        delete.addActionListener((e) -> {

            if (ServiceMatiere.getInstance().deleteMatiere(u)) {
               
                new AfficherMatiere(current).show();

            } else {
               
                System.out.println("hhhhhhh");
            }

        }
        );

        update.addActionListener((e) -> {
           new ModifierMatiere(current, u).show();
        }
        );

        return cnt2;
    }
    
   public AfficherMatiere(Form previous) {
         current = this;
        setLayout(BoxLayout.y());
        
         TextField recherche = new TextField();
       
        Button i = new  Button("Filter");
         
        Form list = new Form(" Matières", BoxLayout.y());
        Button Ajouter= new Button("Ajouter une matière");
        setTitle("Liste des matières ");
      
        for (Matiere s : ServiceMatiere.getInstance().getAllMatieres()) {
            list.add(addItem(s));
        }
        
          Ajouter.addActionListener((e) -> {

                new AjouterMatiere(current).show();
        }
        );
          
           i.addActionListener((e) -> {

                new FiltreMatiere(current,recherche.getText().toString()).show();
        }
        );
          
       addAll(recherche,i,Ajouter,list);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeScolarite().show());
    }    
}
