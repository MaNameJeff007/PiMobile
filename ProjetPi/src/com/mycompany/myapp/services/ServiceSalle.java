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
import com.mycompany.myapp.entities.Salle;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dell
 */
public class ServiceSalle {
      private ConnectionRequest req;
    public static ServiceSalle instance = null;
    public boolean resultOK;
      public ArrayList<Salle> classes;
    
       private ServiceSalle() {
         req = new ConnectionRequest();
    }
       
     public static ServiceSalle getInstance() {
        if (instance == null) {
            instance = new ServiceSalle();
        }
        return instance;
    }
 
     public boolean addSalle(Salle t) { 
        String url = Statics.BASE_URL + "Salle/new?libelle="+t.getLibelle()+"";

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
     
      public boolean updateSalle(Salle t) { 
        String url = Statics.BASE_URL + "editSalle/"+t.getId()+"?libelle="+t.getLibelle()+"";

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
     
     public boolean deleteSalle(Salle t) { //?name=" + t.getName() + "&status=" + t.getStatus()
        String url = Statics.BASE_URL + "Salles/"+t.getId()+"";

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
     
     public ArrayList<Salle> parseTasks(String jsonText){
        try {
            classes =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Salle t = new Salle();
               //float id = Float.parseFloat(obj.get("id").toString());
                t.setId(obj.get("id").toString());
                t.setLibelle(obj.get("libelle").toString());
                
                classes.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return classes;
    }
    
    public ArrayList<Salle> getAllSalles(){
        String url = Statics.BASE_URL+ "allSalles/all";
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
