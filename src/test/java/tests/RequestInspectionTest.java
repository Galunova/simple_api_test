package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class RequestInspectionTest {
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
    public void test01_RequestInspectionHeader_Success() {
        given()
                .spec(baseSpec())
                .auth().preemptive().basic("user", "passwd")
                .formParam("user", "user")
                .formParam("passwd", "passwd")
                .when().get("/headers")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("headers.Host", equalTo("httpbin.org"));
    }

    @Test
    public void test02_RequestInspectionIP_Success() {
        given()
                .spec(baseSpec())
                .auth().preemptive().basic("user", "passwd")
                .formParam("user", "user")
                .formParam("passwd", "passwd")
                .when().get("/ip")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("origin", equalTo("92.245.99.146, 92.245.99.146"));
    }

    @Test
    public void test03_RequestInspectionUserAgent_Success() {
        given()
                .spec(baseSpec())
                .auth().preemptive().basic("user", "passwd")
                .formParam("user", "user")
                .formParam("passwd", "passwd")
                .when().get("/user-agent")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("user-agent", equalTo("Apache-HttpClient/4.5.2 (Java/1.8.0_171)"));
    }
}
