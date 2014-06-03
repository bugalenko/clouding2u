package com.clouding2u.service;


public class UploadData {
 private String header=new String();
 private byte[] data;
 public UploadData(String header,byte[] data) {
	// TODO Auto-generated constructor stub
	 this.header=header;
	 this.data=data;
}
 public void setHeader(String header){
	 this.header=header;
 }
public  void setData(byte[] uploadData){
	 data=uploadData;
 }
 public String getHeader(){
	return header;
	 
 }
 byte[] getData(){
	return data;
	 
 }
 
}
