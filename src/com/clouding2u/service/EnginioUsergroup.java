package com.clouding2u.service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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



public class EnginioUsergroup {
private String name;
private String id="";
//карта пользовательских груп
private static HashMap<String,EnginioUsergroup> mapById=new HashMap<String,EnginioUsergroup>();
private static HashMap<String,EnginioUsergroup> mapByName=new HashMap<String,EnginioUsergroup>();
public void setName(String name){
	this.name=name;
}
public String getName(){
	return name;
	
}
public void setId(String id){
	this.id=id;
	
}
public String getId(){
	return id;
	
}
 private HashMap<String,EnginioUsergroup>getLocalListOfUsergroupById(){
	return mapById;
	 
 }

public EnginioUsergroup(String name) {
	// TODO Auto-generated constructor stub
	this.name=name;
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
	// обработчик ошибок
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

private static EnginioUsergroupInterface operations=
restAdapter.create(EnginioUsergroupInterface.class);
	 
static public  int insert(EnginioUsergroup group){
	
JsonObject responseObj=	 operations.CreateUsergroup(group);
String nameOnCloude=null;
String id=new String();
if(responseObj.has("name")) {
nameOnCloude=responseObj.get("name").getAsString();
} else return -1;
if (group.getName().equals(nameOnCloude)){
	if(responseObj.has("id")) {
		group.setId(responseObj.get("id").getAsString());
		mapByName.put(nameOnCloude, group);
		mapById.put(group.getId(),group);
		
		
	} else return -1;
} else return -1;
  
	return 0;
	 
 }
static public  int delete(String id){
	JsonObject responseObj=	 operations.deleteUsergroup(id);
	if(responseObj.entrySet().isEmpty()){ 
	EnginioUsergroup group=	mapById.get(id);
	mapByName.remove(group.getName());
	mapById.remove(id);
		return 0;
		            }
	return -1;
}
static public HashMap<String,EnginioUsergroup> query(boolean getById){
	
JsonObject responseObj=	operations.getAllGroups();
if(responseObj.has("results")==true){
	mapById.clear();
	mapByName.clear();
	JsonArray array=(JsonArray) responseObj.get("results");
	int l=array.size();
	Iterator<JsonElement> it=array.iterator();
	 while(it.hasNext()){
		JsonElement el= it.next();
		String name=el.getAsJsonObject().get("name").getAsString();
		String id=el.getAsJsonObject().get("id").getAsString();
		EnginioUsergroup group=new EnginioUsergroup(name);
		group.setId(id);
		if (getById)
		mapById.put(id, group); else mapByName.put(name, group);
	 }
	
}
 if (getById) return mapById; else
    return mapByName;
    }

static public int update(EnginioUsergroup group){
	JsonObject bodyObj=(JsonObject) new GsonBuilder().create().toJsonTree(group);
	bodyObj.remove("id");
	EnginioUsergroup.operations.updateUsergroup(group.id, bodyObj);
	return 0;
	
}
static public int insertUser(EnginioUser user,EnginioUsergroup group){
	JsonObject bodyObj=(JsonObject) new GsonBuilder().create().toJsonTree(user);
	JsonObject responseObj=operations.addUser(group.getId(),bodyObj);
	//добавить десириализацию
	return 0;
	
}
//??????????????????????????????????????????????????????????????!!!!!!!!!!!!!!!!!!
static public int deleteUser(EnginioUser user,EnginioUsergroup group){
	JsonObject bodyObj=(JsonObject) new GsonBuilder().create().toJsonTree(user);
	JsonObject responseObj=operations.deleteUser(group.getId(),bodyObj);
	if(responseObj.entrySet().isEmpty()!=true) return -1;
	//добавить десириализацию
	return 0;
	
}
static public ArrayList<String> getUsers(EnginioUsergroup usergroup){
	ArrayList<String> list=null;
	JsonObject reponseObject=operations.getUsergroupMembers(usergroup.getId());
	if(reponseObject.has("results")){
		list=new ArrayList<String>();
		JsonArray jsonArray=reponseObject.get("results").getAsJsonArray();
		Iterator<JsonElement> it=jsonArray.iterator();
		while(it.hasNext()){
		JsonElement el=	it.next();
	if	(el.getAsJsonObject().has("id")) list.add(el.getAsJsonObject().get("id").getAsString()); else return null;
		}
	} else return null;
	return list;
	
}
}
