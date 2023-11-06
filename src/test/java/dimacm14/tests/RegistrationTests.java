package dimacm14.tests;

import dimacm14.modeles.RegistrationBodyModel;
import dimacm14.modeles.RegistrationResponseModel;
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

@Feature("Регистрация")
@Tag("Smoke")
@Severity(BLOCKER)
@Owner("dimacm14")
public class RegistrationTests {

    @Story("Успешная регистрация")
    @Test(description = "Регистрация с электронной почтой и паролем")
    void successfulRegistrationTest() {
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

    @Story("Неуспешная регистрация")
    @Test(description = "Регистрация без пароля")
    void unsuccessfulMissingPasswordRegistrationTest() {
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
