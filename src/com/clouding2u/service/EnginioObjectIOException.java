package com.clouding2u.service;

public class EnginioObjectIOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 EnginioObjectIOException(String EnginioObjectType,String javaVariableName){
		String m1="Error in Enginio Object that type is "+EnginioObjectType+"/n";
		String m2="Presents by java variable"+javaVariableName+"/n";
		System.out.print(m1+m2);
		
		 
	 }

}
