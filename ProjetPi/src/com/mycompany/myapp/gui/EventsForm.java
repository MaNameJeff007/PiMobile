/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Activite;
import com.mycompany.myapp.entities.Evenement;
import com.mycompany.myapp.services.ServiceActivite;
import com.mycompany.myapp.services.ServiceEvent;

/**
 *
 * @author Mohamed Turki
 */
public class EventsForm extends Form {

    private ServiceEvent act = new ServiceEvent();

    public EventsForm(Resources theme, Form previous, int clubId, String nomC) {
        System.out.println(act.ShowAllEvents(clubId, nomC));
        setTitle("Events");
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ClubsForm(theme, previous).showBack());
        for (Evenement c : act.ShowAllEvents(clubId, nomC)) {

           
            ShareButton sb = new ShareButton();
            sb.setText("Share Event!");
            sb.setTextToShare("check out our event");
 //String imageFile =   "path.png";
//sb.setImageToShare(imageFile, "image/png");
           

            Container Y = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container X = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container X1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container X2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container X3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            SpanLabel sp = new SpanLabel();
            SpanLabel sp1 = new SpanLabel();
            SpanLabel sp2 = new SpanLabel();
            SpanLabel sp3 = new SpanLabel();
            SpanLabel sp4 = new SpanLabel();
            SpanLabel sp5 = new SpanLabel();
            SpanLabel sp6 = new SpanLabel();
            SpanLabel sp7 = new SpanLabel();
            sp.setText("   Nom Club : ");
            sp1.setText(c.getNom_club());
            sp2.setText("   Nom Event : ");
            sp3.setText(c.getNom_evenement());

            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String dateDebut = simpleDateFormat.format(c.getHeure_debut());
            String dateFin = simpleDateFormat.format(c.getHeure_fin());

            sp4.setText("   Date Début : ");
            sp5.setText(dateDebut);
            sp6.setText("   Date Fin : ");
            sp7.setText(dateFin);
            X.add(sp);
            X.add(sp1);
            X1.add(sp2);
            X1.add(sp3);
            X2.add(sp4);
            X2.add(sp5);
            X3.add(sp6);
            X3.add(sp7);
            Y.add(X);
            Y.add(X1);
            Y.add(X2);
            Y.add(X3);
            Y.add(sb);
//            Y.add(EventsClub);
//            Y.add(ActivitiesClub);
            add(Y);
            add("--------------------------------------------------------------------");
        }
    }
}
