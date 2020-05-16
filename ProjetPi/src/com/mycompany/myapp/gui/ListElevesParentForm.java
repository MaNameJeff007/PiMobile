/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.getCurrentForm;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.UserService;
import java.util.ArrayList;


/**
 *
 * @author rami2
 */
public class ListElevesParentForm extends Form 
{   
    public ListElevesParentForm(/*Form previous*/)
    {
        setTitle("Liste de vos enfants: ");
        Form list = new Form("Vos enfants:", BoxLayout.y());
        ArrayList<User> arr = new ArrayList<>();
       
        
        for (int i = 0; i < UserService.getInstance().getElevesparparent(8).size(); i++) 
        {
            arr.add(UserService.getInstance().getElevesparparent(8).get(i));
        }

        for (User us : arr) 
        {
            list.add(addItem(us));
        }
        
        addAll(list);
        getToolbar().addMaterialCommandToLeftSideMenu("Home", FontImage.MATERIAL_HOME, e -> new HomeForm().show());   
    }
    
    private Container addItem(User U) 
    {
        Form current = new Form("Eleves:", BoxLayout.y());
        SpanLabel  sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
        cnt1.getStyle().setPadding(LEFT, 80);
       
        sp.setText("Nom : "+U.getPrenom()+" "+U.getNom()+"  Classe : "+U.getClasse());
                
        Button notes = new Button("Notes");
        Button absences = new Button("Absences");
        Button sanctions= new Button("Sanctions");
        Button moyennes= new Button("Moyennes");
    
        Container btns = new Container(BoxLayout.x());
        btns.addAll(notes, absences, sanctions, moyennes);
        cnt1.addAll(sp,btns);
        Container cnt2 = new Container(BoxLayout.x());
        cnt2.add(cnt1);
        
         notes.addActionListener((e) -> 
         {
             new NotesEleveForm(U).show();
         });
        
         absences.addActionListener((e) -> 
         {
             new AbsencesEleveForm(U).show(); 
         });
         
         sanctions.addActionListener((e) -> 
         {
             new SanctionsEleveForm(U).show();
             
         });
         
         moyennes.addActionListener((e) -> 
         {
             new MoyennesEleveForm(U).show();                   
         });
        
         
         return cnt2;
    }
    
}
