/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.User;
import static com.mycompany.myapp.gui.Login.currentUser;

/**
 *
 * @author bhk
 */
public class HomeForm extends Form {

    Form current;

    public HomeForm() 
    {
        current = this;
        setTitle("Home");
        setLayout(BoxLayout.y());
        Button menuScolarite = new Button("Menu service Scolarité");
        
        Button menuEnseignant = new Button("Menu service Enseignant");
       // add(new Label("Choose an option"));
        //Button btnListTasks = new Button("List Tasks");

        menuScolarite.addActionListener(e -> new HomeScolarite().show());
        
        

        
        // btnListTasks.addActionListener(e -> new ForumForm(current).show());
        add(menuScolarite);
        if(currentUser.getRoles().contains("ENSEIGNANT"))
        {
          getToolbar().addMaterialCommandToLeftSideMenu("Enseignant", FontImage.MATERIAL_NOTE, e -> new HomeEnseignantForm().show());  
        }
        
        
        else if(currentUser.getRoles().contains("PARENT"))
        {
          getToolbar().addMaterialCommandToLeftSideMenu("Parent", FontImage.MATERIAL_PERSON, e -> new ListElevesParentForm().show());     
        }
        
        getToolbar().addMaterialCommandToLeftSideMenu("Home", FontImage.MATERIAL_HOME, e -> new HomeForm().show());
        getToolbar().addMaterialCommandToLeftSideMenu("Forum", FontImage.MATERIAL_FORUM, e -> new ForumForm(current).show());
        getToolbar().addMaterialCommandToLeftSideMenu("Disconnect", FontImage.MATERIAL_CLOSE, e -> new Login().show());
    }

}
