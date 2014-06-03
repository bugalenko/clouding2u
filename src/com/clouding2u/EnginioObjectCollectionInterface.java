package com.clouding2u;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.google.gson.JsonObject;


/**
 * @author Александр Клочко
 *
 */
public interface EnginioObjectCollectionInterface {
	  @POST("/objects/{Type}")
	  JsonObject CreateObject(@Path("Type") String type,@Body JsonObject object);
	  @GET("/objects/{Type}/{id}")
	  JsonObject get(@Path("Type")  String type,@Path("id")  String id);
	  @DELETE("/objects/{Type}/{id}")
	  JsonObject delete(@Path("Type")  String type,@Path("id")  String id);
	  @PUT("/objects/{Type}/{id}")
	  JsonObject update(@Path("Type")  String type,@Path("id")  String id,@Body JsonObject object);
	 //установка правил доступа к обьекту коллекции
	  @POST( "/objects/{Type}/{id}/access")
	  JsonObject setAccessRules(@Path("Type")  String type,@Path("id")  String id,@Body JsonObject accessRules);
	  @PUT( "/objects/{Type}/{id}/access")
	  JsonObject AddAccessPermissions(@Path("Type")  String type,@Path("id")  String id,@Body JsonObject accessPermissions);
	  @PUT( "/objects/{Type}/{id}/access")
	  JsonObject getAccessPermissions(@Path("Type")  String type,@Path("id")  String id);
	  
}
