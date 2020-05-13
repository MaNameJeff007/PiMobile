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
import com.mycompany.myapp.entities.Commentaire;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceCommentaire {

    public ArrayList<Commentaire> commentaire;

    public static ServiceCommentaire instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceCommentaire() {
        req = new ConnectionRequest();
    }

    public static ServiceCommentaire getInstance() {
        if (instance == null) {
            instance = new ServiceCommentaire();
        }
        return instance;
    }

    public boolean addCommentaire(Commentaire c) {
        String url = Statics.BASE_URL + "forum/comm/add/" + c.getTexte() + "/" + c.getSujet_id() + "/" + c.getCreateur_id();
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

    public boolean deleteCommentaire(int i) {
        String url = Statics.BASE_URL + "forum/comm/delete/" + i;
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

    public boolean ModifCommentaire(int i, String t) {
        String url = Statics.BASE_URL + "forum/comm/modif/" + i + "/" + t;
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

    public ArrayList<Commentaire> parseCommentaire(String jsonText) throws ParseException {

        try {
            commentaire = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");
            for (Map<String, Object> obj : list) {
                Commentaire c = new Commentaire();
                Map<String, Object> s = (Map<String, Object>) obj.get("sujet");
                c.setCommentaire_id(((int) Float.parseFloat(obj.get("id").toString())));
                c.setCreateur_id(((int) Float.parseFloat(obj.get("createur").toString())));
                c.setTexte(obj.get("texte").toString());
                c.setSujet_id(((int) Float.parseFloat(s.get("id").toString())));
                c.setScore(((int) Float.parseFloat(obj.get("score").toString())));
                c.setDate(obj.get("date").toString());
                commentaire.add(c);
            }
        } catch (IOException ex) {

        }
        return commentaire;
    }

    public ArrayList<Commentaire> getCommentaire(int id) {
        String url = Statics.BASE_URL + "forum/comm/all";
        req.setUrl(url);
        ArrayList<Commentaire> comm = new ArrayList<Commentaire>();
        req.setPost(
                false);
        req.addResponseListener(
                new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt
                    ) {
                        try {
                            commentaire = parseCommentaire(new String(req.getResponseData()));
                            for (Commentaire obj : commentaire) {
                                if (obj.getSujet_id() == id) {
                                    Commentaire c = new Commentaire();
                                    c.setCommentaire_id(obj.getCommentaire_id());
                                    c.setSujet_id(id);
                                    c.setCreateur_id(obj.getCreateur_id());
                                    c.setTexte(obj.getTexte());
                                    c.setScore(obj.getScore());
                                    c.setDate(obj.getDate());
                                    comm.add(c);
                                }
                            }
                        } catch (ParseException ex) {

                        }
                        req.removeResponseListener(this);
                    }
                }
        );
        NetworkManager.getInstance()
                .addToQueueAndWait(req);
        return comm;
    }
}
