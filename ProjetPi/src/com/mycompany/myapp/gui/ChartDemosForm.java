/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp.gui;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;




/**
 *
 * @author shannah
 */
public class ChartDemosForm extends Form 
{  
    List formMenu;
    
    class ListOption {
        Class<AbstractDemoChart> chartClass;
        String name;
        
        ListOption(Class cls, String name){
            this.chartClass = cls;
            this.name = name;
        }
        
        public String toString(){
            return this.name;
        }
    }
    
    ListOption[] options = new ListOption[]{new ListOption(NotesChart.class, "Notes")};

        
    //    new ListOption(NotesChart.class, "Notes"),
     //   new ListOption(MoyennesChart.class, "Moyennes"),
    //};
    
    public ChartDemosForm()
    {
        super("Statistiques");
         
        if(ListElevesParentForm.stats==1)
        {
          options[0]=new ListOption(NotesChart.class, "Notes");  
        }
        else if(ListElevesParentForm.stats==0)
        {
          options[0]=new ListOption(MoyennesChart.class, "Moyennes");
        }
        
        
        formMenu = new List();
        for ( int i=0; i<options.length; i++){
            formMenu.addItem(options[i]);
        }
        
        formMenu.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent evt) 
            {
                int sel = formMenu.getCurrentSelected();
                ListOption opt = options[sel];
                Class cls = opt.chartClass;
                if ( ChartsInBoxLayout.class.equals(cls) )
                {
                    Form f = new ChartsInBoxLayout().getForm();
                    Command cmd = new Command("Menu"){

                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            ChartDemosForm.this.showBack();
                        }
                        
                    };
                    f.setBackCommand(cmd);
                    
                    f.getStyle().setBgColor(0x0);
                    f.getStyle().setBgTransparency(0xff);
                    int numComponents = f.getComponentCount();
                    for (int i=0; i<numComponents; i++) {
                        f.getComponentAt(i).getStyle().setBgColor(0x0);
                        f.getComponentAt(i).getStyle().setBgTransparency(0xff);
                    }
                    
                    f.show();
                    return;
                }
                try {
                    AbstractDemoChart demo = (AbstractDemoChart)cls.newInstance();
                   
                    Form intent = demo.execute();
                    if ( "".equals(intent.getTitle())){
                        intent.setTitle(demo.getName());
                    }
                    Command cmd = new Command("Menu"){

                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            ChartDemosForm.this.showBack();
                        }
                        
                    };
                    intent.setBackCommand(cmd);
                    intent.getStyle().setBgColor(0x0);
                    intent.getStyle().setBgTransparency(0xff);
                    int numComponents = intent.getComponentCount();
                    for (int i=0; i<numComponents; i++) {
                        intent.getComponentAt(i).getStyle().setBgColor(0x0);
                        intent.getComponentAt(i).getStyle().setBgTransparency(0xff);
                    }
                    intent.show();
                } catch (InstantiationException ex) {
                    Log.e(ex);
                } catch (IllegalAccessException ex) {
                    Log.e(ex);
                }
            }
            
        });
        
        setLayout(new BorderLayout());
        addComponent(BorderLayout.CENTER, formMenu);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ListElevesParentForm().show());      
    }
    
    
    
}
