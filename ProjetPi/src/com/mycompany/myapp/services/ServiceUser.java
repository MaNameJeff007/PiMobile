/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.components.InteractionDialog;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.gui.ForumForm;
import com.mycompany.myapp.gui.HomeForm;
import com.mycompany.myapp.gui.Login;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author bhk
 */
public class ServiceUser {

    public ArrayList<User> users;

    private static int workload = 5;
    public static ServiceUser instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceUser() {
        req = new ConnectionRequest();
    }

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }

    public ArrayList<User> parseUsers(String jsonText) {
        try {
            users = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");

            for (Map<String, Object> obj : list) {

                User u = new User();
                u.setIdentifiant(((int) Float.parseFloat(obj.get("id").toString())));
                u.setNom(obj.get("nom").toString());
                u.setUsername(obj.get("username").toString());
                u.setPassword(obj.get("password").toString());
                u.setPrenom(obj.get("prenom").toString());
                u.setEmail(obj.get("email").toString());
                u.setRoles(obj.get("roles").toString());
                // System.out.println(obj.get("classedeseleves").toString());
                users.add(u);
            }

        } catch (IOException ex) {

        }
        return users;
    }

    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        stored_hash = "$2a" + stored_hash.substring(3);
        if (null == stored_hash || !stored_hash.startsWith("$2a$")) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return (password_verified);
    }

    public User getConnectedUser(String u, String p) {
        String url = Statics.BASE_URL + "loginMobile/" + u;
        User ss = new User();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
                boolean test = false;
                if (users.isEmpty()) {
                    InteractionDialog d = new InteractionDialog("Login");
                    TextArea popupBody = new TextArea("Bad Credentials. Try again!");
                    popupBody.setUIID("PopupBody");
                    popupBody.setEditable(false);
                    d.setLayout(new BorderLayout());
                    d.add(BorderLayout.CENTER, popupBody);
                    Button ok = new Button("OK");
                    ok.addActionListener((ee) -> d.dispose());
                    d.addComponent(BorderLayout.SOUTH, ok);
                    d.show(0, 0, 0, 0);
                } else {
                    for (User obj : users) {
                        if (u.equals(obj.getUsername()) && checkPassword(p, obj.getPassword())) {
                            ss.setUsername(obj.getUsername());
                            ss.setIdentifiant(obj.getIdentifiant());
                            ss.setNom(obj.getNom());
                            ss.setPrenom(obj.getPrenom());
                            ss.setEmail(obj.getEmail());
                            ss.setRoles(obj.getRoles());
                            test = true;
                        }
                    }
                    if (test == false) {
                        InteractionDialog d = new InteractionDialog("Login");
                        TextArea popupBody = new TextArea("Bad Credentials. Try again!");
                        popupBody.setUIID("PopupBody");
                        popupBody.setEditable(false);
                        d.setLayout(new BorderLayout());
                        d.add(BorderLayout.CENTER, popupBody);
                        Button ok = new Button("OK");
                        ok.addActionListener((ee) -> d.dispose());
                        d.addComponent(BorderLayout.SOUTH, ok);
                        d.show(0, 0, 0, 0);
                    }
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ss;
    }

}
