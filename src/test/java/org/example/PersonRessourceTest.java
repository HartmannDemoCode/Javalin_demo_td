package org.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class PersonRessourceTest {
    @BeforeAll
    static void setUpAll() {
        RestAssured.baseURI = "http://localhost:7777/api";
        Main.startServer(7777);
    }

    @AfterAll
    static void tearDownAll() {
        Main.closeServer();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test server is running")
    void testServerIsRunning() {
        RestAssured
                .given()
                .when()
                .get("demo/test")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Test response body")
    void testResponseBody() {
        RestAssured
                .given().log().all()
                .when()
                .get("person/Helge")
                .then().log().body()
                .statusCode(200)
                .body("name", equalTo("Helge"))
                .body("age", equalTo(3))
                .body("name", equalTo("Helge")
                        , "age", equalTo(3));
    }

    @Test
    @DisplayName("Json Path")
    void testJsonPath() {
        Person p1 = new Person("Helge", 3);
        Person p2 = new Person("Pedro", 4);
        Person p3 = new Person("Hannah", 5);
        List<Person> personList;
        personList = RestAssured
                .given()
                .when()
                .get("person")
                .then()
                .extract()
                .body()
                .jsonPath().getList("", Person.class);
        System.out.println(personList);
        assertEquals(3, personList.size());
        assertThat(personList, containsInAnyOrder(p1, p2, p3));
        assertThat(personList, hasItem(p1));

    }

    @Test
    @DisplayName("Test post")
    void testPost() {
        Person p = new Person("Hans", 6);
        RestAssured
                .given()
                .contentType("application/json")
                .body(p)
                .when()
                .post("person")
                .then()
                .statusCode(200)
                .body("name", equalTo("Hans"))
                .body("age", equalTo(6));
    }

    @Test
    @DisplayName("Test put")
    void testPut() {
        Person p = new Person("Jesper", 6);
        RestAssured
                .given()
                .contentType("application/json")
                .body(p)
                .when()
                .put("person/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Jesper"))
                .body("age", equalTo(6));
    }
}