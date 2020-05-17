/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Activite;
import com.mycompany.myapp.entities.Club;
import com.mycompany.myapp.services.ServiceActivite;

/**
 *
 * @author Mohamed Turki
 */
public class ActivitiesForm extends Form {

    private ServiceActivite act = new ServiceActivite();
    private String url = "http://localhost/symfony/Ecole--Edtech1/web/images/";
    private ImageViewer ivUp;
    private ImageViewer ivDown;
    private Image UpImg;
    private Image DownImg;
    private EncodedImage enc;

    public ActivitiesForm(Resources theme, Form previous, int userId) {
        setTitle("Activities");
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ClubsForm(theme, previous).showBack());
        for (Activite c : act.ShowAllactivites(userId)) {
            Container Y = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container Y1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container XB = new Container(new BoxLayout(BoxLayout.X_AXIS));

            Container X = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container X1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container X2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container X3 = new Container(new BoxLayout(BoxLayout.X_AXIS));

            SpanLabel sp2 = new SpanLabel();
            sp2.setText("   Activite : ");
            SpanLabel sp3 = new SpanLabel();
            sp3.setText(c.getNomActivite());
            SpanLabel sp = new SpanLabel();
            sp.setText("   Responsable : ");
            SpanLabel sp1 = new SpanLabel();
            sp1.setText(c.getResponsable());
            SpanLabel sp4 = new SpanLabel();
            sp4.setText("  " + c.getVote());
            
            sp1.getSelectedStyle().setBgColor(0xff000);
            sp2.getSelectedStyle().setFgColor(ColorUtil.BLACK);
            sp3.getSelectedStyle().setFgColor(ColorUtil.BLACK);
            sp4.getSelectedStyle().setFgColor(ColorUtil.BLACK);

            ivUp = new ImageViewer();
            ivDown = new ImageViewer();
            UpImg = theme.getImage("upArrow.png");
            DownImg = theme.getImage("downArrow.png");
            ivUp.setImage(UpImg.scaled(150, 150));
            ivDown.setImage(DownImg.scaled(150, 150));

            ivUp.addPointerReleasedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    c.setVote(c.getVote() + 1);
                    sp4.setText("  " + c.getVote());
                    act.PlusForVote(c.getId());

                }
            });

            ivDown.addPointerReleasedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    c.setVote(c.getVote() - 1);
                    sp4.setText("  " + c.getVote());
                    act.MinusForVote(c.getId());

                }
            });

            Y1.add(ivUp);
            Y1.add(sp4);
            Y1.add(ivDown);

            X.add(sp);
            X.add(sp1);
            X1.add(sp2);
            X1.add(sp3);
            Y.add(X1);

            Y.add(X);
            Y.add(X2);
            XB.add(Y);
            XB.add(Y1);

            add(XB);
            add("--------------------------------------------------------------------");
        }

    }
}
