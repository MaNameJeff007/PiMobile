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
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rami2
 */
public class UserService 
{
    public ArrayList<User> users;
    
    public static UserService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private UserService() 
    {
         req = new ConnectionRequest();
    }

    public static UserService getInstance() 
    {
        if (instance == null) 
        {
            instance = new UserService();
        }
        return instance;
    }
    
    public ArrayList<User> parseUsers(String jsonText)
    {
     try 
     {
        users=new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
        List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
        for(Map<String,Object> obj : list)
        {
          User U = new User();
          float id = Float.parseFloat(obj.get("id").toString());
          U.setIdentifiant((int)id);
          U.setPrenom(String.valueOf(obj.get("prenom")));
          U.setNom(String.valueOf(obj.get("nom")));
          U.setRoles(String.valueOf(obj.get("roles")));
          
          if(U.getRoles().contains("ELEVE"))
          {
              U.setClasseeleve_id(String.valueOf(obj.get("classeeleve_id")));
              U.setParent_id(String.valueOf(obj.get("parent_id")));
              if(obj.get("niveau")!=null)
              {
              String classe=obj.get("niveau").toString()+"'"+" annee "+U.getClasseeleve_id();
              U.setClasse(classe);
              }
          }
          else if(U.getRoles().contains("ENSEIGNANT"))
          {
              U.setParent_id(null);
              U.setClasseeleve_id(null);
              U.setClasseenseignant_id(obj.get("classeenseignant_id").toString());
          }
          else
          {
              U.setParent_id(null);
              U.setClasseeleve_id(null);
              U.setParent_id(null);
          }
          
          users.add(U);
        }
            
      } 
    catch (IOException ex) {}
    return users;
   }
    
    public ArrayList<User> getEleves(int classe_id)
    {
        String url = Statics.BASE_URL+"listeeleves/"+classe_id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
    
    public ArrayList<User> getElevesparparent(int parent_id)
    {
        String url = Statics.BASE_URL+"elevesparparent?parentid="+parent_id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
    
    
}
