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

public class AuthMethodsTest {

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
    public void test01_checkBasicAuthGetMethod_Success() {
        given()
                .spec(baseSpec())
                .auth()
                .basic("user", "passwd")
                .when()
                .get("/basic-auth/user/passwd")
                .then()
                .body("user", equalTo("user"),
                        "authenticated", equalTo(true))
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test02_checkBearerAuthGetMethod_Success() {
        given()
                .spec(baseSpec())
                .header("Authorization", "Bearer "+ "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .when()
                .get("/bearer")
                .then()
                .body("authenticated", equalTo(true))
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test03_checkDigestAuthGetMethod_Success() {
        given()
                .spec(baseSpec())
                .auth().digest("user", "passwd")
                .when()
                .get("digest-auth/qop/user/passwd")
                .then()
                .body("authenticated", equalTo(true))
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test04_checkDigestAuthAlgorithmGetMethod_Success() {
        given()
                .spec(baseSpec())
                .auth().digest("user", "passwd")
                .when()
                .get("digest-auth/qop/user/passwd/SHA-256")
                .then()
                .body("authenticated", equalTo(true))
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test05_checkDigestAuthAlgorithmStaleAfterGetMethod_Success() {
        given()
                .spec(baseSpec())
                .auth().digest("user", "passwd")
                .when()
                .get("digest-auth/qop/user/passwd/SHA-256/never")
                .then()
                .body("authenticated", equalTo(true))
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void test06_checkHiddenBasicAuthGetMethod_Success() {
        given()
                .spec(baseSpec())
                .auth().preemptive().basic("user", "passwd")
                .formParam("user", "user")
                .formParam("passwd", "passwd")
                .when()
                .get("hidden-basic-auth/user/passwd")
                .then()
                .body("authenticated", equalTo(true))
                .statusCode(HttpStatus.SC_OK);
    }
}
