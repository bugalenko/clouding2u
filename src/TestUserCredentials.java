import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.google.gson.JsonObject;




public class TestUserCredentials {
public static void main(String[] args) throws Exception {
/*	FileContentRange range=new FileContentRange(new File("cat.JPG"));
	JsonObject obj=range.toJson();
	range.toString();
	EnginioObjectsCollection.insert(range);
JsonObject response=	EnginioObjectsCollection.getAccessPermissions(range);
 User u4=new User("AlexanderPoet4","alex4@gmail.com","Sasha4","Pushkin4","OneginPassword4");
 User u5=new User("AlexanderPoet5","alex5@gmail.com","Sasha4","Pushkin5","OneginPassword5");
 Usergroup group1=new Usergroup("reader1");
 Usergroup group2=new Usergroup("reader2");
 Usergroup.insert(group1);
 Usergroup.insert(group2);
 
// User.insert(u4);
// User.insert(u5);
 EnginioObjectPoliciesFabric rangeFabric=new EnginioObjectPoliciesFabric(range);
 rangeFabric.addUsergroup(AbstractAccessControlPoliciesFabric.AccessPermissionsType.readFromCollection,group1);
 rangeFabric.addUsergroup(AbstractAccessControlPoliciesFabric.AccessPermissionsType.readFromCollection,group2);
 EnginioObjectsCollection.setSingleAccessPermissions(rangeFabric,AbstractAccessControlPoliciesFabric.AccessPermissionsType.readFromCollection);
	
 EnginioObjectsCollection.insert(range);
 range.setChunkCount(128);
 EnginioObjectsCollection.update(range);
 int i=	EnginioObjectsCollection.delete(range);
 FileContentRange newRange=(FileContentRange) EnginioObjectsCollection.get(range);
String str= newRange.toString();*/
	EnginioUsergroup group1=new EnginioUsergroup("tourist");
	EnginioUsergroup.insert(group1);
	EnginioUsergroup group2=new EnginioUsergroup("tourist2");
	EnginioUsergroup.insert(group2);
	
	String password="OneginPasswords";
	 User u2=new User("AlexanderPoet4","alex4@gmail.com","Sashas4","Pushkins4",password);
	 User.insert(u2);
	 User u3=new User("AlexanderPoet5","alex5@gmail.com","Sashas5","Pushkins5",password);
	 User.insert(u3);
	int resp= EnginioUsergroup.insertUser(u2,group1);
	resp= EnginioUsergroup.insertUser(u3,group1);
	
	
	ArrayList<String> users_id=EnginioUsergroup.getUsers(group1);
	
	resp=EnginioUsergroup.insertUser(u2,group2);
	resp=EnginioUsergroup.deleteUser(u2, group1);
	 UserCredentials cred=new UserCredentials(false, u2);
	 cred.getAccessToken();
	 cred.refreshTokens();
	 cred.removeTokens();
	 User.delete(u2.getId());
	 
}
}
