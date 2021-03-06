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
import com.mycompany.myapp.entities.Commentaire;
import com.mycompany.myapp.entities.Signaler;
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
public class ServiceSignaler {

    public ArrayList<Signaler> signaler;

    public static ServiceSignaler instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceSignaler() {
        req = new ConnectionRequest();
    }

    public static ServiceSignaler getInstance() {
        if (instance == null) {
            instance = new ServiceSignaler();
        }
        return instance;
    }

    public boolean addSignaleComm(Commentaire c) {
        System.out.println(new Login().getcurrentUser().getIdentifiant());
        String url = Statics.BASE_URL + "forum/report/add/comm/" + c.getCommentaire_id();
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
    
    public boolean addSignaleSujet(Sujet s) {
        System.out.println(new Login().getcurrentUser().getIdentifiant());
        String url = Statics.BASE_URL + "forum/report/add/sujet/" + s.getSujet_id();
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
