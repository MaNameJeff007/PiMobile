/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.list.DefaultListModel;
import com.mycompany.myapp.entities.Note;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.MatiereService;
import com.mycompany.myapp.services.NoteService;
import com.mycompany.myapp.services.ServiceReclamation;
import com.mycompany.myapp.services.UserService;

/**
 * GUI builder created Form
 *
 * @author Selim Chikh Zaouali
 */
public class AddReclamation extends com.codename1.ui.Form {

    int idConnecte = 9; // Login().getcurrentUser().getIdentifiant()

    public AddReclamation() {
        TextModeLayout tm = new TextModeLayout(4, 2);
        setLayout(new BorderLayout());
        setTitle("Envoyer une réclamation");
        Container cont1 = new Container();
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ReclamationF().show());
        cont1.add(new Label("Enfant"));

        ComboBox<String> enf = new ComboBox<>();
        // Affiche les enfants du parent connecté
        String idprenomnom = "";
        String identif = "";
        int ident = 0;
        for (User u : UserService.getInstance().getEnfants(idConnecte)) {
            ident = u.getIdentifiant();
            System.out.println(ident);
            identif = String.valueOf(ident);
            idprenomnom = identif + " " + u.getPrenom() + " " + u.getNom();
            enf.addItem(idprenomnom);
        }
        enf.setSelectedIndex(0);
        cont1.add(enf);

        cont1.add(new Label("Note"));

        ComboBox<String> note = new ComboBox<>();
        enf.addActionListener(e -> {
            int idmatiere = 0;
            String nomMatiere = "";
            String s = enf.getSelectedItem();
            int id = Integer.parseInt(s.substring(0, s.indexOf(" ")));

            DefaultListModel dlm = (DefaultListModel) note.getModel();
            dlm.removeAll();
            int idnote = 0;
            // Affiche les notes de l'élève selectionné dans la première ComboBox 
/*int j=0;
            for (Note n : NoteService.getInstance().getValeurNote(id)) {
                idnote = n.getId();
                idmatiere = Integer.parseInt(n.getMatiereid());
                nomMatiere = MatiereService.getInstance().getNomMatiere(idmatiere).get(j).getNom();
                String not = String.valueOf(n.getValeur()) + " " + nomMatiere + " " + String.valueOf(idnote);
                note.addItem(not);
                j++;
            }*/
            
            for (int i = 0; i < NoteService.getInstance().getValeurNote(id).size(); i++) {

                idnote = NoteService.getInstance().getValeurNote(id).get(i).getId();
                idmatiere = Integer.parseInt(NoteService.getInstance().getValeurNote(id).get(i).getMatiereid());
                nomMatiere = MatiereService.getInstance().getNomMatiere(idmatiere).get(i).getNom();
                String not = String.valueOf(NoteService.getInstance().getValeurNote(id).get(i).getValeur()) + " " + nomMatiere + " " + String.valueOf(idnote);
                note.addItem(not);
            }
        });
        cont1.add(note);

        Button submit = new Button("Envoyer");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
        submit.addActionListener(e -> {
            Reclamation r = new Reclamation();
            r.setEtat("non traitee");
            //a.setParent(new Login().getcurrentUser().getIdentifiant());
            r.setParent(idConnecte);
            String nn = note.getSelectedItem();
            int idnot = Integer.parseInt(nn.substring(nn.lastIndexOf(" ") + 1));
            String s = enf.getSelectedItem();
            String s1=s.substring(s.indexOf(" ")+1);
            String det = nn.substring(0, nn.lastIndexOf(" ")) + " " + s1;
            r.setNote(idnot);
            r.setDetails(det);
            if (ServiceReclamation.getInstance().addReclamation(r)) {
                LocalNotification n = new LocalNotification();
                n.setId("demo-notification");
                n.setAlertBody("Vous avez réclamé une note");
                n.setAlertTitle("Succès !");

                Display.getInstance().scheduleLocalNotification(
                        n,
                        System.currentTimeMillis() + 10 * 1000, // fire date/time
                        LocalNotification.REPEAT_MINUTE // Whether to repeat and what frequency
                );
                new ReclamationF().show();
            } else {
                Dialog.show("Dialog Title", "mochekla", "OK", null);
            }
        });
        add(BorderLayout.NORTH, cont1);
        add(BorderLayout.SOUTH, submit);

    }

    public AddReclamation(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
    }

//////////////////////////////////////-- DON'T EDIT BELOW THIS LINE!!!


// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.LayeredLayout());
        setInlineStylesTheme(resourceObjectInstance);
                setInlineStylesTheme(resourceObjectInstance);
        setTitle("AddReclamation");
        setName("AddReclamation");
    }// </editor-fold>

//-- DON'T EDIT ABOVE THIS LINE!!!
}
