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
import com.mycompany.myapp.entities.Moyenne;
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
public class MoyenneService 
{
    private ArrayList<Moyenne> moyennes;
   
    public static MoyenneService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private MoyenneService() 
    {
         req = new ConnectionRequest();
    }

    public static MoyenneService getInstance() 
    {
        if (instance == null) 
        {
            instance = new MoyenneService();
        }
        return instance;
    }
    
    public boolean recalculerMoyenneDepuisMoyenne(Moyenne M)
    {
        double moyenne=0;
        Note N=new Note();
        N.setEleve_id(M.getEleve_id());
        N.setMatiereid(M.getMatiere());
        N.setTrimestre(M.getTrimestre());
        ArrayList<Note> Tab=NoteService.getInstance().getNotespourMoyenne(N);
        if(Tab.isEmpty())
        {
         M.setMoyenne(moyenne);
        }
        else
        {
            for(int i=0;i<Tab.size();i++)
            {
                double valeur=Tab.get(i).getValeur(); 
                if(Tab.get(i).getType().equals("CC")){moyenne=moyenne+(valeur * 0.2);}
                else if(Tab.get(i).getType().equals("Devoir de controle")){moyenne=moyenne+(valeur * 0.3);}
                else if(Tab.get(i).getType().equals("Devoir de synthese")){moyenne=moyenne+(valeur * 0.5);    }    
            }
            M.setMoyenne(moyenne);
        }
        
        String url = Statics.BASE_URL+"moyennes/modifier?id="+M.getId()+"&moyenne="+M.getMoyenne();
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
    
    public boolean addMoyenneDepuisNote(ArrayList<Note> Tab)
    {     
     if(!Tab.isEmpty())
     {
        double moyenne=0;
        String eleve_id=null;
        String matiere=null;
        int trimestre=0;
        for(int i=0;i<Tab.size();i++)
        {
            double valeur=Tab.get(i).getValeur();
            eleve_id=Tab.get(i).getEleve_id();
            matiere=Tab.get(i).getMatiereid();
            trimestre=Tab.get(i).getTrimestre();
            
            if(Tab.get(i).getType().equals("CC"))
            {
                moyenne=moyenne+(valeur * 0.2);
            }
            else if(Tab.get(i).getType().equals("Devoir de controle"))
            {
                moyenne=moyenne+(valeur * 0.3);
                
            }
            else if(Tab.get(i).getType().equals("Devoir de synthese"))
            {
                moyenne=moyenne+(valeur * 0.5);    
            }    
        }
        
        String url="";
        Moyenne M=new Moyenne(trimestre, moyenne, eleve_id, matiere);
        
        ArrayList<Moyenne> Table=MoyenneService.getInstance().verifierMoyenne(M);
        
        if(Table.isEmpty())
        url = Statics.BASE_URL+"moyennes/ajouter?trimestre="+M.getTrimestre()+"&matiere="+M.getMatiere()+"&eleve="+M.getEleve_id()+"&moyenne="+M.getMoyenne();  
        
        else
        {
            int id=Table.get(0).getId();
            url = Statics.BASE_URL+"moyennes/modifier?id="+id+"&moyenne="+M.getMoyenne();
        }
        
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
     else
         return false;
    }
    
    public ArrayList<Moyenne> verifierMoyenne(Moyenne M)
    {
         String url = Statics.BASE_URL+"moyennes/verifier?trimestre="+M.getTrimestre()+"&matiere="+M.getMatiere()+"&eleve="+M.getEleve_id();
         req.setUrl(url);
         req.setPost(false);
         req.addResponseListener(new ActionListener<NetworkEvent>() 
         {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                moyennes = parseMoyennes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return moyennes; 
    }
    
    public ArrayList<Moyenne> parseMoyennes(String jsonText)
    {
     try 
     {
        moyennes=new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
        List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
        for(Map<String,Object> obj : list)
        {
          Moyenne M = new Moyenne();
          float id = Float.parseFloat(obj.get("id").toString());
          M.setId((int)id);
          M.setMatiere(obj.get("matiere").toString());
          M.setMoyenne(Double.valueOf(obj.get("moyenne").toString()));
          M.setTrimestre(Integer.valueOf(obj.get("trimestre").toString()));
          M.setEleve_id(obj.get("eleve_id").toString());
          if(obj.get("matiere_nom")!=null)
          M.setMatiere_nom(obj.get("matiere_nom").toString());
          
          if(((obj.get("prenom"))!=null)&&(obj.get("eleve_nom")!=null))
          M.setEleve_nom(M.getId()+"-"+obj.get("prenom").toString()+" "+obj.get("eleve_nom").toString());
          
          moyennes.add(M);
        }
     }
     catch (IOException ex) {}
     
     return moyennes;
    }
    
    public ArrayList<Moyenne> getMoyennes(int enseignant_id)
    {
        String url = Statics.BASE_URL+"moyennes/all?classeenseignant="+enseignant_id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                moyennes = parseMoyennes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return moyennes;
    }
    
    public boolean supprimerMoyenne(Moyenne M)
    {
        String url = Statics.BASE_URL + "moyennes/supprimer?id="+M.getId();
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
    
    public ArrayList<Moyenne> getMoyennesEleve(int eleve_id)
    {
        String url = Statics.BASE_URL+"moyennes/moyenneseleve?id="+eleve_id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                moyennes = parseMoyennes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return moyennes;
    }
    
    public ArrayList<Moyenne> getMoyenneStats(int eleve_id, int matiere)
    {
        String url = Statics.BASE_URL+"moyennes/moyennestats?eleve="+eleve_id+"&matiere="+matiere;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                moyennes = parseMoyennes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return moyennes;
    }
}
