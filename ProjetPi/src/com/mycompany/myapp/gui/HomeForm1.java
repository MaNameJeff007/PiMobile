/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ShareButton;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Mohamed Turki
 */
public class HomeForm1 extends Form {

    Form current;

    public HomeForm1(Resources theme) {
        current = this;
        setTitle("Home");
        setLayout(BoxLayout.y());

        add(new Label("Choose an option"));

        Button btnClubsForm = new Button("Clubs");

        btnClubsForm.addActionListener(e -> new ClubsForm(theme, current).show());


        add(btnClubsForm);
    }
}
