package com.clouding2u.service;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class EnginioObjectPoliciesFabric extends AbstractAccessControlPoliciesFabric {
	
	private HashMap<AccessPermissionsType,HashMap<String,EnginioUsergroup>> credentialsForUsergroup=
			new HashMap<AccessPermissionsType,HashMap<String, EnginioUsergroup>>();
	private HashMap<AccessPermissionsType,HashMap<String,EnginioUser>> credentialsForUser=
			new HashMap<AccessPermissionsType,HashMap<String, EnginioUser>>();
AbstractEnginioObject collectionsObject;
public EnginioObjectPoliciesFabric(AbstractEnginioObject collectionObject) {
	// TODO Auto-generated constructor stub
	this.collectionsObject=collectionObject;
}
 public AbstractEnginioObject getEnginioObject(){
	 return collectionsObject;
 }
@Override
public int addUsergroup(AccessPermissionsType accessPermissions, EnginioUsergroup usergroup) {
	// TODO Auto-generated method stub
	HashMap<String,EnginioUsergroup> usergroups=credentialsForUsergroup.get(accessPermissions);
	if(credentialsForUsergroup.containsValue(usergroup)) return -1;
	
	String id=null;
	if(usergroup.getId().isEmpty()) return -1;
	if(usergroups==null){ usergroups=new HashMap<String,EnginioUsergroup>();
	credentialsForUsergroup.put(accessPermissions, usergroups);
	}
	usergroups.put(usergroup.getId(),usergroup);
	
	return 0;
}
@Override
JsonObject makeRules() {
	// TODO Auto-generated method stub
	
	
	return null;
}
@Override
JsonObject makeSinglePermission(AccessPermissionsType permissionsType) {
	JsonObject requestJson=new JsonObject();
	JsonArray arrayJson=new JsonArray();
HashMap<String,EnginioUsergroup> permissionsList=credentialsForUsergroup.get(permissionsType);
// ���������� ����� ������������� � ������� �������
if(permissionsList!=null) {
  Collection<EnginioUsergroup> collection=permissionsList.values();
 Iterator<EnginioUsergroup>it= collection.iterator();
 while(it.hasNext()){
	 JsonObject obj=new JsonObject();
	 EnginioUsergroup usergroup=it.next();
	 obj.addProperty("id",usergroup.getId());
	 obj.addProperty("objectType","usergroups");
	 arrayJson.add(obj);
 }}
 HashMap<String,EnginioUser> permissionsList2=credentialsForUser.get(permissionsType);
 if(permissionsList2!=null) {
 // ����������  ������������� � ������� �������
int size=permissionsList2.size();
 for(int i=0;i<size;i++){
	 JsonObject obj=new JsonObject();
	 obj.addProperty("id",permissionsList.get(i).getId());
	 obj.addProperty("objectType","aclSubject");
	 arrayJson.add(obj);
 } }
 requestJson.add(permissionsType.getAccessCredentialName(),arrayJson);
	return requestJson;
}
@Override
int addUser(AccessPermissionsType accessPermissions, EnginioUser user) {
	// TODO Auto-generated method stub
	HashMap<String,EnginioUser> users=null;
	if(credentialsForUser.containsValue(user)) return -1;
	String id=null;
	if(user.getId().isEmpty()) return -1;
	users=credentialsForUser.get(accessPermissions);
	if(users==null) users=new HashMap<String,EnginioUser>();
	users.put(user.getId(),user);
	return 0;
}

}
