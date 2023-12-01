package dimacm14.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.BLOCKER;

@Feature("Работа с пользователями")
@Tag("Smoke")
@Severity(BLOCKER)
@Owner("dimacm14")
public class UserTests extends BaseReqresInTest {

    @Story("Получение пользователей")
    @Test(description = "Получить список пользователей")
    void getUserListTest() {
        USER_STEPS.getUserList();
    }

    @Story("Получение пользователей")
    @Test(description = "Получить одного пользователя")
    void getUserTest() {
        USER_STEPS.getUser();
    }

    @Story("Получение пользователей")
    @Test(description = "Получить несуществующего пользователя")
    void getNotExistUserTest() {
        USER_STEPS.getNotExistUser();
    }

    @Story("Получение пользователей")
    @Test(description = "Получить список пользователей с задержкой")
    void delayedGetUserListTest() {
        USER_STEPS.delayedGetUserList();
    }

    @Story("Создание пользователей")
    @Test(description = "Создать нового пользователя")
    void createUserTest() {
        USER_STEPS.createUser();
    }

    @Story("Обновление пользователей")
    @Test(description = "Обновить данные пользователя(put method)")
    void putUpdateUserTest() {
        USER_STEPS.putUpdateUser();
    }

    @Story("Обновление пользователей")
    @Test(description = "Обновить данные пользователя(patch method)")
    void patchUpdateUserTest() {
        USER_STEPS.patchUpdateUser();
    }

    @Story("Удаление пользователей")
    @Test(description = "Удалить пользователя")
    void deleteUserTest() {
        USER_STEPS.deleteUser();
    }
}
