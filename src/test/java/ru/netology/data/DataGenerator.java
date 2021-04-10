package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Value;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    public static Faker faker = new Faker();

    private DataGenerator() {
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void activeUser(UserInfo user) {
        given()
                .spec(requestSpec)
                .body(new Gson().toJson(user))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static void blockedUser(UserInfo user) {
        given()
                .spec(requestSpec)
                .body(new Gson().toJson(user))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String setLogin(){
        return faker.name().username();
    }

    public static String setPassword(){
        return faker.internet().password();
    }

    public static class Registration{
        private Registration(){

        }

        public static UserInfo generateUser(String status){
            UserInfo user = new UserInfo(setLogin(), setPassword(), status);
            return user;
        }
    }

    @Value
    @AllArgsConstructor
    public static class UserInfo{
       String login;
       String password;
       String status;
    }

}