/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;


import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Activite;
import com.mycompany.myapp.entities.Club;
import com.mycompany.myapp.services.ServiceActivite;
import com.mycompany.myapp.services.ServiceClub;
import java.util.List;

import java.lang.Object;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Toolbar;

/**
 *
 * @author Mohamed Turki
 */
public class ClubsForm extends Form {

    private ServiceClub sc = new ServiceClub();
    private ServiceActivite act = new ServiceActivite();

    private String url = "http://localhost/symfony/Ecole--Edtech1/web/images/";
    private ImageViewer iv;
    private Image img;
    private EncodedImage enc;

    public ClubsForm(Resources theme, Form previous) {
        setTitle("Clubs");
        Container bigone = new Container();

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Button EventsClub = new Button("Evenements");
        Button ActivitiesClub = new Button("Activités");
        enc = EncodedImage.createFromImage(theme.getImage("esprit.png"), false);
        TextField search = new TextField("", "Recherche  Club", 20, TextArea.ANY);

        List<Club> clubss = sc.ShowAllClubs();

        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                        || line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                getContentPane().animateLayout(150);
            }
        }, 4);
        // search.keyPressed(CENTER);
        search.addDataChangeListener((i1, i2) -> {
            System.out.println(search.getText());
            bigone.removeAll();

            for (Club c : clubss) {
                if (c.getNomclub().startsWith(search.getText())) {
                    int score = 0;
                    for (Activite a : act.ShowAllactivites(c.getUser_id())) {
                        score += a.getVote();
                    }
                    System.out.println(score);
                    Container Y = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                    Container X = new Container(new BoxLayout(BoxLayout.X_AXIS));
                    Container X1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                    Container X2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                    Container X4 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                    //Y.getStyle().setFgColor(5855577);
                    SpanLabel sp = new SpanLabel();
                    sp.setText("   Responsable : ");
                    SpanLabel sp1 = new SpanLabel();
                    sp1.setText(c.getResponsable());
                    SpanLabel sp2 = new SpanLabel();
                    sp2.setText("   Club :   ");
                    SpanLabel sp3 = new SpanLabel();
                    sp3.setText(c.getNomclub() + "   ");

                    SpanLabel sp4 = new SpanLabel();
                    sp4.setText("   Club Score:  " + score);

                    iv = new ImageViewer();
                    String urlx = url + c.getNom_image();

                    img = URLImage.createToStorage(enc, urlx, urlx, URLImage.RESIZE_SCALE);
                    iv.setImage(img.scaled(200, 200));

                    Button btnActivities = new Button("Activités");
                    Button btnEvenements = new Button("Evenements");
                    btnActivities.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            new ActivitiesForm(theme, previous, c.getUser_id()).show();
                        }
                    });
                    btnEvenements.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            new EventsForm(theme, previous, c.getId(), c.getNomclub()).show();
                        }
                    });

                    X.add(sp);
                    X.add(sp1);
                    X1.add(sp2);
                    X1.add(sp3);
                    X1.add(iv);
                    X4.add(sp4);

                    X2.add(btnActivities);
                    X2.add(btnEvenements);

                    Y.add(X1);
                    Y.add(X);
                    Y.add(X4);
                    Y.add(X2);

////            Y.add(EventsClub);
////            Y.add(ActivitiesClub);
                    bigone.add(Y);
                    bigone.add("---------------------------------------------------------");
                }
            }

            refreshTheme();
        });

        add(search);
        for (Club c : clubss) {
            int score = 0;
            for (Activite a : act.ShowAllactivites(c.getUser_id())) {
                score += a.getVote();
            }
            System.out.println(score);
            Container Y = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container X = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container X1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container X2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container X4 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container reportContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            //Y.getStyle().setFgColor(5855577);
            SpanLabel sp = new SpanLabel();
            sp.setText("   Responsable : ");
            SpanLabel sp1 = new SpanLabel();
            sp1.setText(c.getResponsable());
            SpanLabel sp2 = new SpanLabel();
            sp2.setText("   Club :   ");
            SpanLabel sp3 = new SpanLabel();
            sp3.setText(c.getNomclub() + "   ");

            SpanLabel sp4 = new SpanLabel();
            sp4.setText("   Club Score:  " + score);

            iv = new ImageViewer();
            String urlx = url + c.getNom_image();

            img = URLImage.createToStorage(enc, urlx, urlx, URLImage.RESIZE_SCALE);
            iv.setImage(img.scaled(200, 200));
            Button btnReport = new Button("Report");

            btnReport.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new ReportForm(previous).show();
                }
            }
            );
            Button btnActivities = new Button("Activités");
            Button btnEvenements = new Button("Evenements");
            btnActivities.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new ActivitiesForm(theme, previous, c.getUser_id()).show();
                }
            });
            btnEvenements.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new EventsForm(theme, previous, c.getId(), c.getNomclub()).show();
                }
            });

            X.add(sp);
            X.add(sp1);
            X1.add(sp2);
            X1.add(sp3);
            X1.add(iv);
            X4.add(sp4);
            X2.add(btnActivities);
            X2.add(btnEvenements);

            Y.add(X1);
            Y.add(X);
            Y.add(X4);
            Y.add(X2);
            Y.add(btnReport);
//            Y.add(EventsClub);
//            Y.add(ActivitiesClub);
            bigone.add(Y);

            bigone.add("---------------------------------------------------------");
        }

        add(bigone);
    }
}
