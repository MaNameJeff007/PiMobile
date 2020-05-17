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
import com.mycompany.myapp.entities.Evenement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.codename1.ui.Command;
import com.codename1.share.ShareService;
import com.codename1.share.FacebookShare;

/**
 *
 * @author Mohamed Turki
 */
public class ServiceEvent {

    public ArrayList<Evenement> parseListEventsJson(String json, int club_id, String nomC) {
        ArrayList<Evenement> listEvents = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> Events = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) Events.get("root");
            for (Map<String, Object> obj : list) {
                Evenement c = new Evenement();
                Map<String, Object> r0 = (Map<String, Object>) obj.get("heureDebut");
                Map<String, Object> r1 = (Map<String, Object>) obj.get("heureFin");
                c.setId((int) Float.parseFloat(obj.get("id").toString()));
                c.setImage_evenement(obj.get("nomImage").toString());
                c.setNom_evenement(obj.get("nomEvenement").toString());
                c.setClub_id(club_id);
                c.setNom_club(nomC);

//                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//                cal.setTimeInMillis(timestamp);
//                String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
//                Calendar cal = Calendar.getInstance();
//                cal.setTimeInMillis((int) Float.parseFloat(r0.get("timestamp").toString()) * 1000L);
               
//                System.out.println(cal.getTime());
                //String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();

                c.setHeure_debut(new java.util.Date((long) Float.parseFloat(r0.get("timestamp").toString()) * 1000));
                c.setHeure_fin(new java.util.Date((long) Float.parseFloat(r1.get("timestamp").toString()) * 1000));
                listEvents.add(c);
            }
        } catch (IOException ex) {
        }
        return listEvents;
    }
    ArrayList<Evenement> listEvents = new ArrayList<>();

    public ArrayList<Evenement> ShowAllEvents(int club_id, String nomC) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/symfony/Ecole--Edtech1/web/app_dev.php/afficherEventMobile/" + club_id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceEvent sev = new ServiceEvent();
                listEvents = sev.parseListEventsJson(new String(con.getResponseData()), club_id, nomC);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listEvents;
    }

}
