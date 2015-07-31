package uk.co.ribot.androidboilerplate.data.remote;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;
import uk.co.ribot.androidboilerplate.data.model.User;

public interface UserService {

    String ENDPOINT = "http://172.26.142.29:9000/api";

    @GET("/users/{registerId}")
    Observable<User> getUser(@Path("registerId") String registerId);

    @PUT("/users")
    Observable<User> createOrUpdate(@Body User user);
}
