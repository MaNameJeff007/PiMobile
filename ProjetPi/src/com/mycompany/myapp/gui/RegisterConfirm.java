/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceUser;

/**
 * GUI builder created Form
 *
 * @author admin
 */
public class RegisterConfirm extends com.codename1.ui.Form {

    private final Label code;
    private final TextField codetf;
    private final Container mainContainer;
    private final Button addBtn;
    Form current;

    public RegisterConfirm(int codee) {
        this.setLayout(new BorderLayout());
        mainContainer = new Container();

        code = new Label("Code");
        codetf = new TextField("");
        mainContainer.add(code);
        
        mainContainer.add(codetf);

        //login.getUnselectedStyle().setAlignment(Component.CENTER);
        addBtn = new Button("Login");

        addBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            String s = Integer.toString(codee);
            if (s.equals(codetf.getText())) {
                Dialog.show("Confirm", "Code correct", "OK", "");
                new Login().show();
            } else {
                Dialog.show("Confirm", "Code incorrect", "OK", "");
            }

        });

        mainContainer.add(addBtn);
        this.add(BorderLayout.NORTH, mainContainer);

    }
}
