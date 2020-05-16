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
import com.mycompany.myapp.entities.Note;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rami2
 */
public class NoteService 
{
    public ArrayList<Note> notes;
    public ArrayList<Matiere> matieres;
    
    public static NoteService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private NoteService() 
    {
         req = new ConnectionRequest();
    }

    public static NoteService getInstance() 
    {
        if (instance == null) 
        {
            instance = new NoteService();
        }
        return instance;
    }
    
    public boolean modifierNote(Note N)
    {
        String url = Statics.BASE_URL + "notes/modifierNote?id="+N.getId()+"&type="+N.getType()+"&valeur="+N.getValeur();
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
    
    public boolean addNote(Note N)
    {
        String url = Statics.BASE_URL + "notes/ajouter?trimestre="+N.getTrimestre()+"&eleve="+N.getEleve_id()+"&enseignant="+N.getEnseignant_id()+"&matiere="+N.getMatiereid()+"&type="+N.getType()+"&valeur="+N.getValeur();
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
    
    public ArrayList<Note> parseNotes(String jsonText)
    {
     try 
     {
        notes=new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
        List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
        for(Map<String,Object> obj : list)
        {
          Note N = new Note();
          float id = Float.parseFloat(obj.get("id").toString());
          N.setId((int)id);
          float trimestre=Float.parseFloat(obj.get("id_trimestre").toString());     
          N.setEleve_id(String.valueOf(obj.get("eleve_id")));
          String nom=String.valueOf(obj.get("eleve_prenom"))+" "+String.valueOf(obj.get("eleve_nom"));
          N.setElevenom(nom);
          N.setEnseignant_id(String.valueOf(obj.get("enseignant_id")));
          N.setMatiereid(String.valueOf(obj.get("matiere")));
          N.setMatierenom(String.valueOf(obj.get("matiere_nom")));
          N.setTrimestre((int)trimestre);
          N.setValeur(Double.valueOf(obj.get("valeur").toString()));
          N.setType(String.valueOf(obj.get("type")));
          
          notes.add(N);
        }
            
      } 
    catch (IOException ex) {}
    return notes;
   }
    
    public ArrayList<Note> verifierNote(Note N)
    {
        String url= Statics.BASE_URL+"notes/verifiernote?trimestre="+N.getTrimestre()+"&eleve="+N.getEleve_id()+"&matiere="+N.getMatiereid()+"&type="+N.getType();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                notes = parseNotes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return notes;               
    }
    
    public ArrayList<Note> getNotespourMoyenne(Note N)
    {
        String url = Statics.BASE_URL+"notes/getnotespourmoyenne?trimestre="+N.getTrimestre()+"&matiere="+N.getMatiereid()+"&eleve="+N.getEleve_id();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                notes = parseNotes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return notes;   
    }
    
    public ArrayList<Note> getNotes(int enseignant_id)
    {
        String url = Statics.BASE_URL+"notes/all/"+enseignant_id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                notes = parseNotes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return notes;
    }
    
    public boolean supprimerNote(Note N)
    {
        String url = Statics.BASE_URL + "notes/supprimer?id="+N.getId();
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
    
    public boolean emailnote(Note N)
    {
        String url = Statics.BASE_URL+"notes/mailnote?trimestre="+N.getTrimestre()+"&type="+N.getType()+"&eleve="+N.getEleve_id()+"&matiere="+N.getMatiereid();
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
    
    public ArrayList<Note> getNotesEleve(int eleve_id)
    {
        String url = Statics.BASE_URL+"/notes/noteseleve/"+eleve_id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                notes = parseNotes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return notes;
    }
    
    public ArrayList<Note> getNotesStats(int eleve_id, int matiere, String type)
    {
        String url = Statics.BASE_URL+"notes/notestats?eleve="+eleve_id+"&matiere="+matiere+"&type="+type;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                notes = parseNotes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return notes;
    }
}
