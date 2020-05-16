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
import com.mycompany.myapp.entities.Sanction;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rami2
 */
public class SanctionService 
{
    public ArrayList<Sanction> sanctions;

    
    public static SanctionService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private SanctionService() 
    {
         req = new ConnectionRequest();
    }

    public static SanctionService getInstance() 
    {
        if (instance == null) 
        {
            instance = new SanctionService();
        }
        return instance;
    }
    
    public boolean addSanction(Sanction S)
    {
        String url = Statics.BASE_URL + "sanctions/ajouter?punition="+S.getPunition()+"&raison="+S.getRaisonSanction()+"&etat="+S.getEtat()+"&date="+S.getDateSanction()+"&enseignant="+S.getEnseignant_id()+"&eleve="+S.getEleve_id();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;        
    }
    
    
    public boolean modifierSanction(Sanction S)
    {
        String url = Statics.BASE_URL + "sanctions/modifier?punition="+S.getPunition()+"&justification="+S.getRaisonSanction()+"&id="+S.getId();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;        
    }
    
    public boolean supprimerSanction(Sanction S)
    {
        String url = Statics.BASE_URL + "sanctions/supprimer?id="+S.getId();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;        
    }
    
    
    public ArrayList<Sanction> parseSanctions(String jsonText)
    {
     try 
     {
        sanctions=new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
        List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
        for(Map<String,Object> obj : list)
        {
          Sanction S = new Sanction();
          float id = Float.parseFloat(obj.get("id").toString());
          String nom=String.valueOf(obj.get("prenom"))+" "+String.valueOf(obj.get("nom"));
          
          S.setId((int)id);   
          S.setEleve_id(String.valueOf(obj.get("eleve_id")));
          S.setElevenom(nom);
          S.setEnseignant_id(String.valueOf(obj.get("enseignant_id")));
          S.setPunition(String.valueOf(obj.get("punition")));
          S.setRaisonSanction(String.valueOf(obj.get("raisonsanction")));
          String etat=String.valueOf(obj.get("etat"));
          S.setDateSanction(String.valueOf(obj.get("date_sanction")));
          int e=1;
          if(etat.equals("0"))
              e=0;
          
          S.setEtat(e);
          
          sanctions.add(S);
        }
            
      } 
    catch (IOException ex) {}
    return sanctions;
   }
    
    
    public ArrayList<Sanction> getSanctions(int enseignant_id)
    {
        String url = Statics.BASE_URL+"sanctions/all/"+enseignant_id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                sanctions = parseSanctions(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return sanctions;
    }
    
    public ArrayList<Sanction> getSanctionsEleve(int eleve_id)
    {
        String url = Statics.BASE_URL+"sanctions/sanctionseleve/"+eleve_id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                sanctions = parseSanctions(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return sanctions;
    }  
}
