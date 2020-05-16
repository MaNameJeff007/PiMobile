/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.Moyenne;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.MatiereService;
import com.mycompany.myapp.services.MoyenneService;
import java.util.ArrayList;


/**
 *
 * @author rami2
 */
public class MoyennesEleveForm extends Form 
{
    private Form current;
    public static int eleveMoyenne;
    public static int matiereMoyenne;
    public static String nomMatiereMoyenne;
    
    public MoyennesEleveForm(User U)
    {
        setTitle("Liste des moyennes:");
        Form list = new Form("Moyennes:", BoxLayout.y());
        ArrayList<Moyenne> arr = new ArrayList<>();
        
        for (int i = 0; i < MoyenneService.getInstance().getMoyennesEleve(U.getIdentifiant()).size(); i++) 
        {
            arr.add(MoyenneService.getInstance().getMoyennes(1).get(i));
        }
        
        for (Moyenne us : arr) 
        {
            list.add(addItem(us));
        }
        
        Form stat_buttons= new Form("", BoxLayout.x());
        Label label2=new Label("Matiere");
        
        ComboBox<String> matieres = new ComboBox<>();
        
        for(int i=0; i<MatiereService.getInstance().getMatieres().size();i++)
        {
            String nom=MatiereService.getInstance().getMatieres().get(i).getId()+"-"+MatiereService.getInstance().getMatieres().get(i).getNom();
            matieres.addItem(nom);       
        }
        
        Button bout_stat=new Button("Statistiques");
        
        bout_stat.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) 
            {
                String mt=matieres.getSelectedItem().substring(0,2);

    
                        
                        
                Boolean flag = Character.isDigit(mt.charAt(1));
                if(flag) 
                {
                    mt=matieres.getSelectedItem().substring(0,2);
                }
                else 
                {
                   mt=matieres.getSelectedItem().substring(0,1);
                }
                
                
                eleveMoyenne=U.getIdentifiant();
                matiereMoyenne=Integer.valueOf(mt);
                nomMatiereMoyenne=matieres.getSelectedItem();
                ChartDemosForm demos = new ChartDemosForm();
                current= demos;
                demos.show(); 
            }
        });
        
        stat_buttons.addAll(label2, matieres);
             
        addAll(list, stat_buttons, bout_stat);
        
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ListElevesParentForm().show());        
    }
    
    private Container addItem(Moyenne M) 
    {
        Form current = new Form("Notes:", BoxLayout.y());
        SpanLabel  sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
       
        sp.setText(M.toString());
           
        Container btns = new Container(BoxLayout.x());
        cnt1.addAll(sp);
        Container cnt2 = new Container(BoxLayout.x());
        cnt2.add(cnt1);
          
        return cnt2;
    }
}
