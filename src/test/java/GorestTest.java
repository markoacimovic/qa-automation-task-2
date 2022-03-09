import com.google.gson.Gson;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GorestTest {

    String postEndpoint = "https://gorest.co.in";
    User user;
    Gson gson = new Gson();
    String userJson;
    HttpRequest httpRequest;
    HttpResponse httpResponse;
    HttpClient httpClient = HttpClient.newHttpClient();

    @Test
    public void test1createUser() throws IOException, InterruptedException {

        //creating new user
        user = new User("Jovan Jovanovic", "male", "jjovanoviczmaj@mail.com", "active");
        //parsing user to json
        userJson = gson.toJson(user);
        //building post http request with Auth header
        httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint + "/public/v2/users"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer c89a06e7c8472236c7a2868f366897303be886300423ff2c125a281e1e6c1fa2") // header for auth
                .POST(HttpRequest.BodyPublishers.ofString(userJson))
                .build();
        httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        //getting user from response
        user = gson.fromJson(httpResponse.body().toString(), User.class);
        //Set user id
        UtilClass.ID = user.getId();
        Assert.assertTrue(httpResponse.statusCode() == 201);
        System.out.println("User is created");
    }

    @Test
    public void test2updateUser() throws IOException, InterruptedException {
        //creating new user
        user = new User("Jovan Jovanovic Zmaj", "male", "jjovanoviczmaj@mail.com", "active");
        //parsing user to json
        userJson = gson.toJson(user);
        //building put http request with Auth header
        httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint + "/public/v2/users/" + UtilClass.ID))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer c89a06e7c8472236c7a2868f366897303be886300423ff2c125a281e1e6c1fa2") // header for auth
                .PUT(HttpRequest.BodyPublishers.ofString(userJson))
                .build();
        httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assert.assertTrue(httpResponse.statusCode() == 200);
        System.out.println("User is updated");
    }

    @Test
    public void test3deleteUser() throws IOException, InterruptedException {

        //building delete http request with Auth header
        httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint + "/public/v2/users/" + UtilClass.ID))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer c89a06e7c8472236c7a2868f366897303be886300423ff2c125a281e1e6c1fa2") // header for auth
                .DELETE()
                .build();
        httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assert.assertTrue(httpResponse.statusCode() == 204);
        System.out.println("User is deleted");
    }
}
