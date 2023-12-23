package dimacm14.steps;

import dimacm14.modeles.*;
import io.qameta.allure.*;

import static dimacm14.specs.TestSpecs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps {

    @Step("Получить список пользователей")
    public void getUserList() {
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
            assertThat(userListResponse.getData()[0].getFirstName()).isEqualTo("Michael");
            assertThat(userListResponse.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        });
    }

    @Step("Получить одного пользователя")
    public void getUser() {
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
            assertThat(userResponse.getData().getFirstName()).isEqualTo("Janet");
            assertThat(userResponse.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        });
    }

    @Step("Получить несуществующего пользователя")
    public void getNotExistUser() {
        step("При попытке получения несуществующего пользователя ответ имеет код статуса 404", () -> {
            given(withoutBodyRequestSpec)
                    .when()
                    .get("/users/23")
                    .then()
                    .spec(responseSpec)
                    .statusCode(404);
        });
    }

    @Step("Получить список пользователей с задержкой")
    public void delayedGetUserList() {
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
            assertThat(userList.getData()[0].getFirstName()).isEqualTo("George");
            assertThat(userList.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        });
    }

    @Step("Создать нового пользователя")
    public void createUser() {
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

    @Step("Обновить данные пользователя(put method)")
    public void putUpdateUser() {
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

    @Step("Обновить данные пользователя(patch method)")
    public void patchUpdateUser() {
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

    @Step("Удалить пользователя")
    public void deleteUser() {
        step("При удалении пользователя ответ имеет код статуса 204", () ->
                given(withoutBodyRequestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(204));
    }
}
