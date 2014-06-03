package com.clouding2u;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

import com.google.gson.JsonObject;


public interface EnginioUserAuthenticationInterface {
	
	@Headers("Content-Type: application/x-www-form-urlencoded;charset=UTF-8")
	@POST("/auth/oauth2/token")
	JsonObject getAccessToken(@Query( "grant_type") String grandtype,
			@Query( "username")String username,@Query( "password")String password );
	@Headers("Content-Type: application/x-www-form-urlencoded;charset=UTF-8")
	@POST("/auth/oauth2/token")
	JsonObject refreshTokens(@Query( "grant_type") String grandtype,
			@Query( "refresh_token")String refresh_token);
	@Deprecated
	@Headers("Content-Type: application/x-www-form-urlencoded;charset=UTF-8")
	JsonObject revokeAccessToken(@Query( "grant_type") String grandtype,@Query("access_token")String token);
	
	@Headers("Content-Type: application/x-www-form-urlencoded;charset=UTF-8")
	@POST("/auth/oauth2/revoke")
	JsonObject revokeRefreshToken( @Query("token")String token);
    
	
}
