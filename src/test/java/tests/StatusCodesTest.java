package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StatusCodesTest {
    private RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON.withCharset("UTF-8"))
                .setBaseUri("http://httpbin.org/")
                .setPort(80)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void test01_DeleteStatus200() {
        given()
                .spec(baseSpec())
                .when().delete("/status/200")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void test02_DeleteStatus300() {
        given()
                .spec(baseSpec())
                .when().delete("/status/300")
                .then().statusLine("HTTP/1.1 300 MULTIPLE CHOICES")
                .statusCode(HttpStatus.SC_MULTIPLE_CHOICES);
    }

    @Test
    public void test03_DeleteStatus400() {
        given()
                .spec(baseSpec())
                .when().delete("/status/400")
                .then().statusLine("HTTP/1.1 400 BAD REQUEST")
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void test04_DeleteStatus500() {
        given()
                .spec(baseSpec())
                .when().delete("/status/500")
                .then().statusLine("HTTP/1.1 500 INTERNAL SERVER ERROR")
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void test05_GetStatus200() {
        given()
                .spec(baseSpec())
                .when().get("/status/200")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void test06_GetStatus300() {
        given()
                .spec(baseSpec())
                .when().get("/status/300")
                .then().assertThat().statusCode(HttpStatus.SC_MULTIPLE_CHOICES);
    }

    @Test
    public void test07_GetStatus400() {
        given()
                .spec(baseSpec())
                .when().get("/status/400")
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void test08_GetStatus500() {
        given()
                .spec(baseSpec())
                .when().get("/status/500")
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void test09_PatchStatus200() {
        given()
                .spec(baseSpec())
                .when().patch("/status/200")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void test10_PatchStatus300() {
        given()
                .spec(baseSpec())
                .when().patch("/status/300")
                .then().assertThat().statusCode(HttpStatus.SC_MULTIPLE_CHOICES);
    }

    @Test
    public void test11_PatchStatus400() {
        given()
                .spec(baseSpec())
                .when().patch("/status/400")
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void test12_PatchStatus500() {
        given()
                .spec(baseSpec())
                .when().patch("/status/500")
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void test13_PostStatus200() {
        given()
                .spec(baseSpec())
                .when().post("/status/200")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void test14_PostStatus300() {
        given()
                .spec(baseSpec())
                .when().post("/status/300")
                .then().assertThat().statusCode(HttpStatus.SC_MULTIPLE_CHOICES);
    }

    @Test
    public void test15_PostStatus400() {
        given()
                .spec(baseSpec())
                .when().post("/status/400")
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void test16_PostStatus500() {
        given()
                .spec(baseSpec())
                .when().post("/status/500")
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void test17_PutStatus200() {
        given()
                .spec(baseSpec())
                .when().put("/status/200")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void test18_PutStatus300() {
        given()
                .spec(baseSpec())
                .when().put("/status/300")
                .then().assertThat().statusCode(HttpStatus.SC_MULTIPLE_CHOICES);
    }

    @Test
    public void test19_PutStatus400() {
        given()
                .spec(baseSpec())
                .when().put("/status/400")
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void test20_PutStatus500() {
        given()
                .spec(baseSpec())
                .when().put("/status/500")
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
