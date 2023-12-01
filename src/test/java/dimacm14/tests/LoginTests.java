package dimacm14.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.BLOCKER;

@Feature("Авторизация")
@Tag("Smoke")
@Severity(BLOCKER)
@Owner("dimacm14")
public class LoginTests extends BaseReqresInTest {

    @Story("Успешная авторизация")
    @Test(description = "Авторизация с электронной почтой и паролем")
    void successfulLoginTest() {
        LOGIN_STEPS.successfulLogin();
    }

    @Story("Неуспешная авторизация")
    @Test(description = "Авторизация без пароля")
    void unsuccessfulMissingPasswordLoginTest() {
        LOGIN_STEPS.unsuccessfulMissingPasswordLogin();
    }
}
