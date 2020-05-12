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
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.entities.Matiere;
import com.mycompany.myapp.entities.Salle;
import com.mycompany.myapp.entities.Seance;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author dell
 */
public class ServiceSeance {

    private ConnectionRequest req;
    public static ServiceSeance instance = null;
    public boolean resultOK;
    public ArrayList<Seance> classes;

    private ServiceSeance() {
        req = new ConnectionRequest();
    }

    public static ServiceSeance getInstance() {
        if (instance == null) {
            instance = new ServiceSeance();
        }
        return instance;
    }

    public ArrayList<Seance> getElevesClasse(String libelle) {
        String url = Statics.BASE_URL + "findClasseElv/" + libelle + "";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                classes = parseS(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }

    public boolean updateSeance(Seance t) {
        String url = Statics.BASE_URL + "editSeance/" + t.getId() + "?hdeb=" + t.getHdeb() + "&hfin=" + t.getHfin() + "&jour=" + t.getJour() + "&enseignant=" + t.getEnseignant().getIdentifiant() + "&matiere=" + t.getMatiere().getNom() + "&classe=" + t.getClasse().getLibelle() + "&salle=" + t.getSalle().getLibelle() + "";

        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteSeance(Seance t) {
        String url = Statics.BASE_URL + "Seance/" + t.getId() + "";

        req.setPost(false);

        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean addSeance(Seance t) {
        String url = Statics.BASE_URL + "allSeances/new?hdeb=" + t.getHdeb() + "&hfin=" + t.getHfin() + "&jour=" + t.getJour() + "&enseignant=" + t.getEnseignant().getIdentifiant() + "&matiere=" + t.getMatiere().getNom() + "&classe=" + t.getClasse().getLibelle() + "&salle=" + t.getSalle().getLibelle() + "";

        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Seance> parseS(String jsonText) {
        try {
            classes = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Seance t = new Seance();

                Classe c = new Classe();
                Salle s = new Salle();
                User ens = new User();
                Matiere m = new Matiere();
                try {
                    JSONObject obj2 = new JSONObject(obj.get("classe").toString());

                    String libelle = obj2.getString("libelle");

                    c.setLibelle(libelle);

                } catch (JSONException ex) {
                    System.out.println(ex);
                }

                JSONObject obj3;
                try {
                    obj3 = new JSONObject(obj.get("salle").toString());
                    s.setLibelle(obj3.getString("libelle"));
                    //System.out.println(obj3.getString("libelle"));
                } catch (JSONException ex) {
                    System.out.println(ex);
                }

                JSONObject obj4;
                try {
                    obj4 = new JSONObject(obj.get("enseignant").toString());
                    //ens.setIdentifiant(Integer.parseInt(obj4.getString("id")));
                    ens.setNom(obj4.getString("nom"));
                    ens.setPrenom(obj4.getString("prenom"));
                    //System.out.println(obj4.getString("nom"));
                } catch (JSONException ex) {
                    System.out.println(ex);
                }

                JSONObject obj5;
                try {
                    obj5 = new JSONObject(obj.get("matiere").toString());
                    m.setNom(obj5.getString("nom"));
                    System.out.println(obj5.getString("nom"));
                } catch (JSONException ex) {
                    System.out.println(ex);
                }

                t.setClasse(c);
                t.setEnseignant(ens);
                t.setSalle(s);
                t.setMatiere(m);
                t.setHdeb(obj.get("hdeb").toString());
                t.setHfin(obj.get("hfin").toString());
                t.setJour(obj.get("jour").toString());
                t.setId(((int) Float.parseFloat(obj.get("id").toString())));
                classes.add(t);
            }

        } catch (IOException ex) {

        }
        return classes;
    }

    public ArrayList<Seance> getAllSeances() {
        String url = Statics.BASE_URL + "allSeances/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                classes = parseS(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }

    public ArrayList<Seance> getSeancesClasse(Classe t) {
        String url = Statics.BASE_URL + "SeancesC/" + t.getLibelle() + "";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                classes = parseS2(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }

    public ArrayList<Seance> getSeancesClasse1(String libelle) {
        String url = Statics.BASE_URL + "SeancesC/" + libelle + "";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                classes = parseS2(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classes;
    }

    public ArrayList<Seance> parseS2(String jsonText) {
        try {
            classes = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Seance t = new Seance();

                Classe c = new Classe();
                Salle s = new Salle();
                User ens = new User();
                Matiere m = new Matiere();

                c.setLibelle(obj.get("classe").toString());
                s.setLibelle(obj.get("salle").toString());
                ens.setNom(obj.get("nom").toString());
                ens.setPrenom(obj.get("prenom").toString());
                m.setNom(obj.get("matiere").toString());
                t.setClasse(c);
                t.setEnseignant(ens);
                t.setSalle(s);
                t.setMatiere(m);
                t.setHdeb(obj.get("hdeb").toString());
                t.setHfin(obj.get("hfin").toString());
                t.setJour(obj.get("jour").toString());
                t.setId(((int) Float.parseFloat(obj.get("id").toString())));
                classes.add(t);
            }

        } catch (IOException ex) {

        }
        return classes;
    }
}
