/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Matiere;
import com.mycompany.myapp.services.ServiceMatiere;

/**
 *
 * @author dell
 */
public class ModifierMatiere extends Form {
   Form current;
    
     public boolean checkMatiere(Matiere t) {
      for (int i = 0; i < ServiceMatiere.getInstance().getAllMatieres().size(); i++) {
          Matiere sc = ServiceMatiere.getInstance().getAllMatieres().get(i);
          if ( t.getNom().equals(sc.getNom()) )
          {
           return true;   
          }
      }
        return false;
    }
     
     public ModifierMatiere(Form previous,Matiere t) {
        setTitle("Modifier une matière ");
        setLayout(BoxLayout.y());
        
        TextField nom = new TextField("","Nom ");
        TextField nbH = new TextField("","Nombre des heures ");
        Button btnValider = new Button("Valider");
        
        nom.setText(t.getNom());
        nbH.setText(Integer.toString(t.getNbH()));
        
         btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length()==0)||(0 > nbH.getText().length()  ))
                    Dialog.show("Alert", "Veuillez saisir tous les champs", new Command("OK"));
                else
                {
                   try{
                        t.setNom(nom.getText());
                        t.setNbH(Integer.parseInt(nbH.getText()));
                       
                        if(checkMatiere(t)== false){
                        if( ServiceMatiere.getInstance().updateMatiere(t))
                             
                        Dialog.show("Success","Matiere modifée avec succès",new Command("OK"));
                        else
                        {
                           Dialog.show("ERROR", "Server error", new Command("OK"));  
                        }
                           
                        }else
                        {
                         Dialog.show("ERROR", "Modification échoué. cette Matière existe déja ", new Command("OK"));       
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "nbH doit etre supérieur à 0", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(nom,nbH,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new AfficherMatiere(current).show());
                
        
        
        
        
        
        
        
        
        
        
     } 
}
