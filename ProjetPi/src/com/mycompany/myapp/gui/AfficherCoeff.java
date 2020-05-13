/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.Coeff;
import com.mycompany.myapp.entities.Seance;
import com.mycompany.myapp.services.ServiceCoeff;
import com.mycompany.myapp.services.ServiceSeance;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class AfficherCoeff extends Form{
    Form current, list;
    private Container addItem(Coeff u) {
        SpanLabel sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
          cnt1.getStyle().setBorder(Border.createLineBorder(1));
       
    sp.setText("Niveau : "+u.getNiveau().getLibelle()+"  Matière : "+u.getMatiere().getNom()+" Valeur : "+u.getValeur());
                
        Button delete = new Button("Supprimer");
        Button update = new Button("Modifier ");
    
        Container btns = new Container(BoxLayout.x());
        btns.addAll(delete, update);
         cnt1.addAll(sp,btns);
        Container cnt2 = new Container(BoxLayout.x());
        cnt2.add(cnt1);
        
         delete.addActionListener((e) -> {

            if (ServiceCoeff.getInstance().deleteCoeff(u)) {
               
                new AfficherCoeff(current).show();

            } else {
               
                System.out.println("hhhhhhh");
            }

        }
        );
        
         update.addActionListener((e) -> {
            new ModifierCoeff(current,u).show();
        }
        );
        
        
        return cnt2;
    }

     public AfficherCoeff(Form previous) {
        setTitle("Liste des coefficicents ");
            Button Ajouter = new Button("Ajouter un Coefficient");
        Form list = new Form("Hi World", BoxLayout.y());
        ArrayList<Coeff> arr = new ArrayList<>();
       
        
        for (int i = 0; i < ServiceCoeff.getInstance().getAllCoeff().size(); i++) {
            arr.add(ServiceCoeff.getInstance().getAllCoeff().get(i));

        }

        for (Coeff us : arr) {
            list.add(addItem(us));
        }
        
       Ajouter.addActionListener((e) -> {

                new AjouterCoeff(current).show();
        }
        );

        //list.show();
        System.out.println(arr);
        addAll(Ajouter,list);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeScolarite().show());
    }

}
