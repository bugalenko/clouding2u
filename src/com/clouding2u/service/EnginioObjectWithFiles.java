package com.clouding2u.service;
import java.io.File;
import java.util.HashMap;

import com.google.gson.JsonObject;


public class EnginioObjectWithFiles extends AbstractEnginioObject {
     
	private HashMap<String,File> files;
	@Override
	public JsonObject toJson() throws EnginioObjectIOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fromJson(JsonObject src) throws EnginioObjectIOException {
		// TODO Auto-generated method stub
		return null;
	}
  
}
