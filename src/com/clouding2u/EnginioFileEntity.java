package com.clouding2u;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;

import org.apache.tika.Tika;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


public class EnginioFileEntity {
	private String propertyName=new String();
	private TypedFile file;
	private String createdAt=new String();
	private String fileId=new String();
	private AbstractEnginioObject enginioObjectWithFiles;
	public EnginioFileEntity(File file,AbstractEnginioObject enginioObject,String propertyName ) throws IOException {
		// TODO Auto-generated constructor stub
		if (file==null) throw new IOException();
		if (file.exists()!=true) throw new IOException();
		if(file.isFile()==false) throw new IOException();
		if(file.canRead()!=true) throw new IOException();
	String mimeType=	new Tika().detect(file);
	TypedFile tfile=new TypedFile(mimeType, file);
	this.file=tfile;
	enginioObjectWithFiles=enginioObject;
	this.propertyName=propertyName;
	
	}
	private  RequestInterceptor requestInterceptor = new RequestInterceptor() {
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
	//request.addHeader("Content-Type", "multipart/form-data ");
	     //request.addHeader("Accept", "application/json");
	 
		  }
		};
		// обработчик ошибок
	private class MyErrorHandler implements ErrorHandler {
			  @Override public Throwable handleError(RetrofitError cause) {
			Object objErrors=	  cause.getBody();
		 System.out.println(objErrors.toString());
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

	private  EnginioFileEntityInterface operations=
	restAdapter.create(EnginioFileEntityInterface.class);
	
     String getFileId(){
		return createdAt;
    	 
     }
public  EnginioFileEntity uploadFile() throws FileNotFoundException, IOException{

	 JsonObject object=new JsonObject();
     object.addProperty("id", enginioObjectWithFiles.getId());
     String objectType2= "objects."+enginioObjectWithFiles.getType();
     object.addProperty("objectType",objectType2);
     object.addProperty("propertyName",propertyName);
		JsonObject fileObject=new JsonObject();
		fileObject.addProperty("fileName",file.fileName());
		JsonObject uploadJson=new JsonObject();
     uploadJson.add("targetFileProperty",new GsonBuilder().create().toJsonTree(object));
     uploadJson.add("file",new GsonBuilder().create().toJsonTree(fileObject));
     
     String str=new GsonBuilder().create().toJson(uploadJson);
	MultipartTypedOutput output=new MultipartTypedOutput();
	output.addPart("object",new TypedString(str));
	output.addPart("file",file);

JsonObject responseObj=	operations.uploadFile( output);
 if(responseObj.has("status")) {
	 String status=responseObj.get("status").getAsString();
	 if(status.equals("complete")) {
		 if (responseObj.has("id")) fileId=responseObj.get("id").getAsString();
		 else return null;
		 if (responseObj.has("createdAt")) createdAt=responseObj.get("createdAt").getAsString();
		 else return null;
	 } else return null;
 }
	return this;
 }
 public JsonObject getFileEntityInfo() throws FileNotFoundException{
	    if(this.fileId.isEmpty()) throw new FileNotFoundException();
	return operations.getFileInfo(this.fileId);
	 
 }
 public URI getDownloadUrl() throws FileNotFoundException, URISyntaxException{
	 if(this.fileId.isEmpty()) throw new FileNotFoundException();
     JsonObject urlJson=	operations.getURL(fileId);
     URI url=null;
     if (urlJson.has("expiringUrl")) url=new URI(urlJson.get("expiringUrl").getAsString());
	 return url;
 }

@SuppressWarnings("unused")
public int downloadFile(URI downloadFileURI) throws URISyntaxException, MalformedURLException, IOException{
	// UrlConnectionClient client=new UrlConnectionClient();
	 int cnunk_size=4096;
	 URLConnection connection=downloadFileURI.toURL().openConnection();
 File    file = this.file.file();
 FileOutputStream   fos = new FileOutputStream(file);
 InputStream    inputStream =connection.getInputStream();
   int  totalSize = connection.getContentLength();
int downloadedSize = 0;
   byte[] buffer = new byte[cnunk_size];
   int bufferLength = 0;
    try{
     // читаем со входа и пишем в выход, 
     
     while ((bufferLength = inputStream.read(buffer)) > 0) {
      fos.write(buffer, 0, bufferLength);
      downloadedSize += bufferLength;
     }
 
     fos.close();
     inputStream.close();
 
     return 0;
    } catch (MalformedURLException e) {
     e.printStackTrace();
   
    } catch (IOException e) {
     e.printStackTrace();
  
    }
 
    return -1;
 }
 //запускать в отдельном потоке
 @SuppressWarnings("unused")
public int androidDownloadFile(URI downloadFileURI) 
		 throws URISyntaxException, MalformedURLException, IOException{
	 
		 int cnunk_size=4096;
		 URLConnection connection=downloadFileURI.toURL().openConnection();
	 File    file = this.file.file();
	 FileOutputStream   fos = new FileOutputStream(file);
	 InputStream    inputStream =connection.getInputStream();
	   int  totalSize = connection.getContentLength();
	   int downloadedSize = 0;
	   byte[] buffer = new byte[cnunk_size];
	   int bufferLength = 0;
	try{
	   synchronized (this.file) {
         while (totalSize!=downloadedSize) {
            // obj.wait(timeout);
          // Perform action appropriate to condition
        	 fos.write(buffer, 0, bufferLength);
             downloadedSize += bufferLength;
           Handler  h=new Handler();
           Message message=new Message();
           Bundle data=new Bundle();
           data.putString("downloadingFile",this.file.fileName());
           data.putInt("totalSize",totalSize);
           data.putInt("downloadedSize",downloadedSize);
            message.setData(data);
            h.sendMessage(message);
         }
	          }
	 
	 fos.close();
     inputStream.close();
 
     return 0;
    } catch (MalformedURLException e) {
     e.printStackTrace();
   
    } catch (IOException e) {
     e.printStackTrace();
			return 0;
 
 
 } 
	return -1;
	
    }
  public int uploadChunkedFile() throws FileNotFoundException{
	  FileContentRange range=new FileContentRange(this.file.file());
	  JsonObject object=new JsonObject();
	     object.addProperty("id", enginioObjectWithFiles.getId());
	     String objectType2= "objects."+enginioObjectWithFiles.getType();
	     object.addProperty("objectType",objectType2);
	     object.addProperty("propertyName",propertyName);
			JsonObject fileObject=new JsonObject();
			fileObject.addProperty("fileName",file.fileName());
			JsonObject uploadJson=new JsonObject();
	     uploadJson.add("targetFileProperty",new GsonBuilder().create().toJsonTree(object));
	     uploadJson.add("file",new GsonBuilder().create().toJsonTree(fileObject));
	     JsonObject responseObject=operations.insertChunkedFileProperty(uploadJson);
	     if(responseObject.has("status")) {
	    	 String status=responseObject.get("status").getAsString();
	    	 if(status.equals("empty")) {
	    		 if (responseObject.has("id")) fileId=responseObject.get("id").getAsString();
	    		 else return -1;
	    		 if (responseObject.has("createdAt")) createdAt=responseObject.get("createdAt").getAsString();
	    		 else return -1;
	    	 } else return -1;
	     }
	     
	     String status="";
	     do{
	    UploadData uploadData=range.getUploadChunk();
	    String contentRange=uploadData.getHeader();
	    TypedByteArray data=new TypedByteArray(null,uploadData.getData() );
	    JsonObject responseJson=operations.chunkedUpload(contentRange,this.fileId,data);
	    if(responseJson.has("status")) 
	    	 status=responseJson.get("status").getAsString(); else return -1;
	     } while(status.equals("complete")!=true);
	      
		return 0;
	          
	
	  
  }
 }
