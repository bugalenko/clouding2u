import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


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

      User u2=new User("AlexanderPoet","alex@gmail.com","Sasha","Pushkin","OneginPassword");
      System.out.println();
  String s=    new GsonBuilder().create().toJson(u2);
  User.insert(u2);
  System.out.println(s);
  u2.setUsername("NotYetPushkin");
  u2.setLastName("NotPushkin");
  User.update(u2);
  HashMap<String,User> byId=User.query(UserQueryBy.ById);
  HashMap<String,User> byUsername=User.query(UserQueryBy.ByUsername);
  HashMap<String,User> byFirstname=User.query(UserQueryBy.ByFirstname);
  HashMap<String,User> byLastname=User.query(UserQueryBy.byLastname);
  byId.isEmpty();
 User.delete(u2.getId());
	}

}
