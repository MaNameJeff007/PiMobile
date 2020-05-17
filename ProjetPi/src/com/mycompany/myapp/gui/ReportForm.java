/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

/**
 *
 * @author Mohamed Turki
 */
public class ReportForm extends Form {

    public ReportForm(Form previous) {

        setTitle("Activities");
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        TextArea body = new TextArea("Déposez votre réclamation ici!", 5, 20);
        Button btnReport = new Button("Send");
        TextField mail = new TextField("", "Your mail");
        TextField subject = new TextField("Club Report");
        btnReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Message m = new Message(body.getText());
                Display.getInstance().sendMessage(new String[]{mail.getText()}, subject.getText(), m);
            }
        }
        );
        add(subject);
        add(mail);
        add(body);
        add(btnReport);

    }
}
