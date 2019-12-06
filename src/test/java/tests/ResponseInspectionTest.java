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

public class ResponseInspectionTest {
    private RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON.withCharset("UTF-8"))
//                .setContentType(ContentType.XML)
                .setBaseUri("http://httpbin.org/")
                .setPort(80)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void test01_ResponseInspectionCache_Success() {
        given()
                .spec(baseSpec())
                .header("If-Modified-Since", "1")
                .when().get("/cache")
                .then().assertThat()
                .statusCode(HttpStatus.SC_NOT_MODIFIED);
    }

    @Test
    public void test02_ResponseInspectationCacheValue_Success() {
        given()
                .spec(baseSpec())
                .pathParam("value", "13")
                .when().get("/cache/{value}")
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test03_ResponseInspectationCacheEtag_Success() {
     given()
     .spec(baseSpec())
             //TODO I have no idea how it should work!
     .header("If-None-Match", "686897696a7")
     .when().get("/etag")
     .then().assertThat()
     .statusCode(HttpStatus.SC_PRECONDITION_FAILED);
    }

    @Test
    public void test04_ResponseInspectationResponseHeadersGet_Success() {
        given()
                .spec(baseSpec())
                .queryParam("freeform", "ololo")
                .when().post("/response-headers")
                .then()
                .body("freeform", equalTo("ololo"))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test05_ResponseInspectationResponseHeadersPost_Success() {
        given()
                .spec(baseSpec())
                .queryParam("freeform", "ololo")
                .when().post("/response-headers")
                .then()
                .body("freeform", equalTo("ololo"))
                .statusCode(HttpStatus.SC_OK);
    }
}
