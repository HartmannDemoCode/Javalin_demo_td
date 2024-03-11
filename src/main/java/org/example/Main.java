package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    static DemoController demoController = new DemoController();
        private static List<Person> persons = new ArrayList(Arrays.asList(
            new Person("Helge", 3),
            new Person("Pedro", 4),
            new Person("Hannah", 5)
    ));

    public static void main(String[] args) {
        ObjectMapper om = new ObjectMapper();
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig
                .initiateServer()
                .startServer(7070)
                .setExceptionHandling()
                .setRoute(() -> {

                })
                .setRoute(() -> {
            path("demo", () -> {
                get("test", demoController.sayHello());
                get("error", ctx -> {throw new Exception("Dette er en test");});
            });
        });
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
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @EqualsAndHashCode
    private static class Person {
        String name;
        int age;

    }
}