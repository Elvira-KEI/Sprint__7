import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierGenerator;
import org.example.Credentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class CourierPositiveTest {
    private CourierClient courierClient;
    private Courier courier;
    private int id;
    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();}
    @After
    public void cleanUp(){
        courierClient.delete(id);}
    @Test
    @DisplayName("Check response when courier is created" )
    public void courierCanBeCreated(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");
        int actualStatusCode = responseCreate.extract().statusCode();
        boolean isCourierCreated = responseCreate.extract().path("ok");
        assertEquals("Status Code incorrect",actualStatusCode, SC_CREATED);
        assertTrue("Expected true",isCourierCreated);    }
    @Test
    @DisplayName("Check response when courier is logged in" )
    public void courierCanBeLoginAndCheckResponse(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");
        int actualStatusCode = responseLogin.extract().statusCode();
        assertEquals("Status Code incorrect",actualStatusCode,SC_OK);
        assertThat("Expected courier is logged In",id, notNullValue());
    }
}

