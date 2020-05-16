/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
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
import com.mycompany.myapp.entities.Note;
import com.mycompany.myapp.entities.User;
import static com.mycompany.myapp.gui.Login.currentUser;
import com.mycompany.myapp.services.MatiereService;
import com.mycompany.myapp.services.NoteService;
import java.util.ArrayList;

/**
 *
 * @author rami2
 */
public class NotesEleveForm extends Form{
    
    private Form current;
    public static int eleveNote;
    public static int matiereNote;
    public static String typeNote;
    public static String nomMatiereNote;

    public NotesEleveForm(User U) 
    {
        setTitle("Liste des notes de l'eleve: "+U.getPrenom());
        Form list = new Form("Notes:", BoxLayout.y());
        ArrayList<Note> arr = new ArrayList<>();
            
        for (int i = 0; i < NoteService.getInstance().getNotesEleve(U.getIdentifiant()).size(); i++) 
        {
            arr.add(NoteService.getInstance().getNotesEleve(U.getIdentifiant()).get(i));
        }

        for (Note us : arr) 
        {
            list.add(addItem(us));
        }
        
        Form stat_buttons= new Form("", BoxLayout.x());
        Label label1=new Label("Type");
        Label label2=new Label("Matiere");
        ComboBox<String> types = new ComboBox<>(
         "CC", "Devoir de controle", "Devoir de synthese"
        );
        
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
                ListElevesParentForm.stats=1;
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
                
                
                eleveNote=U.getIdentifiant();
                matiereNote=Integer.valueOf(mt);
                typeNote=types.getSelectedItem();
                nomMatiereNote=matieres.getSelectedItem();
                
                ChartDemosForm demos = new ChartDemosForm();
                current= demos;
                demos.show(); 
            }
        });
        
        stat_buttons.addAll(label1, types, label2, matieres);
             
        addAll(list, stat_buttons, bout_stat);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ListElevesParentForm().show());       
    }
    
    private Container addItem(Note n) 
    {
        Form current = new Form("Notes:", BoxLayout.y());
        SpanLabel  sp = new SpanLabel();
        Container cnt1 = new Container(BoxLayout.y());
        cnt1.getStyle().setBorder(Border.createLineBorder(1));
       
        sp.setText("Trimestre :    "+n.getTrimestre()+"    Type : "+n.getType()+"     Matière :    "+n.getMatierenom()+"    Valeur :    "+n.getValeur());
                
        cnt1.addAll(sp);
        Container cnt2 = new Container(BoxLayout.x());
        cnt2.add(cnt1);
    
        return cnt2;
    }
}  
    

