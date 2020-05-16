/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author rami2
 */
public class HomeNoteForm extends Form{
Form current;
    public HomeNoteForm() {
        current=this;
        setTitle("Home");
        setLayout(BoxLayout.y());
        
        add(new Label("Choose an option"));
        Button btnAddTask = new Button("Add Note");
        Button btnListTasks = new Button("List Note");
        
        btnAddTask.addActionListener(e-> new AddNoteForm(current).show());
        btnListTasks.addActionListener(e-> new ListNotesForm(current).show());
        addAll(btnAddTask,btnListTasks);       
    }
    
    
} 

