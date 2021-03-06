package org.test.task;

import static org.testng.Assert.assertTrue;

import org.test.task.pages.FormPage;
import org.testng.annotations.*;

@Listeners({org.test.task.utils.DbTestListener.class})

public class FormTest extends BaseTest {
    public FormPage formPage;

    @DataProvider(name = "correctData")
    public static Object[][] dataProviderDataMethod() {
        return new Object[][]{
                {"T", "tan@gmail.com", "Kyiv", "+380937675277", 2},
                {"Tetiana 21", "tan_vesela@meta.ua", "Lviv, Trutenko street 51, office 5", "0937675277", 1},
                {"Jo", "`1234567890-=~!^_+@gmail", "Dnipro str.Lomachenko 3/1, off", "093-767-52-77", 0},
                {"Vasylyna Ivanivna Vesela", "tan@gmail.com", "`1234567890-=~!@#$%^&*()_+", "+38(093)767-52-77", 2},
                {"Tetiana Vesela", "tan_kola@meta.com", " ", "", 2}
        };
    }

    @DataProvider(name = "incorrectData")
    public static Object[][] dataProviderDataMethod2() {
        return new Object[][]{
                {"Vitaliy", "tan", "Kyiv", "+380937675277", 2},
                {"", "tan_vesela@meta.ua", "Lviv, Trutenko street 51, office 5", "0937675277", 1},
                {" ", "dan@gmail", "Dnipro str.Lomachenko 3/1, off", "093-767-52-77", 0},
                {"Taras Pavlovich", "tan@", "`1234567890-=~!@#$%^&*()_+", "+38(093)767-52-77", 2},
                {"Oleksandr", "", "Odesa!", "+38077777777", 2},
                {"Taras Oleksandr Yuiru", " ", "`1234567890-=~!@#$%^&*()_+", "+38(093)767-52-77", 0},
        };
    }

    @BeforeMethod
    public void openPage() {
        formPage = new FormPage(driver);
        formPage.open();
    }

    @Test
    public void emptyForm() {
        formPage.pressSubmitButton();
        assertTrue(formPage.isTextForMessagePresent("Did you fill in the form properly?"));
        assertTrue(formPage.isTextForNamePresent("Please fill out this field."));
        assertTrue(formPage.isTextForEmailPresent("Please fill out this field."));
    }

    @Test(dataProvider = "correctData")
    public void correctDataForm(String name, String email, String address, String phone, int index) {
        formPage.fillOutFullName(name);
        formPage.fillOutEmail(email);
        formPage.fillOutAdress(address);
        formPage.fillOutPhone(phone);
        formPage.selectBoxMenu(index);
        formPage.pressSubmitButton();
        assertTrue(formPage.isTextForMessagePresent("Message Submitted!"));
    }

    @Test(dataProvider = "incorrectData")
    public void incorrectDataForm(String name, String email, String address, String phone, int index) {
        formPage.fillOutFullName(name);
        formPage.fillOutEmail(email);
        formPage.fillOutAdress(address);
        formPage.fillOutPhone(phone);
        formPage.selectBoxMenu(index);
        formPage.pressSubmitButton();
        assertTrue(formPage.isTextForMessagePresent("Did you fill in the form properly?"));
    }
}
