/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.Moyenne;
import com.mycompany.myapp.services.MoyenneService;
import java.util.ArrayList;

/**
 *
 * @author rami2
 */
public class ListMoyennesForm extends Form
{
    public ListMoyennesForm(Form previous) 
    {
        int classe=Integer.valueOf(Login.currentUser.getClasseenseignant_id());
        int enseignant=Login.currentUser.getIdentifiant();
        
        setTitle("Liste des moyennes:");
        Form list = new Form("Moyennes:", BoxLayout.y());
        ArrayList<Moyenne> arr = new ArrayList<>();
        
        for (int i = 0; i < MoyenneService.getInstance().getMoyennes(classe).size(); i++) 
        {
            arr.add(MoyenneService.getInstance().getMoyennes(classe).get(i));
        }
        
        for (Moyenne us : arr) 
        {
            list.add(addItem(us));
        }
        
        addAll(list);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeEnseignantForm().show());     
    }
    
    private Container addItem(Moyenne M) 
    {
        Form current = new Form("Notes:", BoxLayout.y());
        SpanLabel  sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
       
        sp.setText(M.toString());
        
        Button delete = new Button("Supprimer");
        Button calmoyenne= new Button("re-calculer");
        
        Container btns = new Container(BoxLayout.x());
        btns.addAll(delete, calmoyenne);
        cnt1.addAll(sp,btns);
        Container cnt2 = new Container(BoxLayout.x());
        cnt2.add(cnt1);
        
         delete.addActionListener((e) -> {

            if (MoyenneService.getInstance().supprimerMoyenne(M)) 
            {
               new ListMoyennesForm(current).show();

            } else {
               
                System.out.println("test");
            }

        }
        );
          
        calmoyenne.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt) 
            {            
              if(MoyenneService.getInstance().recalculerMoyenneDepuisMoyenne(M))
              {
                Dialog.show("Success","Calcul reussie.",new Command("OK")); 
                new ListMoyennesForm(current).show();
              }
              else
              Dialog.show("ERREUR", "Erreur serveur", new Command("OK")); 
            }
        });
        return cnt2;
    }
    
}
