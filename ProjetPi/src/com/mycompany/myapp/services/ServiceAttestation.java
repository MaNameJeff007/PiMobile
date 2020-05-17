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
import com.mycompany.myapp.entities.Attestation;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Selim Chikh Zaouali
 */
public class ServiceAttestation {
    
    public ArrayList<Attestation> attestation;

    public static ServiceAttestation instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceAttestation() {
        req = new ConnectionRequest();
    }

    public static ServiceAttestation getInstance() {
        if (instance == null) {
            instance = new ServiceAttestation();
        }
        return instance;
    }
    
    public boolean addAttestation(Attestation a) {
        String url = Statics.BASE_URL + "attt/add/" + a.getParent() + "/" + a.getEnfant();
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
    
        public ArrayList<Attestation> parseAttestation(String jsonText) throws ParseException {

        try {
            attestation = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");
            for (Map<String, Object> obj : list) {
                Attestation a=new Attestation();
                Map<String, Object> par = (Map<String, Object>) obj.get("parent");
                a.setParent(((int) Float.parseFloat(par.get("id").toString())));
                a.setId(((int) Float.parseFloat(obj.get("id").toString())));
                a.setEnfant(obj.get("enfant").toString());
                a.setEtat(obj.get("etat").toString());
                a.setDate(obj.get("date").toString());
                
                attestation.add(a);
            }
        } catch (IOException ex) {

        }
        return attestation;
    }
    
    public ArrayList<Attestation> getAllAttestation(int i) {
        String url = Statics.BASE_URL + "attt/"+i;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    attestation = parseAttestation(new String(req.getResponseData()));
                } catch (ParseException ex) {

                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return attestation;
    }
    
    public boolean deleteAttestation(int i) {
        String url = Statics.BASE_URL + "attt/delete/" + i;
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
