package com.clouding2u.service;


public abstract class AbstractEnginioObject implements IEnginioObject {
 String id=new String();
 String type=new String();
 String getId() {
	return id;
}
 void setId(String id) {
	 this.id=id;
};
 String getType() {
	return type;
}
 void setType(String type) {
	this.type=type;
}
 // поля  устанавливаеміе сервером
String  createdAt=new String();
String  updateAt=new String();
String objectType=new String();
void  setCreatedAt(String createdAt){
	  this.createdAt=createdAt;
  }
void  setUpdateAt(String updateAt){
	  this.updateAt=updateAt;
}
}
