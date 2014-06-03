package com.clouding2u;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class EnginioUser {
	private String username=new String();
	private  String email=new String();
	private String firstName=new String();
	private String lastName=new String();
	private String id="";
	private String password=new String();
	private static HashMap<String,EnginioUser> mapById=new HashMap<String,EnginioUser>();
	private static HashMap<String,EnginioUser> mapByUsername=new HashMap<String,EnginioUser>();
	private static HashMap<String,EnginioUser> mapByEmail=new HashMap<String,EnginioUser>();
	private static HashMap<String,EnginioUser> mapByFirstName=new HashMap<String,EnginioUser>();
	private static HashMap<String,EnginioUser> mapByLastName=new HashMap<String,EnginioUser>();
	public void setUsername(String username){
		this.username=username;
	}
	public EnginioUser(String username,String email,String firstName,
			String lastName,String password){
		this.username=username;
		this.email=email;
		this.firstName=firstName;
		this.lastName=lastName;
		setPassword(password);
	}
	public String getUsername(){
		return username;
	}
	public void setEmailAddress(String email){
		this.email=email;
	}
	public String  getEmail(){
		return email;
	
	}
	public void setFirstName(String firstName){
		this.firstName=firstName;
	}
	public String getFirstName(){
		return firstName;
	}
	public void setLastName(String lastName){
		this.lastName=lastName;
	}
	public String getLastName(){
		return lastName;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}
	public void setPassword(String password){
	 this.password=password;
	}
	public String getPassword(){
		return password;
	 
	}
	// интерфейс инкапсулирующий добавление заголовка запроса
	private static RequestInterceptor requestInterceptor = new RequestInterceptor() {
		  @Override
		  public void intercept(RequestFacade request) {
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
		    request.addHeader("Enginio-Backend-Id",r.getBackendId());
		    request.addHeader("Enginio-Backend-MasterKey",
		   "BEARER "+r.getAuthToken());
		  }
		};
		private static	class MyErrorHandler implements ErrorHandler {
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
	private	static  RestAdapter restAdapter = new RestAdapter.Builder()
	     .setEndpoint(Root.Service_URI).
	     setRequestInterceptor(requestInterceptor).setErrorHandler(new MyErrorHandler())
	     .build();

	private static EnginioUserInterface operations=
	(EnginioUserInterface) restAdapter.create(EnginioUserInterface.class);
	static public  int insert(EnginioUser user){
		
		JsonObject responseObj=	 operations.CreateUser(user);
		String nameOnCloude=null;
		String id=new String();
		if(responseObj.has("username")) {
		nameOnCloude=responseObj.get("username").getAsString();
		} else return -1;
		if (user.getUsername().equals(nameOnCloude)){
			if(responseObj.has("id")) {
				user.setId(responseObj.get("id").getAsString());
				if(responseObj.has("email")!=true)  return -1;
				if(responseObj.has("firstName")!=true) return -1;
				if (responseObj.has("lastName")!=true) return -1;
				if(responseObj.has("password"))  return -1;
				boolean b=user.getEmail().equals(responseObj.get("email").getAsString());
	if(user.getEmail().equals(responseObj.get("email").getAsString())!=true) return -1;
	if(user.getFirstName().equals(responseObj.get("firstName").getAsString())!=true) return -1;	
	if(user.getLastName().equals(responseObj.get("lastName").getAsString())!=true) return -1;
	
				mapByUsername.put(nameOnCloude,user);
				mapById.put(user.getId(),user);
				mapByEmail.put(responseObj.get("email").getAsString(),user);
				mapByFirstName.put(responseObj.get("firstName").getAsString(),user);
				mapByLastName.put(responseObj.get("lastName").getAsString(),user);
			} else return -1;
		} else return -1;
		  
			return 0;
			 
		 }
	static public  int delete(String id){
		JsonObject responseObj=	 operations.deleteUser(id);
		if(responseObj.entrySet().isEmpty()){ 
		EnginioUser user=	mapById.get(id);
	mapByUsername.remove(user.getUsername());
	mapById.remove(id);
	mapByFirstName.remove(user.getFirstName());
	mapByLastName.remove(user.getLastName());
	mapByEmail.remove(user.getEmail());
			return 0;
			            }
		return -1;
	}
	static public int update(EnginioUser user){
		JsonObject bodyObj=(JsonObject) new GsonBuilder().create().toJsonTree(user);
		bodyObj.remove("id");
		bodyObj.remove("password");
		EnginioUser.operations.updateUser(user.getId(), bodyObj);
		return 0;
		
	}
	static public HashMap<String,EnginioUser> query(UserQueryBy sortBy){
		
		JsonObject responseObj=	operations.getAllUsers();
		if(responseObj.has("results")==true){
			mapById.clear();
			mapByEmail.clear();
			mapByUsername.clear();
			mapByFirstName.clear();
			mapByLastName.clear();
			JsonArray array=(JsonArray) responseObj.get("results");
			Iterator<JsonElement> it=array.iterator();
			 while(it.hasNext()){
				JsonObject jsonObject= it.next().getAsJsonObject();
				String id = null;
				String username;
				String firstName;
				String lastName;
			if(jsonObject.has("id")) id=jsonObject.get("id").getAsString();	
			if(jsonObject.has("username")) username=jsonObject.get("username").getAsString(); else return null;
			if(jsonObject.has("firstName")) firstName=jsonObject.get("firstName").getAsString(); else return null;
			if(jsonObject.has("lastName")) lastName=jsonObject.get("lastName").getAsString(); else return null;
			EnginioUser user=new EnginioUser(username,"",firstName,lastName,"");
			user.setId(id);
			mapById.put(id,user);
			mapByUsername.put(username,user);
			mapByFirstName.put(firstName,user);
			mapByLastName.put(lastName,user);
			 }
		}
		if(sortBy.equals(UserQueryBy.ById)) return mapById;
		if(sortBy.equals(UserQueryBy.ByUsername)) return mapByUsername;
		if(sortBy.equals(UserQueryBy.ByFirstname)) return mapByFirstName;
		if(sortBy.equals(UserQueryBy.byLastname)) return mapByLastName;
		    return null;
		    }
}
