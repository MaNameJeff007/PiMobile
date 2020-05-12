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
import com.mycompany.myapp.entities.Bulletin;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dell
 */
public class ServiceBulletin {

    private ConnectionRequest req;
    public static ServiceBulletin instance = null;
    public boolean resultOK;
    public ArrayList<Bulletin> classes;
    public ArrayList myList;

    private ServiceBulletin() {
        req = new ConnectionRequest();
    }

    public static ServiceBulletin getInstance() {
        if (instance == null) {
            instance = new ServiceBulletin();
        }
        return instance;
    }

    public ArrayList<Bulletin> parseDonnees(String jsonText) {
        classes = new ArrayList();
        try {

            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {

                //float id = Float.parseFloat(obj.get("id").toString());
                Bulletin t = new Bulletin();
                t.setMoyenne((Float) Float.parseFloat(obj.get("pourcentage").toString()));
                classes.add(t);
                /* t.setLibelle(obj.get("libelle").toString());
                t.setId(obj.get("id").toString());
               pourcentage
               t.setCapacite(((int)Float.parseFloat(obj.get("capacite").toString())));
                  t.setNiveau(((int)Float.parseFloat(obj.get("niveau").toString())));*/
                //myList.add(obj.get("pourcentage").toString());
            }

        } catch (IOException ex) {

        }
        return classes;
    }

    public ArrayList<Bulletin> getStatMG1(String classe) {
        String url = Statics.BASE_URL + "MoyGC1/" + classe + "";
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                classes = parseDonnees(new String(req.getResponseData()));

                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }

    public ArrayList<Bulletin> getStatMG2(String classe) {
        String url = Statics.BASE_URL + "MoyGC2/" + classe + "";
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                classes = parseDonnees(new String(req.getResponseData()));

                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }

    public ArrayList<Bulletin> getStatMG3(String classe) {
        String url = Statics.BASE_URL + "MoyGC3/" + classe + "";
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                classes = parseDonnees(new String(req.getResponseData()));

                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }

    public ArrayList<Bulletin> getStatMG4(String classe) {
        String url = Statics.BASE_URL + "MoyGC4/" + classe + "";
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                classes = parseDonnees(new String(req.getResponseData()));

                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }

    public ArrayList<Bulletin> getStatMT1(String classe, int trim) {
        String url = Statics.BASE_URL + "MoyGT1/" + classe + "/" + trim + "";
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                classes = parseDonnees(new String(req.getResponseData()));

                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }

    public ArrayList<Bulletin> getStatMT2(String classe, int trim) {
        String url = Statics.BASE_URL + "MoyGT/" + classe + "/" + trim + "";
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                classes = parseDonnees(new String(req.getResponseData()));

                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }

    public ArrayList<Bulletin> getStatMT3(String classe, int trim) {
        String url = Statics.BASE_URL + "MoyGT3/" + classe + "/" + trim + "";
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                classes = parseDonnees(new String(req.getResponseData()));

                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }
}
