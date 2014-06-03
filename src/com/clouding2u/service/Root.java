package com.clouding2u.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


public class Root {
	//������ ��������� REST-��������
	public final static String Service_URI="https://api.engin.io/v1/";
   // ����������� � ���� ������� � ���������
	private String QtBackendid=new String();
	private String AuthToken=new String();
	
	public Root() throws FileNotFoundException,IOException{
		// �������� ����� �������� ����� ������� �������
		FileReader reader=new FileReader("setup");
		//������� �������� �����
		JsonReader GReader=new JsonReader(reader);
		GReader.setLenient(false);
		//������ ����� 
		JsonParser parser= new JsonParser();
	//������ ��������� �����-��������	
	JsonElement el= parser.parse(GReader);
	//��������� � �������� ����� �������
	 JsonObject  jObj=el.getAsJsonObject();
	 //������� � ��������� ����� ������� �������
	 JsonElement el1=   jObj.get("Id");
	 QtBackendid=el1.getAsString();
	 JsonElement el2=jObj.get("Token");
	 AuthToken=el2.getAsString();
	}

 public String	getBackendId(){
	return QtBackendid;
	}
public String getAuthToken(){
	return AuthToken;
	
}
}
