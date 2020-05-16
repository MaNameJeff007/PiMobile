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
import com.mycompany.myapp.entities.Absence;
import com.mycompany.myapp.services.AbsenceService;


/**
 *
 * @author rami2
 */
public class ModifierAbsenceForm extends Form
{
    public ModifierAbsenceForm(Form previous, Absence A) 
    {
        setTitle("Modifier l'absence:");
        setLayout(BoxLayout.y());
        Button btnValider = new Button("Ajouter");
        Label l1= new Label("Justification:");
        Label l2= new Label("Etat:");
        TextField valeur = new TextField("","Justification");
        ComboBox<String> etats = new ComboBox<>(
         "0", "1"
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
                   A.setJustification(valeur.getText());
                   A.setEtat(Integer.valueOf(etats.getSelectedItem()));
                   if(AbsenceService.getInstance().modifierAbsence(A))
                   Dialog.show("Success","Modification reussie",new Command("OK"));
                   else
                   Dialog.show("ERREUR", "Erreur serveur", new Command("OK"));                
                 }
                
            } 
            
        });
        addAll(l1, valeur, l2, etats,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new ListAbsencesForm(previous).show()); 
    }
    
    
}
