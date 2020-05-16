/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author rami2
 */
public class HomeEnseignantForm extends Form{
    
    Form current;
    public HomeEnseignantForm()
    {
        current=this;
        setTitle("Home Enseignant");
        setLayout(BoxLayout.y());
        Button btnListTasks1 = new Button("Liste Note");
        Button btnListTasks2 = new Button("Liste Sanctions");
        Button btnListTasks3 = new Button("Liste Absences");
        Button btnListTasks4 = new Button("Liste Moyennes");
        
        btnListTasks1.addActionListener(e-> new ListNotesForm(current).show());
        btnListTasks2.addActionListener(e-> new ListSanctionsForm(current).show());
        btnListTasks3.addActionListener(e-> new ListAbsencesForm(current).show());
        btnListTasks4.addActionListener(e-> new ListMoyennesForm(current).show());
        addAll(btnListTasks1,btnListTasks2, btnListTasks3, btnListTasks4); 
        getToolbar().addMaterialCommandToLeftSideMenu("Home", FontImage.MATERIAL_HOME, e -> new HomeForm().show());
    }
    
}
