package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.RouteRole;
import lombok.*;
import org.example.controllers.ISecurityController;
import org.example.controllers.SecurityController;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    static DemoController demoController = new DemoController();
    private static ObjectMapper jsonMapper = new ObjectMapper();
    private static ISecurityController securityController = new SecurityController();
    private static List<Person> persons = new ArrayList(Arrays.asList(
            new Person("Helge", 3),
            new Person("Pedro", 4),
            new Person("Hannah", 5)
    ));

    public static void main(String[] args) {
        startServer(7070);
    }

    public static void startServer(int port) {
        ObjectMapper om = new ObjectMapper();
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig
                .initiateServer()
                .startServer(port)
                .setExceptionHandling()
                .setRoute(getPersonRoutes())
                .setRoute(securityRoutes())
                .setRoute(securedRoutes())
                .checkSecurityRoles()
                .setRoute(() -> {
                    path("demo", () -> {
                        get("test", demoController.sayHello());
                        get("error", ctx -> {
                            throw new Exception("Dette er en test");
                        });
                    });
                });
    }

    public static EndpointGroup getPersonRoutes() {
        return () -> {
            path("person", () -> {
                get("/", ctx -> ctx.json(persons));
                get("/{name}", ctx -> {
                    String name = ctx.pathParam("name");
                    Person p = persons.stream().filter(person -> person.getName().equals(name)).findFirst().get();
                    ctx.json(p);
                });
                post("/", ctx -> {
                    Person incoming = ctx.bodyAsClass(Person.class);
                    persons.add(incoming);
                    ctx.json(incoming);
                });
                put("/{id}", ctx -> {
                    String id = ctx.pathParam("id");
                    Person incoming = ctx.bodyAsClass(Person.class);
                    Person p = persons.get(Integer.parseInt(id));
                    p.setName(incoming.getName());
                    p.setAge(incoming.getAge());
                    ctx.json(p);
                });
            });
        };
    }

    public static void closeServer() {
        ApplicationConfig.getInstance().stopServer();
    }

    public static EndpointGroup securityRoutes() {
        return ()->{
            path("/auth", ()->{
                post("/login", securityController.login(),Role.ANYONE);
                post("/register", securityController.register(),Role.ANYONE);
            });
        };
    }
    public static EndpointGroup securedRoutes(){
        return ()->{
            path("/protected", ()->{
                before(securityController.authenticate());
                get("/user_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from USER Protected")),Role.USER);
                get("/admin_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from ADMIN Protected")),Role.ADMIN);
            });
        };
    }

//}
//        app.get("/", ctx -> ctx.result("Hello World"));

//        app.get("/", new Handler() {
//            @Override
//            public void handle(@NotNull Context context) throws Exception {
//                context.result("hello world");
//            }
//        })

    //        app.get("/person", ctx->ctx.json(persons) );
//        app.get("/person/{name}",ctx->{
//            String name = ctx.pathParam("name");
//            Person p = persons.stream().filter(person->person.getName().equals(name)).findFirst().get();
//            ctx.json(p);
//        });
//        app.routes(
//                () -> {
//                    path("person", () -> {
//                        get("/", ctx -> ctx.json(persons));
//                        get("/{name}", ctx -> {
//                            String name = ctx.pathParam("name");
//                            Person p = persons.stream().filter(person -> person.getName().equals(name)).findFirst().get();
//                            ctx.json(p);
//                        });
//                        post("/",ctx->{
//                            Person incoming = ctx.bodyAsClass(Person.class);
//                            persons.add(incoming);
//                            ctx.json(incoming);
//                        });
//                    });
//                }
//        );
//        app.get("/date",
//                ctx -> ctx.json(
//                        om.createObjectNode()
//                                .put("current_date", java.time.LocalDate.now().toString())));
////        app.post("/", ctx->ctx.)
//    }
//
    private static enum Role implements RouteRole { ANYONE, USER, ADMIN }
}
