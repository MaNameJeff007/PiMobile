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
import com.mycompany.myapp.services.MatiereService;
import com.mycompany.myapp.services.NoteService;
import com.mycompany.myapp.services.UserService;


/**
 *
 * @author rami2
 */
public class AddNoteForm extends Form
{
    public AddNoteForm(Form previous) 
    {
        int classe=Integer.valueOf(Login.currentUser.getClasseenseignant_id());
        String enseignant=String.valueOf(Login.currentUser.getIdentifiant());
        
        setTitle("Ajouter une note:");
        setLayout(BoxLayout.y());
        Button btnValider = new Button("Ajouter");
        TextField valeur = new TextField("","valeur");
        Label l1= new Label("Type:");
        Label l2= new Label("Trimestre:");
        Label l3= new Label("Matiere:");
        Label l4= new Label("Eleve:");
        Label l5= new Label("Valeur:");
        
        ComboBox<String> types = new ComboBox<>(
         "CC", "Devoir de controle", "Devoir de synthese"
        );
        
        ComboBox<Integer> trimestres= new ComboBox<>(1, 2, 3);
        
        ComboBox<String> eleves = new ComboBox<>();
        for (int i = 0; i<UserService.getInstance().getEleves(classe).size(); i++) 
        {
            String nom=String.valueOf(UserService.getInstance().getEleves(classe).get(i).getIdentifiant())+"-"+UserService.getInstance().getEleves(classe).get(i).getNom();
            eleves.addItem(nom);
        }
        
        ComboBox<String> matieres = new ComboBox<>();
        
        for(int i=0; i<MatiereService.getInstance().getMatieres().size();i++)
        {
            String nom=MatiereService.getInstance().getMatieres().get(i).getId()+"-"+MatiereService.getInstance().getMatieres().get(i).getNom();
            matieres.addItem(nom);       
        }
        
        eleves.setSelectedIndex(0);
        matieres.setSelectedIndex(0);

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
                        
                        String id_eleve=eleves.getSelectedItem().substring(0,2);
                        String mt=matieres.getSelectedItem().substring(0,2);
                        String nom_matiere;
                        String nom_eleve;
                        
                        
                        Boolean flag = Character.isDigit(mt.charAt(1));
                        if(flag) 
                        {
                            mt=matieres.getSelectedItem().substring(0,2);
                            nom_matiere=matieres.getSelectedItem().substring(2);
                        }
                        else 
                        {
                            mt=matieres.getSelectedItem().substring(0,1);
                            nom_matiere=matieres.getSelectedItem().substring(1);
                        }
                        
        
                        flag = Character.isDigit(id_eleve.charAt(1));
        
                        if(flag) 
                        {
                            id_eleve=eleves.getSelectedItem().substring(0,2);
                            nom_eleve=eleves.getSelectedItem().substring(2);
                        }
                        else 
                        {
                            id_eleve=eleves.getSelectedItem().substring(0,1);
                            nom_eleve=eleves.getSelectedItem().substring(1);
                        }
                        
                        
                        Note N = new Note(types.getSelectedItem(), trimestres.getSelectedItem(), enseignant, id_eleve, nom_eleve, mt, nom_matiere, Double.valueOf(valeur.getText()));
                        if(NoteService.getInstance().verifierNote(N).isEmpty())
                        {
                            if(NoteService.getInstance().addNote(N))
                            {
                              Dialog.show("Success","Ajout reussie.",new Command("OK"));
                              if(NoteService.getInstance().emailnote(N))
                              Dialog.show("Success","Email envoyé avec succès.",new Command("OK"));
                              else
                              Dialog.show("ERREUR", "Email non envoye", new Command("OK")); 
                            }
                            else
                            Dialog.show("ERREUR", "Erreur serveur", new Command("OK"));                
                        }
                        
                        else
                        {
                            Dialog.show("Erreur","Note existe deja.",new Command("OK"));
                        }
       
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Valeur doit etre un nombre", new Command("OK"));
                    }                 
                }
            }
        });
        
        addAll(l1, types, l2, trimestres, l3, matieres, l4, eleves, l5, valeur, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new ListNotesForm(previous).show());            
    }
}
