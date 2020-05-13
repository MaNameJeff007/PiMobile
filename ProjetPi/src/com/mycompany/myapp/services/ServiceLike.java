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
import com.mycompany.myapp.entities.Likes;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.gui.Login;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceLike {

    public ArrayList<Likes> Likes;

    public static ServiceLike instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceLike() {
        req = new ConnectionRequest();
    }

    public static ServiceLike getInstance() {
        if (instance == null) {
            instance = new ServiceLike();
        }
        return instance;
    }

    public boolean addLike(Sujet s) {
        System.out.println(new Login().getcurrentUser().getIdentifiant());
        String url = Statics.BASE_URL + "forum/like/add/" + s.getSujet_id() + "/" + new Login().getcurrentUser().getIdentifiant();
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

    public boolean addLikeComm(Commentaire c) {
        System.out.println(new Login().getcurrentUser().getIdentifiant());
        String url = Statics.BASE_URL + "forum/like/add/comm/" + c.getCommentaire_id() + "/" + new Login().getcurrentUser().getIdentifiant();
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

    public boolean deleteLike(int i) {
        String url = Statics.BASE_URL + "forum/like/delete/" + i;
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
    public boolean deleteCommLike(int i) {
        String url = Statics.BASE_URL + "forum/like/comm/delete/" + i;
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

    public ArrayList<Likes> parseLikes(String jsonText) throws ParseException {

        try {
            Likes = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");
            for (Map<String, Object> obj : list) {
                Likes l = new Likes();
                l.setLike_id(((int) Float.parseFloat(obj.get("id").toString())));
                Map<String, Object> s = null;
                l.setType(obj.get("type").toString());
                if (l.getType().equals("sujet")) {
                    s = (Map<String, Object>) obj.get("sujet");
                    l.setSujet_id(((int) Float.parseFloat(s.get("id").toString())));
                } else {
                    s = (Map<String, Object>) obj.get("commentaire");
                    l.setCommentaire_id(((int) Float.parseFloat(s.get("id").toString())));
                }
                l.setCreateur_id((int) Float.parseFloat(s.get("createur").toString()) + 1);
                Likes.add(l);
            }
        } catch (IOException ex) {

        }
        return Likes;
    }

    public ArrayList<Likes> getAllLikes() {
        String url = Statics.BASE_URL + "forum/like/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    Likes = parseLikes(new String(req.getResponseData()));
                } catch (ParseException ex) {

                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Likes;
    }
}
