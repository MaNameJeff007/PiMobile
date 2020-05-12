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

import com.mycompany.myapp.entities.Coeff;
import com.mycompany.myapp.entities.Matiere;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author dell
 */
public class ServiceCoeff {
   private ConnectionRequest req;
    public static ServiceCoeff instance = null;
    public boolean resultOK;
      public ArrayList<Coeff> classes;
    
       private ServiceCoeff() {
         req = new ConnectionRequest();
    }
       
     public static ServiceCoeff getInstance() {
        if (instance == null) {
            instance = new ServiceCoeff();
        }
        return instance;
    } 
  
     public boolean addCoeff(Coeff t) { 
        String url = Statics.BASE_URL + "allCoeff/new?niveau="+t.getNiveau().getLibelle()+"&matiere="+t.getMatiere().getNom()+"&valeur="+t.getValeur()+"";

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
     
     public boolean updateCoeff(Coeff t) { 
        String url = Statics.BASE_URL + "editCoeff/"+t.getId()+"?niveau="+t.getNiveau().getLibelle()+"&matiere="+t.getMatiere().getNom()+"&valeur="+t.getValeur()+"";

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
    
     public ArrayList<Coeff> getAllCoeff(){
        String url = Statics.BASE_URL+ "allCoeff/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                classes = parseCoeff(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }  
     
      public boolean deleteCoeff(Coeff t) { 
        String url = Statics.BASE_URL + "Coeff/"+t.getId()+"";

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
      
  public ArrayList<Coeff> parseCoeff(String jsonText){
        try {
            classes =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                
                Coeff t = new Coeff();
                Classe c = new Classe();
                Matiere m = new Matiere();
                
                JSONObject obj5;
                try {
                    obj5 = new JSONObject(obj.get("matiere").toString());
                    m.setNom(obj5.getString("nom"));
                    // System.out.println(obj5.getString("nom"));
                } catch (JSONException ex) {
                      System.out.println(ex);
                }
                
                
                 try {
                     JSONObject obj2 = new JSONObject(obj.get("niveau").toString());
                    
                     String libelle = obj2.getString("libelle");
                     System.out.println(obj2.getString("niveau"));
                      c.setLibelle(libelle);
                      c.setNiveau(((int) Float.parseFloat(obj2.getString("niveau"))));
                   
                   
                } catch (JSONException ex) {
                     System.out.println(ex);
                }
                 
              
               t.setId(((int)Float.parseFloat(obj.get("id").toString())));
               t.setValeur(((int)Float.parseFloat(obj.get("valeur").toString())));
               t.setNiveau(c);
               t.setMatiere(m);
              
                classes.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return classes;
    }   
     
}
