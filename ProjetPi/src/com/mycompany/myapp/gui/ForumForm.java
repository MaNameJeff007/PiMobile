/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.Likes;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.services.ServiceLike;
import com.mycompany.myapp.services.ServiceSignaler;
import com.mycompany.myapp.services.ServiceSujet;
import java.util.ArrayList;

/**
 *
 * @author bhk
 */
public class ForumForm extends Form {

    Form current, list;
    /*Garder traçe de la Form en cours pour la passer en paramètres 
     aux interfaces suivantes pour pouvoir y revenir plus tard en utilisant
     la méthode showBack*/

    private ArrayList<String> searchLocations(String text) {
        ArrayList<String> ss = new ArrayList<>();
        for (Sujet s : ServiceSujet.getInstance().getAllSujet()) {
            if (s.getTitre().contains(text)) {
                System.out.println(text);
                ss.add(s.getTitre());
            }
        }
        return ss;
    }

    public ForumForm(Form previous) {
        setLayout(BoxLayout.y());
        setTitle("List sujets");
        Container cnt1 = new Container(BoxLayout.x());
        Button recherche = new Button("");

        final DefaultListModel<String> options = new DefaultListModel<>();
        AutoCompleteTextField auto = new AutoCompleteTextField(options) {
            @Override
            protected boolean filter(String text) {
                if (text.length() == 0) {
                    return false;
                }
                ArrayList<String> l = searchLocations(text);
                if (l == null || l.size() == 0) {
                    return false;
                }
                options.removeAll();
                for (String s : l) {
                    options.addItem(s);
                }
                return true;
            }
        };
        auto.setMinimumElementsShownInPopup(5);
        FontImage.setMaterialIcon(recherche, FontImage.MATERIAL_FIND_IN_PAGE);
        cnt1.addAll(recherche, auto);
        add(cnt1);
        if (auto.getTensileLength() > 4) {
            getToolbar().addMaterialCommandToLeftSideMenu("Home", FontImage.MATERIAL_HOME, e -> new HomeForm().show());
        }
        recherche.addActionListener((e) -> {
           for (Sujet ss : ServiceSujet.getInstance().getRechSujet(auto.getText())) {
               resh();
                add(addItem(ss));
            }
        });
        getToolbar().addMaterialCommandToLeftSideMenu("forum", FontImage.MATERIAL_FORUM, e -> new ForumForm(current).show());
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD_CIRCLE, e -> new AddSujetForm().show());
        for (Sujet s : ServiceSujet.getInstance().getAllSujet()) {
            add(addItem(s));
        }
    }

private void resh()
{
    removeAll();
        Container cnt1 = new Container(BoxLayout.x());
        Button recherche = new Button("");

        final DefaultListModel<String> options = new DefaultListModel<>();
        AutoCompleteTextField auto = new AutoCompleteTextField(options) {
            @Override
            protected boolean filter(String text) {
                if (text.length() == 0) {
                    return false;
                }
                ArrayList<String> l = searchLocations(text);
                if (l == null || l.size() == 0) {
                    return false;
                }
                options.removeAll();
                for (String s : l) {
                    options.addItem(s);
                }
                return true;
            }
        };
        auto.setMinimumElementsShownInPopup(5);
        FontImage.setMaterialIcon(recherche, FontImage.MATERIAL_FIND_IN_PAGE);
        cnt1.addAll(recherche, auto);
        add(cnt1);
        if (auto.getTensileLength() > 4) {
            getToolbar().addMaterialCommandToLeftSideMenu("Home", FontImage.MATERIAL_HOME, e -> new HomeForm().show());
        }
}
    private void refresh() {
        removeAll();
        Container cnt1 = new Container(BoxLayout.x());
        Button recherche = new Button("");

        final DefaultListModel<String> options = new DefaultListModel<>();
        AutoCompleteTextField auto = new AutoCompleteTextField(options) {
            @Override
            protected boolean filter(String text) {
                if (text.length() == 0) {
                    return false;
                }
                ArrayList<String> l = searchLocations(text);
                if (l == null || l.size() == 0) {
                    return false;
                }
                options.removeAll();
                for (String s : l) {
                    options.addItem(s);
                }
                return true;
            }
        };
        auto.setMinimumElementsShownInPopup(5);
        FontImage.setMaterialIcon(recherche, FontImage.MATERIAL_FIND_IN_PAGE);
        cnt1.addAll(recherche, auto);
        add(cnt1);
        if (auto.getTensileLength() > 4) {
            getToolbar().addMaterialCommandToLeftSideMenu("Home", FontImage.MATERIAL_HOME, e -> new HomeForm().show());
        }
       recherche.addActionListener((e) -> {
           for (Sujet ss : ServiceSujet.getInstance().getRechSujet(auto.getText())) {
               resh();
                add(addItem(ss));
            }
        });
        for (Sujet ss : ServiceSujet.getInstance().getAllSujet()) {
            add(addItem(ss));
            show();
        }
    }
    private Container addItem(Sujet s) {
        Container cnt1 = new Container(BoxLayout.y());
        //Label l1 = new Label(Integer.toString(s.getSujet_id()));
        SpanLabel l2 = new SpanLabel(s.getTitre());
        //l2.setSize(new Dimension(250, 100));
        //l2.setHeight(100);
        SpanLabel l4 = new SpanLabel(Integer.toString(s.getScore()));
        SpanLabel l3 = new SpanLabel(s.getDescription());
        Button btnsupprimer = new Button("");

        FontImage.setMaterialIcon(btnsupprimer, FontImage.MATERIAL_DELETE);
        Button btnmodif = new Button("");
        FontImage.setMaterialIcon(btnmodif, FontImage.MATERIAL_EDIT);

        Button btncomm = new Button("");
        FontImage.setMaterialIcon(btncomm, FontImage.MATERIAL_CHAT);

        Button btnreport = new Button("");
        FontImage.setMaterialIcon(btnreport, FontImage.MATERIAL_REPORT);

        Button btnlike = new Button("like");
        Button btndislike = new Button("dislike");

        ShareButton sb = new ShareButton();
        sb.setTextToShare(s.getTitre() + " : " + s.getDescription());
        cnt1.add(sb);
        cnt1.add(l2);
        cnt1.add(l3);
        Container cnt3 = new Container(BoxLayout.x());
        cnt3.add(l4);
        Likes ll = new Likes();
        cnt3.add(btnlike);
        for (Likes l : ServiceLike.getInstance().getAllLikes()) {
            if (l.getSujet_id() == s.getSujet_id() && l.getCreateur_id() == new Login().getcurrentUser().getIdentifiant()) {
                cnt3.add(btndislike);
                ll.setLike_id(l.getLike_id());
                cnt3.removeComponent(btnlike);
            }
        }
        cnt3.add(btncomm);
        if (s.getCreateur_id() == new Login().getcurrentUser().getIdentifiant()) {
            cnt3.add(btnsupprimer);
            cnt3.add(btnmodif);
        }
        cnt3.add(btnreport);
        cnt1.add(cnt3);
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
        Container cnt2 = new Container(BoxLayout.y());
        cnt2.add(cnt1);

        btnsupprimer.addActionListener((e) -> {
            if (ServiceSujet.getInstance().deleteSujet(s.getSujet_id())) {
                refresh();
            } else {
                System.out.println(s.getTitre());
            }
        });
        btnlike.addActionListener((e) -> {
            if (ServiceLike.getInstance().addLike(s)) {
                refresh();
            } else {
                System.out.println(s.getTitre());
            }
        });
        btnreport.addActionListener((e) -> {
            if (ServiceSignaler.getInstance().addSignaleSujet(s)) {
                Dialog.show("Report", "Sujet reported", "OK", "");
            } else {
                System.out.println(s.getTitre());
            }
        });
        btndislike.addActionListener((e) -> {
            if (ServiceLike.getInstance().deleteLike(ll.getLike_id())) {
                refresh();
            } else {
                System.out.println(s.getTitre());
            }
        });
        btnmodif.addActionListener((e) -> {
            new ModifierSujetForm(s.getSujet_id()).show();
        });
        btncomm.addActionListener((e) -> {
            new CommentForm(s).show();
        });
        return cnt2;
    }
}
