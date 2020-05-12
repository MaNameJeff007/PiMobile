/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dell
 */
public class ServiceClasse {

    private ConnectionRequest req;
    public static ServiceClasse instance = null;
    public boolean resultOK;
    public ArrayList<Classe> classes;

    private ServiceClasse() {
        req = new ConnectionRequest();
    }

    public static ServiceClasse getInstance() {
        if (instance == null) {
            instance = new ServiceClasse();
        }
        return instance;
    }

    public boolean updateClasse(Classe t) {
        String url = Statics.BASE_URL + "editClasse/" + t.getId() + "?libelle=" + t.getLibelle() + "&capacite=" + t.getCapacite() + "&niveau=" + t.getNiveau() + "";

        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteClasse(Classe t) {
        String url = Statics.BASE_URL + "Classe/" + t.getId() + "";

        req.setPost(false);

        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Classe> getSelectedClasse(User t) {
        String url = Statics.BASE_URL + "findClasseE/" + t.getIdentifiant() + "";

        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                classes = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }

    public boolean addTask(Classe t) { //?name=" + t.getName() + "&status=" + t.getStatus()
        String url = Statics.BASE_URL + "classApi/new";

        req.setPost(false);
        req.addArgument("niveau", String.valueOf(t.getNiveau()));
        req.addArgument("libelle", t.getLibelle());
        req.addArgument("capacite", String.valueOf(t.getCapacite()));

        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Classe> parseTasks(String jsonText) {
        try {
            classes = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Classe t = new Classe();
                //float id = Float.parseFloat(obj.get("id").toString());
                t.setLibelle(obj.get("libelle").toString());
                t.setId(obj.get("id").toString());

                t.setCapacite(((int) Float.parseFloat(obj.get("capacite").toString())));
                t.setNiveau(((int) Float.parseFloat(obj.get("niveau").toString())));
                classes.add(t);
            }

        } catch (IOException ex) {

        }
        return classes;
    }

    public ArrayList<Classe> getAllTasks() {
        String url = Statics.BASE_URL + "classA/allp";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                classes = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }
}
