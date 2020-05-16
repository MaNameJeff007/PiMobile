/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Note;
import com.mycompany.myapp.services.NoteService;
import java.util.ArrayList;

/**
 *
 * @author rami2
 */
public class ModifierNoteForm extends Form 
{
    public ModifierNoteForm(Form previous, Note N) 
    {
        setTitle("Modifier la note:"+N.getId());
        setLayout(BoxLayout.y());
        Button btnValider = new Button("Modifier");
        TextField valeur = new TextField("","valeur");
        Label l1= new Label("Type:");
        ComboBox<String> types = new ComboBox<>(
         "CC", "Devoir de controle", "Devoir de synthese"
        );
        
        btnValider.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) 
            {             
                if(valeur.getText().length()==0)
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {              
                    try {                        
                        N.setType(types.getSelectedItem());
                        N.setValeur(Double.valueOf(valeur.getText()));
                        ArrayList<Note> Tab=NoteService.getInstance().verifierNote(N);
                        if(Tab.isEmpty())
                        {
                            if(NoteService.getInstance().modifierNote(N))
                            Dialog.show("Success","Modification reussie",new Command("OK"));
                            else
                            Dialog.show("ERREUR", "Erreur serveur", new Command("OK"));                
                        }
                        
                        else
                        {
                            boolean b=true;
                            for(int i=0;i<Tab.size();i++)
                            {
                                System.out.print("note:"+Tab.get(i).getId()+" "+Tab.get(i).getType()+"\n");
                                if((Tab.get(i).getType().equals(N.getType()))&&(Tab.get(i).getId()!=N.getId()))
                                {
                                  b=false;
                                  System.out.print("note identique trouvee:"+Tab.get(i).getId()+" "+Tab.get(i).getType()+"\n");
                                  break;
                                }                                
                            }
                           
                            if(b)
                            {
                                if(NoteService.getInstance().modifierNote(N))
                                Dialog.show("Success","Modification reussie.",new Command("OK"));
                                else
                                Dialog.show("ERREUR", "Erreur serveur", new Command("OK"));  
                            }
                            
                            else
                            Dialog.show("Erreur","Note identique existe deja.",new Command("OK"));
                        }
       
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Valeur doit etre un nombre", new Command("OK"));
                    }                 
                }
            }
        });
        
        addAll(l1, types, valeur, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new ListNotesForm(previous).show()); 
    }
}
