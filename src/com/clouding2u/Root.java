package com.clouding2u;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


public class Root {
	//сервер обработки REST-запросов
	public final static String Service_URI="https://api.engin.io/v1/";
   // индефикатор и ключ доступа к хранилищу
	private String QtBackendid=new String();
	private String AuthToken=new String();
	
	public Root() throws FileNotFoundException,IOException{
		// открытие файла настроек через читалку потоков
		FileReader reader=new FileReader("setup");
		//читалка обьектов джСон
		JsonReader GReader=new JsonReader(reader);
		GReader.setLenient(false);
		//парсер джсон 
		JsonParser parser= new JsonParser();
	//чтение основного джсон-элемента	
	JsonElement el= parser.parse(GReader);
	//Получения в качестве джсон обьекта
	 JsonObject  jObj=el.getAsJsonObject();
	 //парсинг и установка полей данного обьекта
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
