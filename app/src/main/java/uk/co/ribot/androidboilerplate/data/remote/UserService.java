package uk.co.ribot.androidboilerplate.data.remote;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;
import uk.co.ribot.androidboilerplate.data.model.User;

public interface UserService {

    String ENDPOINT = "http://172.26.142.29:9000/api";

    @GET("/users/{userName}")
    Observable<User> getUser(@Path("userName") String userName);

    @PUT("/users")
    Observable<User> createOrUpdate(@Body User user);
}
