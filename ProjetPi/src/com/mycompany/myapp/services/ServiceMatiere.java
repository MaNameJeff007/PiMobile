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
import com.mycompany.myapp.entities.Matiere;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dell
 */
public class ServiceMatiere {
    private ConnectionRequest req;
    public static ServiceMatiere instance = null;
    public boolean resultOK;
      public ArrayList<Matiere> classes;
    
       private ServiceMatiere() {
         req = new ConnectionRequest();
    }
       
     public static ServiceMatiere getInstance() {
        if (instance == null) {
            instance = new ServiceMatiere();
        }
        return instance;
    } 
     
     
     public boolean deleteMatiere(Matiere t) { 
        String url = Statics.BASE_URL + "Matiere/"+t.getId()+"";

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
  
     public ArrayList<Matiere> getSelectedMatiere(String name) {
        String url = Statics.BASE_URL + "findMatiere/"+name+"";
     
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                classes = parseMatiere(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }
     
     public boolean addMatiere(Matiere t) { 
        String url = Statics.BASE_URL + "newMatiere?nom="+t.getNom()+"&nbH="+t.getNbH()+"";

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
     
     public boolean updateMatiere(Matiere t) { 
        String url = Statics.BASE_URL + "editMatiere/"+t.getId()+"?nom="+t.getNom()+"&nbH="+t.getNbH()+"";

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
     
 public ArrayList<Matiere> parseMatiere(String jsonText){
        try {
            classes =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Matiere t = new Matiere();
              // float id = Float.parseFloat(obj.get("id").toString());
              t.setId(obj.get("id").toString());
                t.setNom(obj.get("nom").toString());
                t.setNbH(((int)Float.parseFloat(obj.get("nbH").toString())));
               
                classes.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return classes;
    }
    
    public ArrayList<Matiere> getAllMatieres(){
        String url = Statics.BASE_URL+ "allMatiere/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                classes = parseMatiere(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }      
     
}
