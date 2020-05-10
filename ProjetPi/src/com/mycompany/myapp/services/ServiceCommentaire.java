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
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceCommentaire {

    public ArrayList<Commentaire> commentaire;

    public static ServiceCommentaire instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceCommentaire() {
        req = new ConnectionRequest();
    }

    public static ServiceCommentaire getInstance() {
        if (instance == null) {
            instance = new ServiceCommentaire();
        }
        return instance;
    }

    /* public boolean addLike(Likes t) {
     String url = "";// Statics.BASE_URL + "/tasks/" + t.getName() + "/" + t.getStatus();
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
     }*/

    /*  public ArrayList<Likes> parseTasks(String jsonText){
     try {
     tasks=new ArrayList<>();
     JSONParser j = new JSONParser();
     Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
           
     List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
        
     for(Map<String,Object> obj : list){
     Likes t = new Likes();
     float id = Float.parseFloat(obj.get("id").toString());
     t.setId((int)id);
     t.setStatus(((int)Float.parseFloat(obj.get("status").toString())));
     t.setName(obj.get("name").toString());
              
     tasks.add(t);
     }
            
            
     } catch (IOException ex) {
            
     }
     return tasks;
     }*/
    /* public ArrayList<Likes> getAllTasks() {
     String url = Statics.BASE_URL + "/tasks/all";
     req.setUrl(url);
     req.setPost(false);
     req.addResponseListener(new ActionListener<NetworkEvent>() {
     @Override
     public void actionPerformed(NetworkEvent evt) {
     // Likes = parseTasks(new String(req.getResponseData()));
     req.removeResponseListener(this);
     }
     });
     NetworkManager.getInstance().addToQueueAndWait(req);
     return Likes;
     }*/
}
