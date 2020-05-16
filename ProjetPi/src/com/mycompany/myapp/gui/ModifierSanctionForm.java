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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Sanction;
import com.mycompany.myapp.services.SanctionService;

/**
 *
 * @author rami2
 */
public class ModifierSanctionForm extends Form{
    
    public ModifierSanctionForm(Form previous, Sanction S)
    {
        setTitle("Modifier la sanction: "+S.getId());
        setLayout(BoxLayout.y());
        Button btnValider = new Button("Modifier");
        Label l1= new Label("Justification:");
        Label l2= new Label("Punition:");
        
        ComboBox<String> justifications = new ComboBox<>(
         "Retard", "Insolence", "Travail non fait"
        );
        
        ComboBox<String> punitions = new ComboBox<>(
         "Observation", "Retenue", "Avertissement"
        );
        
        
        punitions.setSelectedIndex(0);
        justifications.setSelectedIndex(0);
        
        btnValider.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) 
            {  
                S.setPunition(punitions.getSelectedItem());
                S.setRaisonSanction(justifications.getSelectedItem());
                if(SanctionService.getInstance().modifierSanction(S))
                Dialog.show("Success","Modification reussie.",new Command("OK"));
                else
                Dialog.show("ERREUR", "Erreur serveur", new Command("OK"));  
            }
        });
        
        
        addAll(l1, justifications, l2, punitions, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new ListSanctionsForm(previous).show());       
    }   
}
