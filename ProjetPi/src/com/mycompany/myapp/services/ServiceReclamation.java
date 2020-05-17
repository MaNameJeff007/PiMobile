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
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Selim Chikh Zaouali
 */
public class ServiceReclamation {
    public ArrayList<Reclamation> reclamation;

    public static ServiceReclamation instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceReclamation() {
        req = new ConnectionRequest();
    }

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }
    
    public boolean addReclamation(Reclamation r) {
        String url = Statics.BASE_URL + "recc/add/" + r.getNote() + "/" + r.getParent() + "/" + r.getDetails();
        req.setUrl(url);
        System.out.println(url);
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
    
        public ArrayList<Reclamation> parseReclamation(String jsonText) throws ParseException {

        try {
            reclamation = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");
            for (Map<String, Object> obj : list) {
                Reclamation r=new Reclamation();
                Map<String, Object> par = (Map<String, Object>) obj.get("parent");
                Map<String, Object> not = (Map<String, Object>) obj.get("note");
                r.setParent(((int) Float.parseFloat(par.get("id").toString())));
                r.setNote(((int) Float.parseFloat(not.get("id").toString())));
                r.setId(((int) Float.parseFloat(obj.get("id").toString())));
                r.setEtat(obj.get("etat").toString());
                r.setDate(obj.get("date").toString());
                r.setDetails(obj.get("details").toString());
                
                reclamation.add(r);
            }
        } catch (IOException ex) {

        }
        return reclamation;
    }
    
    public ArrayList<Reclamation> getAllReclamation(int i) {
        String url = Statics.BASE_URL + "recc/"+i;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    reclamation = parseReclamation(new String(req.getResponseData()));
                } catch (ParseException ex) {

                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamation;
    }
    
    public boolean deleteReclamation(int i) {
        String url = Statics.BASE_URL + "recc/delete/" + i;
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
    
}
