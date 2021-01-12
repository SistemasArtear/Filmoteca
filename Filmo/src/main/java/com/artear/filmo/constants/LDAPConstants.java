package com.artear.filmo.constants;

public abstract class LDAPConstants {

	public static final String VALUE = "value";
	public static final String KEY = "key";
	public static final String CONDICION = "condicion";
	
	public static final String KEY_PATTERN = "%key%";
	public static final String VALUE_PATTERN = "%value%";
	public static final String CONDICION_PATTERN = "%condicion%";
	public static final String QUERY_PATTERN = "("+ KEY + CONDICION + VALUE +")";
	
}
