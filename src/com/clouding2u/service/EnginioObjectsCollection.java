package com.clouding2u.service;
import java.io.FileNotFoundException;
import java.io.IOException;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.google.gson.JsonObject;



public class EnginioObjectsCollection {
	public enum AccessCredentials{
		readFromCollection("read"),
		updateCollection("update"),
		deleteFromCollection("delete"),
		administrator("admin");
		private AccessCredentials(String AccessRules) {
			// TODO Auto-generated constructor stub
		}
	};
	
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
	private static EnginioObjectCollectionInterface operations=
			restAdapter.create(EnginioObjectCollectionInterface.class);
	//ставка обьекта в коллецию
	static public int insert(AbstractEnginioObject EnginioObject) throws EnginioObjectIOException{
	JsonObject src=	EnginioObject.toJson();
JsonObject responseObj=	operations.CreateObject(EnginioObject.getType(),src);
 if (responseObj.has("id")) EnginioObject.setId(responseObj.get("id").getAsString()); else return -1;
 if (responseObj.has("createdAt")) EnginioObject.setCreatedAt(responseObj.get("createdAt").getAsString()); else return -1;
 if (responseObj.has("updatedAt")) EnginioObject.setUpdateAt(responseObj.get("updatedAt").getAsString()); else return -1;	
 String objectType=null;
 if (responseObj.has("objectType")) objectType= responseObj.get("objectType").getAsString(); else return -1;
String str=null;
if(objectType.startsWith("objects.")) str=objectType.split("objects.")[1]; else return -1;
 if(EnginioObject.getType().equals(str)){ 
	 EnginioObject.setType(str);
	 return 0;}
 return 1;
		
	}
	static public Object get(AbstractEnginioObject object) throws EnginioObjectIOException{
JsonObject responseObject=	 operations.get(object.getType(),object.getId());
    
		return object.fromJson(responseObject);
		
	}
	static public JsonObject getJsonObject(AbstractEnginioObject object) throws EnginioObjectIOException{
		JsonObject responseObject=	 operations.get(object.getType(),object.getId());
		    
				return responseObject;
				
			}
	static public int delete(AbstractEnginioObject object) throws EnginioObjectIOException{
JsonObject responseObject=	 operations.delete(object.getType(),object.getId());
 boolean empty=  responseObject.entrySet().isEmpty();
if (empty)		return 0;
return -1;
		
	}	
	static public int update(AbstractEnginioObject object) throws EnginioObjectIOException{
		  
		JsonObject bodyObj=object.toJson();
	JsonObject responseObject=	EnginioObjectsCollection.operations.update(object.getType(),object.id, bodyObj);
	String id=null;
	if (responseObject.has("id")) id=responseObject.get("id").getAsString(); else return -1;
	if (id.equals(object.getId())!=true) return -1;
		return 0;
		
	}
	static JsonObject setSingleAccessPermissions(EnginioObjectPoliciesFabric pFabric,AbstractAccessControlPoliciesFabric.AccessPermissionsType permissionsType ){
	JsonObject responseObj=	operations.AddAccessPermissions(pFabric.getEnginioObject().getType(),
				pFabric.getEnginioObject().getId()
				, pFabric.makeSinglePermission(permissionsType));
		return null;
		
	}
	static JsonObject getAccessPermissions(AbstractEnginioObject object){
	return 	operations.getAccessPermissions(object.getType(), object.getId());
	}
}
