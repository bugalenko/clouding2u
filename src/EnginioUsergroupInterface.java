import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.RestMethod;

import com.google.gson.JsonObject;

public interface EnginioUsergroupInterface {
  @POST("/usergroups")
JsonObject CreateUsergroup(@Body EnginioUsergroup group);
  @GET("/usergroups")  
JsonObject getAllGroups();
  @DELETE("/usergroups/{id}")   
JsonObject deleteUsergroup(@Path("id") String id); 
  @PUT("/usergroups/{id}")
JsonObject updateUsergroup(@Path("id") String id,@Body JsonObject jsonGroupName);
  @POST("/usergroups/{id}/members") 
JsonObject addUser(@Path("id") String usergroup_id,@Body JsonObject jsonGroupName);
  @Headers("Content-Type: application/json")
  //под вопросом
  @DELETE("/usergroups/{id}/members") 
  JsonObject deleteUser(@Path("id") String usergroup_id,@Body JsonObject jsonUser);

  @GET("/usergroups/{id}/members") 
JsonObject getUsergroupMembers(@Path("id") String usergroup_id);
    

}
