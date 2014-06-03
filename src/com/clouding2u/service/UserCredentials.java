package com.clouding2u.service;
import java.io.FileNotFoundException;
import java.io.IOException;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.google.gson.JsonObject;


public class UserCredentials {
private Root root;
private String  secret_token=new String();	
 private String	access_token=new String();
 private String refresh_token=new String();
 private EnginioUser user;
 private int expires_in=0;
 
 public UserCredentials(boolean isAdmin,EnginioUser user) throws Exception{
	 if (isAdmin==true){
			  Root r=null;
				// CloudeFile f=null;
			     try {
					 r=new Root();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     secret_token=r.getAuthToken();
	 } else {
		
		 // add typed exception
		 if (user==null) throw new Exception();
	 }
 }
 public String getAccessToken(){
	 JsonObject responseObj=	operations.getAccessToken("password",
			 user.getUsername(),user.getPassword());
	 if (responseObj.has("access_token")) this.access_token=responseObj.get("access_token").getAsString();
	 else return null;
	 if (responseObj.has("refresh_token")) this.refresh_token=responseObj.get("refresh_token").getAsString();
	 else return null;
	 if (responseObj.has("expires_in")) this.expires_in=responseObj.get("expires_in").getAsInt();
	 else return null;	
	 return access_token;
	
	 
 }
public  String refreshTokens(){
	 JsonObject responseObj=	operations.refreshTokens("refresh_token",
			 this.refresh_token);
	 if (responseObj.has("access_token")) this.access_token=responseObj.get("access_token").getAsString();
	 else return null;
	 if (responseObj.has("refresh_token")) this.refresh_token=responseObj.get("refresh_token").getAsString();
	 else return null;
	 if (responseObj.has("expires_in")) this.expires_in=responseObj.get("expires_in").getAsInt();
	 else return null;	
	 return access_token;
	
 }
public int removeTokens(){
JsonObject responseObj=operations.revokeRefreshToken(refresh_token);
if(responseObj.entrySet().isEmpty()!=true) return -1;
//responseObj=operations.revokeAccessToken("access_token",access_token);
	return 0;
	
}

  private  RequestInterceptor requestInterceptor = new RequestInterceptor() {
	  @Override
	  public void intercept(RequestFacade request) {
	    request.addHeader("Enginio-Backend-Id",root.getBackendId());
	    request.addHeader("Accept", "application/json");
	//    request.addQueryParam("grant_type","password");
	//    request.addQueryParam("username",user.getUsername());
	 //   request.addQueryParam("password", user.getPassword());
	  }
	};
	private class MyErrorHandler implements ErrorHandler {
		  @Override public Throwable handleError(RetrofitError cause) {
		Object objErrors=	  cause.getBody();
		    Response r = cause.getResponse();
		    if (r != null && r.getStatus() == 400&&r.getStatus()==404) {
		      try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		    return cause;
		  }
		}
	private	 RestAdapter restAdapter = new RestAdapter.Builder()
    .setEndpoint(Root.Service_URI).
    setRequestInterceptor(requestInterceptor).setErrorHandler(new MyErrorHandler())
    .build();

private  EnginioUserAuthenticationInterface operations=
restAdapter.create(EnginioUserAuthenticationInterface.class);

}
