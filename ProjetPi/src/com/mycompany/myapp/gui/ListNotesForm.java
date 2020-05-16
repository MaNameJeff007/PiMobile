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
import com.mycompany.myapp.entities.Note;
import com.mycompany.myapp.services.MoyenneService;
import com.mycompany.myapp.services.NoteService;
import java.util.ArrayList;

/**
 *
 * @author rami2
 */
public class ListNotesForm extends Form{

    public ListNotesForm(Form previous) 
    {
        int classe=Integer.valueOf(Login.currentUser.getClasseenseignant_id());
        int enseignant=Login.currentUser.getIdentifiant();
        
        setTitle("Liste des notes ");
        Button Ajouter = new Button("Ajouter une Note");
        Form list = new Form("Notes:", BoxLayout.y());
        ArrayList<Note> arr = new ArrayList<>();
       
        
        for (int i = 0; i < NoteService.getInstance().getNotes(enseignant).size(); i++) 
        {
            arr.add(NoteService.getInstance().getNotes(enseignant).get(i));

        }

        for (Note us : arr) 
        {
            list.add(addItem(us));
        }
        
        Ajouter.addActionListener((e) -> 
        {
         new AddNoteForm(previous).show();
        }
        );

        addAll(list, Ajouter);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeEnseignantForm().show());
    }
    
    private Container addItem(Note n) 
    {
        Form current = new Form("Notes:", BoxLayout.y());
        SpanLabel  sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
       
        sp.setText("Trimestre :    "+n.getTrimestre()+"    Type : "+n.getType()+"     Matière :    "+n.getMatierenom()+"    Valeur :    "+n.getValeur()+"    Eleve :    "+n.getElevenom());
                
        Button delete = new Button("Supprimer");
        Button update = new Button("Modifier ");
        Button calmoyenne= new Button("Moyenne");
    
        Container btns = new Container(BoxLayout.x());
        btns.addAll(delete, update, calmoyenne);
        cnt1.addAll(sp,btns);
        Container cnt2 = new Container(BoxLayout.x());
        cnt2.add(cnt1);
        
         delete.addActionListener((e) -> {

            if (NoteService.getInstance().supprimerNote(n)) 
            {
               new ListNotesForm(current).show();

            } else {
               
                System.out.println("test");
            }

        }
        );
        
         update.addActionListener((e) -> 
         {
            new ModifierNoteForm(current, n).show();
         }
        );
         
         calmoyenne.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt) 
            { 
              ArrayList<Note> tab=NoteService.getInstance().getNotespourMoyenne(n);
              if(MoyenneService.getInstance().addMoyenneDepuisNote(tab))
              Dialog.show("Success","Calcul reussie.",new Command("OK"));  
              else
              Dialog.show("ERREUR", "Erreur serveur", new Command("OK")); 
            }
        });
        return cnt2;
    }
} 
    

