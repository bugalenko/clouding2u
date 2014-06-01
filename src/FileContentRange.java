import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class FileContentRange extends AbstractEnginioObject{
	//размер куска передаваемых данных	
	 private static int chunkSize=4096;
	 //данные из файла
	 private String fileId=new String();
	 private byte[] fileData;
	 private int chunkCount=0;
	 private int currentChunk=-1;
	 private long chunkStart;
	 private long chunkEnd;
	 private long totalFileSize;
	 //заменить конструктором
	 FileContentRange(File uploadFile) throws FileNotFoundException{
		 //добавить исключение если файл не существует или изменился
	@SuppressWarnings("rawtypes")
	Class cl=this.getClass();
String name=	cl.getCanonicalName();
  this.setType(name);
		 try {
			 totalFileSize=uploadFile.length();
			 fileData=new byte[(int) totalFileSize];
			new FileInputStream(uploadFile).read(fileData);
			chunkCount=(int)(totalFileSize/chunkSize);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 public void  setFileId(String fileId){
		 this.fileId=fileId;
	 }
	 public String  getFileId(){
		return fileId;
		 
	 }
	 private byte[] getDataForUpload(){
		 //добавить проверку
		 
		return Arrays.copyOfRange(fileData, (int)chunkStart,(int) chunkEnd);
		 
	 }

	 public void setTotalSize(long size){
		totalFileSize=size; 
	 }
	 public long getTotalSize(){
		return totalFileSize;
	 }
	 public void setChunkCount(int count){
		 chunkCount=count;
	 }
	 //установка диапазано передаваемой части файла
	 private void setChunk(){
		 chunkStart=currentChunk*chunkSize;
		 if(currentChunk!=chunkCount)
		chunkEnd=chunkStart+chunkSize;// не отнимаю единицу
		 else chunkEnd=getTotalSize();
	 }
	 public void setChunkSize(int size) throws ChunkSizeException {
		if(size>(totalFileSize/10)||size<=0) {
			ChunkSizeException e=new ChunkSizeException(size, totalFileSize);
			throw e;
		}else
		 chunkSize=size;
		 
	 }
	 public int getChunkSize(){
		return chunkSize;
		 
	 }
	 public void organizeChunk(File file){

		if((totalFileSize%chunkSize)==0) 
			chunkCount=(int) (totalFileSize/chunkSize);
		                else  chunkCount=((int) (totalFileSize/chunkSize)+1);
	 }
	private String nextChunk(){
		 StringBuilder builder = null;
		if(currentChunk<chunkCount) {currentChunk++;
		 setChunk();
		 //формирование заголовка о передаваемых данных
		 //Content-Range: bytes 0-1024/2048
		  builder=new StringBuilder();
		 builder.append("Content-Range: bytes " );
		 builder.append(chunkStart);
		 builder.append("-");
		 builder.append(chunkEnd);
		 builder.append("/");
		 builder.append(totalFileSize);}
		return builder.toString() ;
		 
	 }
	 // передача информации для необходимой для загрузки
	  public UploadData getUploadChunk(){
		  String header=nextChunk();
		return new UploadData(header,getDataForUpload());
		  
	  }
	@Override
	public JsonObject toJson() throws EnginioObjectIOException {
		// TODO Auto-generated method stub
	JsonObject src=	 new GsonBuilder().create().toJsonTree(this).getAsJsonObject();
	src.remove("id");
	src.remove("fileData");
	src.remove("createdAt");
	//src.remove("type");
	src.remove("createdAt");
	src.remove("updateAt");
	src.remove("objectType");
		return src;
	}

	
	@Override
	public Object fromJson(JsonObject src) throws EnginioObjectIOException {
		// TODO Auto-generated method stub
	FileContentRange fcr=	new GsonBuilder().create().fromJson(src.toString(), FileContentRange.class);
	fcr.setType("FileContentRange");
	if (fcr.updateAt==null) fcr.updateAt="";

	
		return fcr;
	}

	

	
}
