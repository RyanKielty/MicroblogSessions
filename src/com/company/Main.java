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
                        System.out.println();

                        Session session = request.session();
                        session.attribute("createUser", name);
                    }


                    response.redirect("/");
                    return "";
                })
        );



        Spark.post(
                "/create-message",
                ((request, response) -> {
                    Session session = request.session();

                    String addPost = request.queryParams("createMessage");

//                    messageList.add(new Messages(addPost));

                    response.redirect("/");
                    return "";
                })
        );



        Spark.post(
                "/delete-message",
                ((request, response) -> {
                    Session session = request.session();

                    String removePost = request.queryParams("deleteMessage");

//                    messageList.remove(Integer.parseInt(removePost)-1);

                    response.redirect("/");
                    return "";
                })
        );



        Spark.post(
                "/amend-message",
                ((request, response) -> {
                    Session session = request.session();

                    String amendPost = request.queryParams("amendPost");
                    String addPost = request.queryParams("createMessage");

//                    messageList.remove(Integer.parseInt(amendPost)-1);
//                    messageList.add(Integer.parseInt(amendPost)-1, new Messages(addPost));

                    response.redirect("/");
                    return "";
                })
        );




        Spark.post(
                "/logout",
                ((request, response) -> {

                    //name = null;
                    //Remove above line after capability to store users is available

                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );
    }
}