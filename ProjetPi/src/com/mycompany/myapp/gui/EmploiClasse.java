/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template EmploiClasse, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.Table;
import com.codename1.ui.table.TableModel;
import com.codename1.ui.tree.Tree;
import com.codename1.ui.tree.TreeModel;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.entities.Seance;
import com.mycompany.myapp.services.ServiceSeance;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 *
 * @author dell
 */
public class EmploiClasse extends Form {

    Form current;

    private void createEntry(ArrayList<Seance> vect, Seance file) {
        //Label fileField = new Label(EmploiClasse.getHdeb());
        // Button delete = new Button();
        // Button view = new Button();
        // FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
        // FontImage.setMaterialIcon(view, FontImage.MATERIAL_OPEN_IN_NEW);
        // Container content = BorderLayout.center(fileField);

        // Util.register("Seance", Seance.class);
        vect.add(file);
        Storage.getInstance().writeObject("seances", vect);
        /*try (InputStream is = Storage.getInstance().writeObject(EmploiClasse, hi)) {
            String s = Util.readToString(is, "UTF-8");
            //Dialog.show(EmploiClasse, s, "OK", null);
        } catch (IOException err) {
            System.out.println(err);
        }
         */
        // add(content);
    }

    private void showEntry() {

        ArrayList<Seance> vect = (ArrayList<Seance>) Storage.getInstance().readObject("seances");
          int i=1;
        for (Seance s : vect) {
            Util.register("Seance", Seance.class);
            /* Label hdeb = new Label(s.getHdeb()); 
           Label hfin = new Label(s.getHdeb()); 
           Label classe = new Label(s.getClasse().getLibelle()); */
            // SpanLabel sp = new SpanLabel();
            // sp.setText(s.getHdeb() + " fin " + s.getClasse().getLibelle());
            TableModel model;
            Container cnt1 = new Container(BoxLayout.y());
            // cnt1.add(sp);
            //add(cnt1);

            model = new DefaultTableModel(new String[]{"Jour", "Début", "Matiere", "Salle"}, new Object[][]{
                {s.getJour(), s.getHdeb(), s.getMatiere().getNom(), s.getSalle().getLibelle()},}) {
                public boolean isCellEditable(int row, int col) {
                    return col != 0;

                }
            };
            Table table = new Table(model);
            Label seance = new Label("Séance "+i+" : ");
            addAll(seance, table);
            i++;
        }

    }

    public EmploiClasse(Form previous, Classe t) {
        ArrayList<Seance> vect = new ArrayList<Seance>();
        for (Seance s : ServiceSeance.getInstance().getSeancesClasse(t)) {
            vect.add(s);

            //createEntry(vect,s);
        }
        Storage.getInstance().writeObject("seances", vect);
        showEntry();
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new AfficherClasses(current).show());

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

}
