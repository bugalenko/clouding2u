package com.clouding2u;
import java.util.HashMap;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;


public class TestUsers {
	
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		HashMap<String,String> list=new HashMap<String,String>();
		list.put("a", "asder");
		list.put("b", "bwerty");
   Class cl= list.getClass();
   Class[] classes=cl.getClasses();
   int l= classes.length;
 String nam=  classes[0].getCanonicalName();
	JsonElement obj=new GsonBuilder().create().toJsonTree(list, HashMap.class);	

      EnginioUser u2=new EnginioUser("AlexanderPoet","alex@gmail.com","Sasha","Pushkin","OneginPassword");
      System.out.println();
  String s=    new GsonBuilder().create().toJson(u2);
  EnginioUser.insert(u2);
  System.out.println(s);
  u2.setUsername("NotYetPushkin");
  u2.setLastName("NotPushkin");
  EnginioUser.update(u2);
  HashMap<String,EnginioUser> byId=EnginioUser.query(UserQueryBy.ById);
  HashMap<String,EnginioUser> byUsername=EnginioUser.query(UserQueryBy.ByUsername);
  HashMap<String,EnginioUser> byFirstname=EnginioUser.query(UserQueryBy.ByFirstname);
  HashMap<String,EnginioUser> byLastname=EnginioUser.query(UserQueryBy.byLastname);
  byId.isEmpty();
 EnginioUser.delete(u2.getId());
	}

}
