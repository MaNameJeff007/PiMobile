/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextComponent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.services.ServiceSujet;

/**
 * GUI builder created Form
 *
 * @author admin
 */
public class AddSujetForm extends Form {

    private Form current;

    public AddSujetForm() {
        TextModeLayout tm = new TextModeLayout(4, 2);
        setLayout(new BorderLayout());
        setTitle("new sujets");
        Container content = new Container();

        TextComponent name = new TextComponent().labelAndHint("Titre");
        content.add(name);

        TextComponent desc = new TextComponent().labelAndHint("Description").multiline(true).rows(3);
        content.add(desc);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ForumForm(current).show());
        Button submit = new Button("Add");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
        submit.addActionListener(e -> {
            Sujet s = new Sujet();
            s.setCreateur_id(new Login().getcurrentUser().getIdentifiant());
            s.setTitre(name.getText());
            s.setDescription(desc.getText());
            if (ServiceSujet.getInstance().addSujet(s)) {
                 new ForumForm(current).show();
            } else {
                Dialog.show("Dialog Title", "mochekla", "OK", null);
            }
        });
        add(BorderLayout.NORTH, content);
        add(BorderLayout.SOUTH, submit);

        Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
        val.addConstraint(name, new GroupConstraint(new LengthConstraint(10), new RegexConstraint("^([a-zA-Z ]*)$", "Please only use latin characters for name"))).addSubmitButtons(submit);
        val.addConstraint(desc, new GroupConstraint(new LengthConstraint(15), new RegexConstraint("^([a-zA-Z ]*)$", "Please only use latin characters for name"))).addSubmitButtons(submit);

    }
}
