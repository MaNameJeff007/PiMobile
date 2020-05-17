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
import com.mycompany.myapp.entities.Activite;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mohamed Turki
 */
public class ServiceActivite {

    public ArrayList<Activite> parseListActiviteJson(String json) {

        ArrayList<Activite> listActivite = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> Activities = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) Activities.get("root");
            for (Map<String, Object> obj : list) {
                Activite act = new Activite();
                Map<String, Object> r0 = (Map<String, Object>) obj.get("User");
                act.setId((int) Float.parseFloat(obj.get("id").toString()));
                act.setNomActivite(obj.get("nomActivite").toString());
                act.setTypeActivite(obj.get("typeActivite").toString());
                act.setResponsable(r0.get("nom").toString() + " " + r0.get("prenom").toString());
                act.setVote((int) Float.parseFloat(obj.get("vote").toString()));
                listActivite.add(act);
            }

        } catch (IOException ex) {
        }
        return listActivite;

    }
    ArrayList<Activite> listActivite = new ArrayList<>();

    public ArrayList<Activite> ShowAllactivites(int idu) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/symfony/Ecole--Edtech1/web/app_dev.php/afficherActivityMobile/" + idu);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceActivite sc = new ServiceActivite();
                listActivite = sc.parseListActiviteJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

        return listActivite;
    }
     

    public void MinusForVote(int idA) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/symfony/Ecole--Edtech1/web/app_dev.php/voteActivitéfrontminusMobile/" + idA);

        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public void PlusForVote(int idA) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/symfony/Ecole--Edtech1/web/app_dev.php/voteActivitéfrontplusMobile/" + idA);

        NetworkManager.getInstance().addToQueueAndWait(con);
    }
}
