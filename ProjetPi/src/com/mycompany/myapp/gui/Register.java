/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceSujet;
import com.mycompany.myapp.services.ServiceUser;
import java.util.Random;

/**
 * GUI builder created Form
 *
 * @author admin
 */
public class Register extends com.codename1.ui.Form {

    public int getcode() {
        Random r = new Random();
        return r.nextInt((9999 - 1000) + 1) + 10000;
    }

    public Register() {
        setLayout(new BorderLayout());
        setTitle("Register");
        Container content = new Container();

        TextComponent username = new TextComponent().labelAndHint("Username");
        content.add(username);

        TextComponent email = new TextComponent().labelAndHint("Email");
        content.add(email);

        TextComponent nom = new TextComponent().labelAndHint("Nom");
        content.add(nom);

        TextComponent prenom = new TextComponent().labelAndHint("Prenom");
        content.add(prenom);
        TextComponent password = new TextComponent().labelAndHint("Prenom");

        content.add(password);

        Button submit = new Button("Add");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
        submit.addActionListener(e -> {
            int code = getcode();
            ServiceUser.getInstance().Register(nom.getText(), prenom.getText(), email.getText(), username.getText(), "ROLE_ELEVE", Integer.toString(code));
            new RegisterConfirm(code).show();
        });
        add(BorderLayout.NORTH, content);
        add(BorderLayout.SOUTH, submit);

        Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
        val.addConstraint(username, new GroupConstraint(new LengthConstraint(5), new RegexConstraint("^([a-zA-Z ]*)$", "Please only use latin characters for name"))).addSubmitButtons(submit);
        val.addConstraint(nom, new GroupConstraint(new LengthConstraint(5), new RegexConstraint("^([a-zA-Z ]*)$", "Please only use latin characters for name"))).addSubmitButtons(submit);
        val.addConstraint(prenom, new GroupConstraint(new LengthConstraint(5), new RegexConstraint("^([a-zA-Z ]*)$", "Please only use latin characters for name"))).addSubmitButtons(submit);
        val.addConstraint(password, new GroupConstraint(new LengthConstraint(5), new RegexConstraint("^([a-zA-Z ]*)$", "Please only use latin characters for name"))).addSubmitButtons(submit);

    }
//-- DON'T EDIT ABOVE THIS LINE!!!
}
