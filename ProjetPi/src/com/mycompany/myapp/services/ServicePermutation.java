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
import com.mycompany.myapp.entities.Permutation;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Selim Chikh Zaouali
 */
public class ServicePermutation {

    public ArrayList<Permutation> permutation;

    public static ServicePermutation instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServicePermutation() {
        req = new ConnectionRequest();
    }

    public static ServicePermutation getInstance() {
        if (instance == null) {
            instance = new ServicePermutation();
        }
        return instance;
    }

    public boolean addPermutation(Permutation p) {
        String url = Statics.BASE_URL + "permm/add/" + p.getClasse_s() + "/" + p.getRaison() + "/" + p.getEleve_id() + "/" + p.getParent() + "/" + p.getEnfant();
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

    public ArrayList<Permutation> parsePermutation(String jsonText) throws ParseException {

        try {
            permutation = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");
            for (Map<String, Object> obj : list) {
                Permutation p = new Permutation();
                Map<String, Object> par = (Map<String, Object>) obj.get("parent");
                Map<String, Object> ele = (Map<String, Object>) obj.get("eleve");
                p.setParent(((int) Float.parseFloat(par.get("id").toString())));
                p.setEleve_id(((int) Float.parseFloat(ele.get("id").toString())));
                p.setId(((int) Float.parseFloat(obj.get("id").toString())));
                p.setEnfant(obj.get("enfant").toString());
                p.setEtat(obj.get("etat").toString());
                p.setDate(obj.get("date").toString());
                p.setRaison(obj.get("raison").toString());
                p.setClasse_s(obj.get("classeS").toString());

                permutation.add(p);
            }
        } catch (IOException ex) {

        }
        return permutation;
    }

    public ArrayList<Permutation> getAllPermutation(int i) {
        String url = Statics.BASE_URL + "permm/" + i;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    permutation = parsePermutation(new String(req.getResponseData()));
                } catch (ParseException ex) {

                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return permutation;
    }

    public boolean deletePermutation(int i) {
        String url = Statics.BASE_URL + "permm/delete/" + i;
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
