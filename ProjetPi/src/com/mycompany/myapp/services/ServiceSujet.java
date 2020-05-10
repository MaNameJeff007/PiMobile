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
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceSujet {

    public ArrayList<Sujet> sujet;

    public static ServiceSujet instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceSujet() {
        req = new ConnectionRequest();
    }

    public static ServiceSujet getInstance() {
        if (instance == null) {
            instance = new ServiceSujet();
        }
        return instance;
    }

    public boolean addSujet(Sujet s) {
        String url = Statics.BASE_URL + "forum/sujet/add/" + s.getTitre() + "/" + s.getDescription() + "/" + s.getCreateur_id();
        req.setUrl(url);
        System.out.println(url);
        System.out.println("1");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("2");
                resultOK = req.getResponseCode() == 200;
                System.out.println("3");
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteSujet(int i) {
        String url = Statics.BASE_URL + "forum/sujet/delete/" + i;
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Sujet> parseSujet(String jsonText) throws ParseException {

        try {
            sujet = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            // System.out.println("4");
            List<Map<String, Object>> list = null;
            // System.out.println("4.5");
            list = (List<Map<String, Object>>) tasksListJson.get("root");
            //System.out.println("5");
            for (Map<String, Object> obj : list) {
                // System.out.println("6");
                Sujet s = new Sujet();
                s.setSujet_id(((int) Float.parseFloat(obj.get("id").toString())));
                s.setCreateur_id(((int) Float.parseFloat(obj.get("createur").toString())));
                s.setDescription(obj.get("description").toString());
                s.setTitre(obj.get("titre").toString());
                s.setScore(((int) Float.parseFloat(obj.get("score").toString())));
                s.setDate(obj.get("date").toString());
                sujet.add(s);
            }

        } catch (IOException ex) {

        }
        return sujet;
    }

    public ArrayList<Sujet> getAllSujet() {
        String url = Statics.BASE_URL + "forum/sujet/all";
        req.setUrl(url);
        req.setPost(false);
        // System.out.println("1");

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    //    System.out.println("2");
                    sujet = parseSujet(new String(req.getResponseData()));
                    //      System.out.println("3");
                } catch (ParseException ex) {

                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return sujet;
    }

}
