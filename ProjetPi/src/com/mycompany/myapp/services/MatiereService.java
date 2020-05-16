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
 * @author rami2
 */
public class MatiereService 
{
    public ArrayList<Matiere> matieres;
    
    public static MatiereService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private MatiereService() 
    {
         req = new ConnectionRequest();
    }

    public static MatiereService getInstance() 
    {
        if (instance == null) 
        {
            instance = new MatiereService();
        }
        return instance;
    }
    
    public ArrayList<Matiere> parseMatieres(String jsonText)
    {
        try
        {
            matieres=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list)
            {
                String nom=obj.get("nom").toString();
                String id=obj.get("id").toString();
                Matiere M=new Matiere(id, nom);
                        
                matieres.add(M);      
            }  
        }
        catch (IOException ex) {}
        return matieres;   
    }
    
    public ArrayList<Matiere> getMatieres()
    {
        String url = Statics.BASE_URL+"notes/getMatieres";
                    
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                matieres = parseMatieres(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return matieres;   
    }
    
    public ArrayList<Matiere> getMatiereStatsMoyennes(int eleve_id)
    {
        String url = Statics.BASE_URL+"moyennes/getMatiereStatsMoyennes?eleve_id="+eleve_id;                    
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                matieres = parseMatieres(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return matieres;         
    }
    
    public ArrayList<Matiere> getMatiereStatsNotes(int eleve_id)
    {
        String url = Statics.BASE_URL+"Notes/getMatiereStatsNotes?eleve_id="+eleve_id;                    
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                matieres = parseMatieres(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return matieres;         
    }
}
