package org.example;

import io.javalin.http.Handler;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class DemoController {
    public Handler sayHello(){
       return ctx -> ctx.json("{\"msg\":\"Hello from server\"}");
    }
}
