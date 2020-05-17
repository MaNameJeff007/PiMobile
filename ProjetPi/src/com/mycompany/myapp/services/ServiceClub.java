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
import com.mycompany.myapp.entities.Club;
import com.mycompany.myapp.entities.User;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mohamed Turki
 */
public class ServiceClub {

    public ArrayList<Club> parseListClubsJson(String json) {
        ArrayList<Club> listClubs = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> clubs = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) clubs.get("root");
            for (Map<String, Object> obj : list) {
                Club c = new Club();
                Map<String, Object> r0 = (Map<String, Object>) obj.get("User");
                c.setId((int) Float.parseFloat(obj.get("id").toString()));
                c.setResponsable(r0.get("nom").toString() + " " + r0.get("prenom").toString());
                c.setUser_id((int) Float.parseFloat(r0.get("id").toString()));
                c.setNomclub(obj.get("nomclub").toString());
                c.setNom_image(obj.get("nomImage").toString());
                listClubs.add(c);
            }
        } catch (IOException ex) {
        }
        return listClubs;
    }
    ArrayList<Club> listClubs = new ArrayList<>();

    public ArrayList<Club> ShowAllClubs() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/symfony/Ecole--Edtech1/web/app_dev.php/afficherclubMobile");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceClub sc = new ServiceClub();
                listClubs = sc.parseListClubsJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listClubs;
    }

}
