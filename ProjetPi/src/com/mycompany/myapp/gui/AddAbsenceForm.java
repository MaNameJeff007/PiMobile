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
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.entities.Absence;
import com.mycompany.myapp.services.AbsenceService;
import com.mycompany.myapp.services.UserService;


/**
 *
 * @author rami2
 */
public class AddAbsenceForm extends Form 
{
    public AddAbsenceForm(Form previous)
    {    
        int classe=Integer.valueOf(Login.currentUser.getClasseenseignant_id());
        String enseignant=String.valueOf(Login.currentUser.getIdentifiant());
        
        setTitle("Ajouter une absence:");
        setLayout(BoxLayout.y());
        Button btnValider = new Button("Ajouter");
        Label l1= new Label("Eleve:");
        Label l2= new Label("Justification:");
        Label l3= new Label("Heure debut:");
        Label l4= new Label("Heure fin:");
        Label l5= new Label("Date:");
        
        ComboBox<String> eleves = new ComboBox<>();
        for (int i = 0; i<UserService.getInstance().getEleves(classe).size(); i++) 
        {
            String nom=String.valueOf(UserService.getInstance().getEleves(classe).get(i).getIdentifiant())+"-"+UserService.getInstance().getEleves(classe).get(i).getNom();
            eleves.addItem(nom);
        }
        
        ComboBox<String> justifications = new ComboBox<>("A ajouter");
        
        TextField heuredebut=new TextField("", "heuredebut");
        TextField heurefin=new TextField("", "heurefin");
        
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        
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
              String justification=justifications.getSelectedItem();
              int hd=Integer.valueOf(heuredebut.getText());
              int hf=Integer.valueOf(heurefin.getText());
              
              Absence A=new Absence(justification, StringToDate, hd, hf, 0, enseignant, id_eleve);
              
              if(AbsenceService.getInstance().verifierAbsence(A))
              {
                if(AbsenceService.getInstance().addAbsence(A))
                Dialog.show("Success","Ajout reussie.",new Command("OK"));
                else
                Dialog.show("ERREUR", "Erreur serveur", new Command("OK")); 
              }
              
              else
              Dialog.show("ERREUR","Absence identique existe deja.",new Command("OK"));
            }       
        });
        
        addAll(l1, eleves, l2, justifications, l3, heuredebut, l4, heurefin, l5, datePicker, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new ListAbsencesForm(previous).show());            
    }   
}
