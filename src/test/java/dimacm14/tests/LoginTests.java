package dimacm14.tests;

import dimacm14.modeles.LoginBodyModel;
import dimacm14.modeles.LoginResponseModel;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;

import static dimacm14.specs.TestSpecs.bodyRequestSpec;
import static dimacm14.specs.TestSpecs.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Авторизация")
@Tag("Smoke")
@Severity(BLOCKER)
@Owner("dimacm14")
public class LoginTests {

    @Story("Успешная авторизация")
    @Test(description = "Авторизация с электронной почтой и паролем")
    void successfulLoginTest() {
        LoginBodyModel loginBody = new LoginBodyModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseModel loginResponse =
                step("При авторизации с электронной почтой и паролем ответ имеет код статуса 200", () ->
                        given(bodyRequestSpec)
                                .body(loginBody)
                                .when()
                                .post("/login")
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(LoginResponseModel.class));

        step("Проверить токен", () ->
                assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }

    @Story("Неуспешная авторизация")
    @Test(description = "Авторизация без пароля")
    void unsuccessfulMissingPasswordLoginTest() {
        LoginBodyModel loginBody = new LoginBodyModel();
        loginBody.setEmail("eve.holt@reqres.in");
        String expectedError = "Missing password";

        LoginResponseModel loginResponse =
                step("При авторизации без пароля ответ имеет код статуса 400", () ->
                        given(bodyRequestSpec)
                                .body(loginBody)
                                .when()
                                .post("/login")
                                .then()
                                .spec(responseSpec)
                                .statusCode(400)
                                .extract().as(LoginResponseModel.class));

        step("Получено сообщение об ошибке: " + expectedError, () ->
                assertThat(loginResponse.getError()).isEqualTo(expectedError));
    }
}
