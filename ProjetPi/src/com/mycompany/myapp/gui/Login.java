/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
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
public class Login extends com.codename1.ui.Form {

    private final Label login, password;
    private final TextField loginTf, passwordTf;
    private final Container mainContainer;
    private final Button addBtn, addBtn2;
    Form current;
    static User currentUser = new User();

    public User getcurrentUser() {
        return currentUser;
    }

    public void setcurrentUser(User u) {
        currentUser.setIdentifiant(u.getIdentifiant());
        currentUser.setNom(u.getNom());
        currentUser.setUsername(u.getUsername());
        currentUser.setPrenom(u.getPrenom());
        currentUser.setEmail(u.getEmail());
        currentUser.setRoles(u.getRoles());

        if (currentUser.getRoles().contains("ENSEIGNANT")) {
            currentUser.setClasseenseignant_id(u.getClasseenseignant_id());
        }
    }

    public Login() {
        this.setLayout(new BorderLayout());
        mainContainer = new Container();

        login = new Label("Username");
        loginTf = new TextField("");
        mainContainer.add(login);
        mainContainer.add(loginTf);

        password = new Label("Password:");

        passwordTf = new TextField();
        passwordTf.setConstraint(com.codename1.ui.TextArea.PASSWORD);

        mainContainer.add(password);
        mainContainer.add(passwordTf);

        //login.getUnselectedStyle().setAlignment(Component.CENTER);
        addBtn = new Button("Login");
        addBtn2 = new Button("Register");

        addBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            SpanLabel sp = new SpanLabel();
            User s = ServiceUser.getInstance().getConnectedUser(loginTf.getText(), passwordTf.getText());
            if (s.getIdentifiant() != 0) {
                setcurrentUser(s);
                new HomeForm().show();
            } else {
                passwordTf.clear();
            }
            //sp.setText(ServiceUser.getInstance().getConnectedUser(loginTf.getText(), passwordTf.getText()).toString());
            //System.out.println(getcurrentUser().getIdentifiant());

        });
        addBtn2.addActionListener((ActionListener) (ActionEvent evt) -> {
            new Register().show();
        });
        mainContainer.add(addBtn);
        mainContainer.add(addBtn2);
        this.add(BorderLayout.NORTH, mainContainer);
    }
}
