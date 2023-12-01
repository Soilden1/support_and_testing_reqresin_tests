package dimacm14.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.BLOCKER;

@Feature("Регистрация")
@Tag("Smoke")
@Severity(BLOCKER)
@Owner("dimacm14")
public class RegistrationTests extends BaseReqresInTest {

    @Story("Успешная регистрация")
    @Test(description = "Регистрация с электронной почтой и паролем")
    void successfulRegistrationTest() {
        REGISTRATION_STEPS.successfulRegistration();
    }

    @Story("Неуспешная регистрация")
    @Test(description = "Регистрация без пароля")
    void unsuccessfulMissingPasswordRegistrationTest() {
        REGISTRATION_STEPS.unsuccessfulMissingPasswordRegistration();
    }
}
