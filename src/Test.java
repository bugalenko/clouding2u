


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;



public class Test {
  public  int count;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
     RequestInterceptor requestInterceptor = new RequestInterceptor() {
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
    	    request.addHeader("Enginio-Backend-Id","534a7e4fe5bde5148c012381");
    	    request.addHeader("Enginio-Backend-MasterKey",
    	   "BEARER "+"r9o698VThQ74rANUbWfRMPVS6cutU8Bbf0Oq7y40+ZkaPCXmiUIeRONCitba2UFoqfJsvvtWauPi44QK0chAhA==");
    	  }
    	};
    	class MyErrorHandler implements ErrorHandler {
    		  @Override public Throwable handleError(RetrofitError cause) {
    		    Response r = cause.getResponse();
    		    if (r != null && r.getStatus() == 400) {
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

     RestAdapter restAdapter = new RestAdapter.Builder()
     .setEndpoint(Root.Service_URI).
     setRequestInterceptor(requestInterceptor).setErrorHandler(new MyErrorHandler())
     .build();
  EnginioUsergroupInterface usergroup=
		  restAdapter.create(EnginioUsergroupInterface.class);
     JsonObject obj=null;
      try {
     obj=usergroup.CreateUsergroup( new EnginioUsergroup("teacher3"));
                               
                   } catch (Exception e){
                	  e.printStackTrace(); 
                   }
                                           
// obj.toString();
JsonObject allgroups=   usergroup.getAllGroups();
HashMap<String,EnginioUsergroup> map=new HashMap<String,EnginioUsergroup>();
JsonElement results=null;
if(allgroups.has("results")==true){
 results= allgroups.get("results");
JsonArray array=	results.getAsJsonArray();
int l=array.size();
Iterator<JsonElement> it=array.iterator();
 while(it.hasNext()){
	JsonElement el= it.next();
	String name=el.getAsJsonObject().get("name").getAsString();
	String id=el.getAsJsonObject().get("id").getAsString();
	EnginioUsergroup group=new EnginioUsergroup(name);
	group.setId(id);
	map.put(name, group);
	
	
 }
};
 JsonObject updateObj=new JsonObject();
  updateObj.addProperty("name", "workers");
  usergroup.updateUsergroup(map.get("teacher").getId(), updateObj);
JsonObject resp=usergroup.deleteUsergroup(map.get("teacher3").getId());
String str=resp.toString();
System.out.println(str);

/*    User u=new User("vasya"); 
    u.getUserDetailsFromCloud(r);
  
try {
	f = new CloudeFile("cat.JPG",u,"photo");
long size=	f.getFile().length();
System.out.println(size);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
FileUploadSpy spy=new FileUploadSpy();
spy.setCloudeFile(f);
// добавление записи о файле в обьект
  f.sendFile(r);
  boolean fileUploaded=false;
  do {
 JsonObject result= spy.uploadChunk(r);
 boolean errors= result.has("errors");
 if (errors==true){
	 System.out.println("Ошибка в передаче файла!!!!!!");
	 
 } } while(fileUploaded);*/
		
	/*	TestCloudeClass cloude=new TestCloudeClass();
		cloude.getData().put("code",255);
		cloude.getData().put("itemname", "cat");
		JsonElement el=cloude.toCloudeObject();
		
		el.toString();
	PostOperation operation=new PostOperation();
	operation.operation();
	Usergroups group=new Usergroups("manager");
	try {
	JsonElement obj2=	group.operations.get("put").operation(group);
    System.out.println(obj.toString());
	} catch (WrongOperationParametr e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 BaseOperation op=new BaseOperation(HttpPost.class);
		op.toString();*/
	}
	
	
	
	
	

}
