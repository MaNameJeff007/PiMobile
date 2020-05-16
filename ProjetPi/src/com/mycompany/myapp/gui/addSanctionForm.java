/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.entities.Sanction;
import com.mycompany.myapp.services.SanctionService;
import com.mycompany.myapp.services.UserService;

/**
 *
 * @author rami2
 */
public class addSanctionForm extends Form 
{
    public addSanctionForm(Form previous) 
    {
        int classe=Integer.valueOf(Login.currentUser.getClasseenseignant_id());
        String enseignant=String.valueOf(Login.currentUser.getIdentifiant());
        
        setTitle("Ajouter une sanction:");
        setLayout(BoxLayout.y());
        Button btnValider = new Button("Ajouter");
        Label l1= new Label("Eleve:");
        Label l2= new Label("Justification:");
        Label l3= new Label("Punition:");
        Label l4= new Label("Date:");
        
        ComboBox<String> justifications = new ComboBox<>(
         "Retard", "Insolence", "Travail non fait"
        );
        
        ComboBox<String> punitions = new ComboBox<>(
         "Observation", "Retenue", "Avertissement"
        );
        
        ComboBox<String> eleves = new ComboBox<>();
        for (int i = 0; i<UserService.getInstance().getEleves(classe).size(); i++) 
        {
            String nom=String.valueOf(UserService.getInstance().getEleves(classe).get(i).getIdentifiant())+"-"+UserService.getInstance().getEleves(classe).get(i).getNom();
            eleves.addItem(nom);
        }
        
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        
        eleves.setSelectedIndex(0);
        punitions.setSelectedIndex(0);
        justifications.setSelectedIndex(0);
        
        btnValider.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) 
            {
                   String id_eleve=eleves.getSelectedItem().substring(0,2);
                   String nom_eleve;
                    
                   boolean flag = Character.isDigit(id_eleve.charAt(1));
        
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
                   
                   String StringToDate = new SimpleDateFormat("yyyy-MM-dd").format(datePicker.getDate());
                 
                   Sanction S=new Sanction(justifications.getSelectedItem(), StringToDate, punitions.getSelectedItem(), 0, enseignant, id_eleve, nom_eleve);

                   if(SanctionService.getInstance().addSanction(S))
                   Dialog.show("Success","Ajout reussie.",new Command("OK"));
                   else
                   Dialog.show("ERREUR", "Erreur serveur", new Command("OK"));    
                 }
            });
        
        addAll(l1, eleves, l2, justifications, l3, punitions, l4, datePicker, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new ListSanctionsForm(previous).show());    
        
    }
    
}
