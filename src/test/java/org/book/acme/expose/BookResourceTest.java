package org.book.acme.expose;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.book.acme.expose.dto.BookDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.wildfly.common.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.UUID;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookResourceTest {

    private static String createdBookId;

    private static final String BASE_PATH = "/books";


    @Test
    @Order(1)
    void testCreateBook() {
        BookDto.Request request = new BookDto.Request();
        request.setTitle("Test Book");
        request.setAuthor("Test Author");
        request.setAmount(1);
        request.setIsActive(true);
        request.setCreatedBy("test");
        request.setLastModifiedBy("test");

        BookDto.Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(BASE_PATH)
                .then()
                    .statusCode(201)
                    .body("id", org.hamcrest.Matchers.notNullValue())
                    .body("title", is("Test Book"))
                    .body("author", is("Test Author"))
                    .body("amount", is(1))
                    .extract().as(BookDto.Response.class);

        createdBookId = response.getId().toString();
        assertNotNull(createdBookId);
    }


    @Test
    @Order(2)
    void testGetBooksAll() {
        given()
                .when().get(BASE_PATH)
                .then()
                    .statusCode(200)
                    .body("size()", greaterThanOrEqualTo(1))
                    .body("title", hasItem("Test Book"));

    }

    @Test
    @Order(3)
    void testGetBookById() {
        given()
                .when()
                .get(BASE_PATH + "/" + createdBookId)
                .then()
                    .statusCode(200)
                    .body("id", is(createdBookId))
                    .body("title", is("Test Book"));
    }


    @Test
    @Order(4)
    void testUpdateBook() {
        BookDto.Request request = new BookDto.Request();
        request.setTitle("Updated Test Book");
        request.setAuthor("Updated Test Author");
        request.setAmount(2);
        request.setIsActive(true);
        request.setCreatedBy("test");
        request.setLastModifiedBy("test");

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", createdBookId)
                .body(request)
                .when()
                .put(BASE_PATH + "/{id}")
                .then()
                    .statusCode(200)
                    .body("title", is(request.getTitle()));
    }

    @Test
    @Order(5)
    void testDeleteBook() {
        assertNotNull(createdBookId);

        given()
                .pathParam("id", createdBookId)
                .when().delete(BASE_PATH + "/{id}")
                .then()
                    .statusCode(200);


    }


    @Test
    @Order(6)
    void testGetBookByIdNotFound() {

        String notExistsBookId = UUID.randomUUID().toString();
        given()
                .pathParam("id", notExistsBookId)
                .when().get(BASE_PATH + "/{id}")
                .then()
                    .statusCode(404);
    }

}
