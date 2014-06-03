import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.google.gson.JsonObject;


public interface EnginioUserInterface {
	 @POST("/users")
	 JsonObject CreateUser(@Body EnginioUser user);
	   @GET("/users")  
	 JsonObject getAllUsers();
	   @DELETE("/users/{id}")   
	 JsonObject deleteUser(@Path("id") String id); 
	   @PUT("/users/{id}")
	 JsonObject updateUser(@Path("id") String id,@Body JsonObject jsonGroupName);  
	   
}
