package com.clouding2u.service;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;



public class TestFiles {

	public static void main(String[] args) throws EnginioObjectIOException, IOException, URISyntaxException {
		// TODO Auto-generated method stub
		FileContentRange range=new FileContentRange(new File("cat.JPG"));
		
		EnginioObjectsCollection.insert(range);
 EnginioFileEntity file1Entity=new EnginioFileEntity(new File("cat.JPG"),range,"photo");//.uploadFile();
  //         JsonObject fileJsonInfo=file1Entity.getFileEntityInfo();
  //         fileJsonInfo.toString();
 //       URI file1URI=file1Entity.getDownloadUrl();
   //   URL url=  file1URI.toURL();
  //  JsonObject jsonRange=      EnginioObjectsCollection.getJsonObject(range);
 //   jsonRange.toString();
 //   file1Entity.downloadFile(file1URI);
     file1Entity.uploadChunkedFile();
    
	}

}
