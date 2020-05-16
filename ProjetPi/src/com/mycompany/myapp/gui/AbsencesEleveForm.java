/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.Absence;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.AbsenceService;
import java.util.ArrayList;

/**
 *
 * @author rami2
 */
public class AbsencesEleveForm extends Form 
{
    public AbsencesEleveForm(User U)
    {
        setTitle("Liste des absences de l'eleve: "+U.getPrenom());
        Form list = new Form("Absence:", BoxLayout.y());
        ArrayList<Absence> arr = new ArrayList<>();
       
        
        for (int i = 0; i < AbsenceService.getInstance().getAbsencesEleve(U.getIdentifiant()).size(); i++) 
        {
            arr.add(AbsenceService.getInstance().getAbsencesEleve(U.getIdentifiant()).get(i));
        }

        for (Absence us : arr) 
        {
            list.add(addItem(us));
        }
        
        //list.show();
        System.out.println(arr);
        addAll(list);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ListElevesParentForm().show());
    }
    
    private Container addItem(Absence S) 
    {
        Form current = new Form("Sanctions:", BoxLayout.y());
        SpanLabel  sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
       
        sp.setText("Justification : "+S.getJustification()+" Date : "+S.getDateAbsence()+" Heure Debut: "+S.getHeureDebut()+" Heure Fin: "+S.getHeureFin()+" Eleve : "+S.getEleve_nom());
        
        cnt1.addAll(sp);
        Container cnt2 = new Container(BoxLayout.x());
        cnt2.add(cnt1);
              
        return cnt2;
    }          
}

