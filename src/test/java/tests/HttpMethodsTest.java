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

public class HttpMethodsTest {
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
    public void test01_checkGetMethod_Success200() {
        given().spec(baseSpec()).when().get("/get")
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test02_checkDeleteMethod_Success200() {
        given().spec(baseSpec()).when().delete("/delete")
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test03_checkPutMethod_Success() {
        given().spec(baseSpec()).when().put("/put")
                .then().body("headers.Host", equalTo("httpbin.org"));
    }

    @Test
    public void test04_checkPatchMethod_Success() {
        given().spec(baseSpec()).when().patch("/patch")
                .then().statusCode(200);
    }

    @Test
    public void test05_checkPostMethod_Success() {
        given().spec(baseSpec()).when().post("/post")
                .then().statusCode(200);
    }

}
