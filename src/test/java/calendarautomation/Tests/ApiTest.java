package calendarautomation.Tests;

import io.restassured.response.Response;
import calendarautomation.utils.ApiClient;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTest {
    @Test(groups = "positive", priority = 7)

    public void getUserReturnsCorrectData() {
        ApiClient apiClient = new ApiClient();
        Response response = apiClient.getUser(2);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("data.id"), 2);
    }
}

