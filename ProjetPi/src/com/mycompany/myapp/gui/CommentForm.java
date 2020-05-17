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
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextComponent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import static com.codename1.ui.layouts.mig.LayoutUtil.MAX;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.myapp.entities.Commentaire;
import com.mycompany.myapp.entities.Likes;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.services.ServiceCommentaire;
import com.mycompany.myapp.services.ServiceLike;
import com.mycompany.myapp.services.ServiceSignaler;
import com.mycompany.myapp.services.ServiceSujet;
import com.mycompany.myapp.services.ServiceUser;

/**
 * GUI builder created Form
 *
 * @author admin
 */
public class CommentForm extends com.codename1.ui.Form {

    Form current, list;

    public CommentForm(Sujet s) {
        refresh(s);
        revalidate();
    }

    private void refresh(Sujet s) {
        removeAll();
        Container cnt1 = new Container(BoxLayout.y());
        SpanLabel l2 = new SpanLabel(s.getTitre());
        SpanLabel l4 = new SpanLabel("by " + ServiceUser.getInstance().getUser(s.getCreateur_id()));
        SpanLabel l3 = new SpanLabel(s.getDate());
        l2.getStyle().setUnderline(focusScrolling);
        SpanLabel l5 = new SpanLabel(s.getDescription());
        cnt1.setLayout(BoxLayout.y());
        setTitle(s.getTitre());
        Container cnt2 = new Container(BoxLayout.y());
        cnt2.setLayout(new BorderLayout());
        SpanLabel l6 = new SpanLabel(Integer.toString(s.getScore()));
        SpanLabel l7 = new SpanLabel(Integer.toString(s.getVues())+" vues");
        cnt2.add(BorderLayout.WEST, l6);
        cnt2.add(BorderLayout.EAST, l7);
        cnt1.addAll(l2, l4, l3, l5,cnt2);
        cnt1.getStyle().setPaddingBottom(150);
        add(cnt1);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ForumForm(current).show());
        for (Commentaire c : ServiceCommentaire.getInstance().getCommentaire(s.getSujet_id())) {
            add(addItem(c, s));
        }
        Container cont = new Container();
        cont.setLayout(new BorderLayout());
        Container content = new Container();
        TextComponent desc = new TextComponent().labelAndHint("Comment").multiline(true).rows(3);
        content.add(desc);
        Button submit = new Button("Add");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
        submit.addActionListener(e -> {
            Commentaire c = new Commentaire();
            c.setCreateur_id(new Login().getcurrentUser().getIdentifiant());
            c.setTexte(desc.getText());
            c.setSujet_id(s.getSujet_id());
            if (ServiceCommentaire.getInstance().addCommentaire(c)) {
                refresh(s);
            } else {
                Dialog.show("Dialog Title", "mochekla", "OK", null);
            }
        });
        content.getStyle().setPaddingTop(50);
        cont.add(BorderLayout.NORTH, content);
        cont.add(BorderLayout.SOUTH, submit);
        add(cont);
        Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
        val.addConstraint(desc, new GroupConstraint(new LengthConstraint(30), new RegexConstraint("^([a-zA-Z ]*)$", "Please only use latin characters for name"))).addSubmitButtons(submit);

    }

    private Container addItem(Commentaire c, Sujet s) {
        Container cnt1 = new Container(BoxLayout.y());
        cnt1.setSize(new Dimension(MAX,MAX));
        SpanLabel l2 = new SpanLabel(ServiceUser.getInstance().getUser(c.getCreateur_id()));
        SpanLabel l4 = new SpanLabel(c.getDate());
        SpanLabel l3 = new SpanLabel(c.getTexte());
        SpanLabel l5 = new SpanLabel(Integer.toString(c.getScore()));
        Button btnsupprimer = new Button("");
        FontImage.setMaterialIcon(btnsupprimer, FontImage.MATERIAL_DELETE);
        Button btnmodif = new Button("");
        FontImage.setMaterialIcon(btnmodif, FontImage.MATERIAL_EDIT);
        Button btnreport = new Button("");
        FontImage.setMaterialIcon(btnreport, FontImage.MATERIAL_REPORT);
        Button btnlike = new Button("");
        FontImage.setMaterialIcon(btnlike, FontImage.MATERIAL_THUMB_UP);
        
        Button btndislike = new Button("");
        FontImage.setMaterialIcon(btndislike, FontImage.MATERIAL_THUMB_DOWN);
        cnt1.add(l2);
        cnt1.add(l4);
        cnt1.add(l3);
        Container cnt3 = new Container(BoxLayout.x());
        cnt3.add(l5);
        Likes ll = new Likes();
        cnt3.add(btnlike);
        for (Likes l : ServiceLike.getInstance().getAllLikes()) {
            if (l.getCommentaire_id() == c.getCommentaire_id() && l.getCreateur_id() == new Login().getcurrentUser().getIdentifiant()) {
                cnt3.add(btndislike);
                ll.setLike_id(l.getLike_id());
                cnt3.removeComponent(btnlike);
            }
        }
        if (c.getCreateur_id() == new Login().getcurrentUser().getIdentifiant()) {
            cnt3.add(btnsupprimer);
            cnt3.add(btnmodif);
        }
        
        cnt3.add(btnreport);
        btnsupprimer.addActionListener((e) -> {
            if (ServiceCommentaire.getInstance().deleteCommentaire(c.getCommentaire_id())) {
                refresh(s);
            } else {
                System.out.println(s.getTitre());
            }
        });
        btnreport.addActionListener((e) -> {
            if (ServiceSignaler.getInstance().addSignaleComm(c)) {
                Dialog.show("Report", "Comment reported", "OK","");
            } else {
                System.out.println(s.getTitre());
            }
        });
        btnmodif.addActionListener((e) -> {
            new ModifCommentForm(c, s).show();
        });
        btnlike.addActionListener((e) -> {
            if (ServiceLike.getInstance().addLikeComm(c)) {
                refresh(s);
            } else {
                System.out.println(s.getTitre());
            }
        });
        btndislike.addActionListener((e) -> {
            if (ServiceLike.getInstance().deleteCommLike(ll.getLike_id())) {
                refresh(s);
            } else {
                System.out.println(s.getTitre());
            }
        });
        cnt1.add(cnt3);
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
        Container cnt2 = new Container(BoxLayout.y());
        cnt2.add(cnt1);
        return cnt2;
    }
}
