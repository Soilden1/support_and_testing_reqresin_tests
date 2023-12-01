package dimacm14.steps;

import dimacm14.modeles.RegistrationBodyModel;
import dimacm14.modeles.RegistrationResponseModel;
import io.qameta.allure.*;

import static dimacm14.specs.TestSpecs.bodyRequestSpec;
import static dimacm14.specs.TestSpecs.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationSteps {

    @Step("Регистрация с электронной почтой и паролем")
    public void successfulRegistration() {
        RegistrationBodyModel registrationBody = new RegistrationBodyModel();
        registrationBody.setEmail("eve.holt@reqres.in");
        registrationBody.setPassword("pistol");

        RegistrationResponseModel registrationResponse =
                step("При регистрации с электронной почтой и паролем ответ имеет код статуса 200", () ->
                        given(bodyRequestSpec)
                                .body(registrationBody)
                                .when()
                                .post("/register")
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(RegistrationResponseModel.class));

        step("Проверить id и token", () -> {
            assertThat(registrationResponse.getId()).isEqualTo("4");
            assertThat(registrationResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        });
    }

    @Step("Регистрация без пароля")
    public void unsuccessfulMissingPasswordRegistration() {
        RegistrationBodyModel registrationBody = new RegistrationBodyModel();
        registrationBody.setEmail("sydney@fife");
        String expectedError = "Missing password";

        RegistrationResponseModel registrationResponse =
                step("При регистрации без пароля ответ имеет код статуса 400", () ->
                        given(bodyRequestSpec)
                                .body(registrationBody)
                                .when()
                                .post("/register")
                                .then()
                                .spec(responseSpec)
                                .statusCode(400)
                                .extract().as(RegistrationResponseModel.class));

        step("Получено сообщение об ошибке: " + expectedError, () ->
                assertThat(registrationResponse.getError()).isEqualTo(expectedError));
    }
}
