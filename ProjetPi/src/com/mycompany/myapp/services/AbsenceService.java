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
import com.mycompany.myapp.entities.Absence;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author rami2
 */
public class AbsenceService 
{
    public ArrayList<Absence> absences;
   
    public static AbsenceService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private AbsenceService() 
    {
         req = new ConnectionRequest();
    }

    public static AbsenceService getInstance() 
    {
        if (instance == null) 
        {
            instance = new AbsenceService();
        }
        return instance;
    }
    
    public boolean verifierAbsence(Absence A)
    {
        String url = Statics.BASE_URL + "absences/verifier?heuredebut="+A.getHeureDebut()+"&heurefin="+A.getHeureFin()+"&date="+A.getDateAbsence()+"&eleve="+A.getEleve_id();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                absences= parseAbsences(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        if(absences.isEmpty())
            return true;
                    
        else
            return false;
    }
    
    public boolean addAbsence(Absence A)
    {
        String url = Statics.BASE_URL + "absences/ajouter?justification="+A.getJustification()+"&heuredebut="+A.getHeureDebut()+"&heurefin="+A.getHeureFin()+"&etat="+A.getEtat()+"&date="+A.getDateAbsence()+"&enseignant="+A.getEnseignant_id()+"&eleve="+A.getEleve_id();
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
    
    public boolean modifierAbsence(Absence A)
    {
        String url = Statics.BASE_URL + "absences/modifier?etat="+A.getEtat()+"&justification="+A.getJustification()+"&id="+A.getId();
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
    
    public boolean supprimerAbsence(Absence A)
    {
        String url = Statics.BASE_URL + "absences/supprimer?id="+A.getId();
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
    
    public ArrayList<Absence> parseAbsences(String jsonText)
    {
     try 
     {
        absences=new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
        List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
        for(Map<String,Object> obj : list)
        {
          Absence A = new Absence();
          float id = Float.parseFloat(obj.get("id").toString());
          String nom=String.valueOf(obj.get("prenom"))+" "+String.valueOf(obj.get("nom"));
          
          A.setId((int)id);   
          A.setEleve_id(String.valueOf(obj.get("eleve_id")));
          A.setEleve_nom(nom);
          A.setEnseignant_id(String.valueOf(obj.get("enseignant_id")));
          A.setHeureDebut(Integer.valueOf(obj.get("heuredebut").toString()));
          A.setHeureFin(Integer.valueOf(obj.get("heurefin").toString()));
          A.setDateAbsence(String.valueOf(obj.get("dateabs")));
          String etat=String.valueOf(obj.get("etat"));
          A.setJustification(String.valueOf(obj.get("justification")));
          int e=1;
          if(etat.equals("0"))
              e=0;
          
          A.setEtat(e);
          
          absences.add(A);
        }
            
      } 
    catch (IOException ex) {}
    return absences;
   }
    
    public ArrayList<Absence> getAbsences(int enseignant_id)
    {
        String url = Statics.BASE_URL+"/absences/all/"+enseignant_id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                absences= parseAbsences(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return absences;
    }
    
    public ArrayList<Absence> getAbsencesEleve(int eleve_id)
    {
        String url = Statics.BASE_URL+"/absences/absenceseleve/"+eleve_id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                absences= parseAbsences(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return absences;
    }  
}
