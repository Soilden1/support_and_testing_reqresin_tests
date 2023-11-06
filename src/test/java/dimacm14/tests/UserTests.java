package dimacm14.tests;

import dimacm14.modeles.*;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;

import static dimacm14.specs.TestSpecs.*;
import static dimacm14.specs.TestSpecs.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Работа с пользователями")
@Tag("Smoke")
@Severity(BLOCKER)
@Owner("dimacm14")
public class UserTests {

    @Story("Получение пользователей")
    @Test(description = "Получить список пользователей")
    void getUserListTest() {
        UserListResponseModel userListResponse =
                step("При получении списка пользователей ответ имеет код статуса 200", () ->
                        given(withoutBodyRequestSpec)
                                .when()
                                .get("/users?page=2")
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(UserListResponseModel.class));

        step("Проверить данные списка пользователей", () -> {
            assertThat(userListResponse.getPage()).isEqualTo(2);
            assertThat(userListResponse.getData()[0].getFirst_name()).isEqualTo("Michael");
            assertThat(userListResponse.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        });
    }

    @Story("Получение пользователей")
    @Test(description = "Получить одного пользователя")
    void getUserTest() {
        UserResponseModel userResponse =
                step("При получении пользователя ответ имеет код статуса 200", () ->
                        given(withoutBodyRequestSpec)
                                .when()
                                .get("/users/2")
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(UserResponseModel.class));

        step("Проверить данные пользователя", () -> {
            assertThat(userResponse.getData().getId()).isEqualTo(2);
            assertThat(userResponse.getData().getFirst_name()).isEqualTo("Janet");
            assertThat(userResponse.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        });
    }

    @Story("Получение пользователей")
    @Test(description = "Получить несуществующего пользователя")
    void getNotExistUserTest() {
        step("При попытке получения несуществующего пользователя ответ имеет код статуса 404", () -> {
            given(withoutBodyRequestSpec)
                    .when()
                    .get("/users/23")
                    .then()
                    .spec(responseSpec)
                    .statusCode(404);
        });
    }

    @Story("Получение пользователей")
    @Test(description = "Получить список пользователей с задержкой")
    void delayedGetUserListTest() {
        UserListResponseModel userList =
                step("При получении списка пользователей с задержкой ответ имеет код статуса 200", () ->
                        given(withoutBodyRequestSpec)
                                .when()
                                .get("/users?delay=3")
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(UserListResponseModel.class));

        step("Проверить данные списка пользователей", () -> {
            assertThat(userList.getPage()).isEqualTo(1);
            assertThat(userList.getData()[0].getFirst_name()).isEqualTo("George");
            assertThat(userList.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        });
    }

    @Story("Создание пользователей")
    @Test(description = "Создать нового пользователя")
    void createUserTest() {
        UserBodyModel createUserBody = new UserBodyModel();
        createUserBody.setName("morpheus");
        createUserBody.setJob("leader");

        CreateUserResponseModel createUserResponse =
                step("При создании нового пользователя с именем и работой ответ имеет код статуса 201", () ->
                        given(bodyRequestSpec)
                                .body(createUserBody)
                                .when()
                                .post("/users")
                                .then()
                                .spec(responseSpec)
                                .statusCode(201)
                                .extract().as(CreateUserResponseModel.class));

        step("Проверить данный нового пользователя", () -> {
            assertThat(createUserResponse.getName()).isEqualTo("morpheus");
            assertThat(createUserResponse.getJob()).isEqualTo("leader");
        });
    }

    @Story("Обновление пользователей")
    @Test(description = "Обновить данные пользователя(put method)")
    void putUpdateUserTest() {
        UserBodyModel updateUserBody = new UserBodyModel();
        updateUserBody.setName("morpheus");
        updateUserBody.setJob("zion resident");

        UpdateUserResponseModel updateUserResponse =
                step("При обновлении данных пользователя ответ имеет код статуса 200", () ->
                        given(bodyRequestSpec)
                                .body(updateUserBody)
                                .when()
                                .put("/users/2")
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(UpdateUserResponseModel.class));

        step("Проверить данные пользователя", () -> {
            assertThat(updateUserResponse.getName()).isEqualTo("morpheus");
            assertThat(updateUserResponse.getJob()).isEqualTo("zion resident");
        });
    }

    @Story("Обновление пользователей")
    @Test(description = "Обновить данные пользователя(patch method)")
    void patchUpdateUserTest() {
        UserBodyModel updateUserBody = new UserBodyModel();
        updateUserBody.setName("morpheus");
        updateUserBody.setJob("zion resident");

        UpdateUserResponseModel updateUserResponse =
                step("При обновлении данных пользователя ответ имеет код статуса 200", () ->
                        given(bodyRequestSpec)
                                .body(updateUserBody)
                                .when()
                                .patch("/users/2")
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(UpdateUserResponseModel.class));

        step("Проверить данные пользователя", () -> {
            assertThat(updateUserResponse.getName()).isEqualTo("morpheus");
            assertThat(updateUserResponse.getJob()).isEqualTo("zion resident");
        });
    }

    @Story("Удаление пользователей")
    @Test(description = "Удалить пользователя")
    void deleteUserTest() {
        step("При удалении пользователя ответ имеет код статуса 204", () ->
                given(withoutBodyRequestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(204));
    }
}
