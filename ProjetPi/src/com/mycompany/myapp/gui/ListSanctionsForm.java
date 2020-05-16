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
import com.mycompany.myapp.entities.Sanction;
import com.mycompany.myapp.services.SanctionService;
import java.util.ArrayList;


/**
 *
 * @author rami2
 */
public class ListSanctionsForm extends Form
{
    public ListSanctionsForm(Form previous) 
    {
        int classe=Integer.valueOf(Login.currentUser.getClasseenseignant_id());
        int enseignant=Login.currentUser.getIdentifiant();
        
        setTitle("Liste des sanctions ");
        Button Ajouter = new Button("Ajouter une Sanction");
        Form list = new Form("Sanction:", BoxLayout.y());
        ArrayList<Sanction> arr = new ArrayList<>();
       
        
        for (int i = 0; i < SanctionService.getInstance().getSanctions(enseignant).size(); i++) 
        {
            arr.add(SanctionService.getInstance().getSanctions(enseignant).get(i));
        }

        for (Sanction us : arr) 
        {
            list.add(addItem(us));
        }
        
       Ajouter.addActionListener((e) -> {

           new addSanctionForm(previous).show();
        }
        );

        //list.show();
        System.out.println(arr);
        addAll(list, Ajouter);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeEnseignantForm().show());
    }
    
    private Container addItem(Sanction S) 
    {
        Form current = new Form("Sanctions:", BoxLayout.y());
        SpanLabel  sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
       
        sp.setText("Date de la sanction  :  "+S.getDateSanction()+"   Raison sanction : "+S.getRaisonSanction()+" Punition : "+S.getPunition()+"  Eleve : "+S.getElevenom());
                
        Button delete = new Button("Supprimer");
        Button update = new Button("Modifier ");
    
        Container btns = new Container(BoxLayout.x());
        btns.addAll(delete, update);
        cnt1.addAll(sp,btns);
        Container cnt2 = new Container(BoxLayout.x());
        cnt2.add(cnt1);
        
         delete.addActionListener((e) -> {

            if (SanctionService.getInstance().supprimerSanction(S)) 
            {
               new ListSanctionsForm(current).show();

            } else {
               
                System.out.println("test");
            }
        });
        
         update.addActionListener((e) -> 
         {
            new ModifierSanctionForm(current, S).show();
         });
        
        return cnt2;
    }       
}
