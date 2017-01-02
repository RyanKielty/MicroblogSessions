package com.company;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
public class Main {
    public static HashMap<String, User> usersMap = new HashMap<>();
    public static void main(String[] args) {
        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap createUser = new HashMap();
                    Session session = request.session();
                    String name = session.attribute("createUser");
                    User user = usersMap.get(name);
                    if (name == null) {
                        return new ModelAndView(createUser, "index.html");
                    } else {
                        createUser.put("name", user.getUserName());
                        createUser.put("password", user.getPassword());
                        createUser.put("messages", user.messageList);
                        return new ModelAndView(createUser, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/login-page",
                ((request, response) -> {
                    String name = request.queryParams("createUser");
                    String password = request.queryParams("enterPassword");
                    User user = usersMap.get(name);
                    if (user == null) {
                        user = new User(name, password);
                        usersMap.put(name, user);
                    }
                    if (user.password.equals(password)) {
                        System.out.println("You are logged-in to the blog now");
                        Session session = request.session();
                        session.attribute("createUser", name);
                    } else {
                        System.out.println("Incorrect password associated with this account");
                    }
                    response.redirect("/");
                    return "";
                })
        );



        Spark.post(
                "/create-message",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("createUser");
                    User user = usersMap.get(name);
                    if (user == null) {
                        throw new Exception("You fail at life");
                    }
                    String addPost = request.queryParams("createMessage");

                    Messages createNewMessage = new Messages(addPost);
                    user.messageList.add(createNewMessage);

                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/delete-message",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("createUser");
                    User user = usersMap.get(name);

                    String removePost = request.queryParams("deleteMessage");

                    user.messageList.remove(Integer.parseInt(removePost)-1);

                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/amend-message",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("createUser");
                    User user = usersMap.get(name);


                    String amendPost = request.queryParams("amendPost");
                    String addPost = request.queryParams("createMessage");

                    user.messageList.remove(Integer.parseInt(amendPost)-1);
                    user.messageList.add(Integer.parseInt(amendPost)-1, new Messages(addPost));

                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );
    }
}