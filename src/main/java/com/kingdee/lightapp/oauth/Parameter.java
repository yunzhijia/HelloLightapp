 package com.kingdee.lightapp.oauth;
 
 import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import net.sf.json.JSONObject;
 
 public class Parameter
   implements Serializable, Comparable<Parameter>
 {
   String name;
   String value;
   private static final long serialVersionUID = -8708108746980739212L;
 
   public Parameter(String name, String value)
   {
      this.name = name;
      this.value = value;
   }
 
   public Parameter(String name, double value) {
      this.name = name;
      this.value = String.valueOf(value);
   }
 
   public Parameter(String name, int value) {
      this.name = name;
     this.value = String.valueOf(value);
   }
 
   public String getName() {
      return this.name;
   }
 
   public String getValue() {
     return this.value;
   }
 
   public static Parameter[] getParameterArray(String name, String value) {
      return new Parameter[] { new Parameter(name, value) };
   }
 
   public static Parameter[] getParameterArray(String name, int value) {
      return getParameterArray(name, String.valueOf(value));
   }
 
   public static Parameter[] getParameterArray(String name1, String value1, String name2, String value2)
   {
      return new Parameter[] { new Parameter(name1, value1), 
       new Parameter(name2, value2) };
   }
 
   public static Parameter[] getParameterArray(String name1, int value1, String name2, int value2)
   {
     return getParameterArray(name1, String.valueOf(value1), name2, 
        String.valueOf(value2));
   }
 
   public int hashCode()
   {
     int result = this.name.hashCode();
    result = 31 * result + this.value.hashCode();
     return result;
   }
 
   public boolean equals(Object obj)
   {
     if (obj == null) {
        return false;
     }
     if (this == obj) {
        return true;
     }
     if ((obj instanceof Parameter)) {
       Parameter that = (Parameter)obj;
 
        return (this.name.equals(that.name)) && (this.value.equals(that.value));
     }
     return false;
   }
 
   public String toString()
   {
    return "Parameter{name='" + this.name + '\'' + ", value='" + this.value + 
       '}';
   }
 
   public int compareTo(Parameter o)
   {
      Parameter that = o;
     int compared = this.name.compareTo(that.name);
   if (compared == 0) {
        compared = this.value.compareTo(that.value);
     }
    return compared;
   }
 
   public static String encodeParameters(Parameter[] httpParams) {
     if (httpParams == null) {
       return "";
     }
     StringBuffer buf = new StringBuffer();
      for (int j = 0; j < httpParams.length; j++) {
       if (j != 0)
          buf.append("&");
       try
       {
         buf.append(URLEncoder.encode(httpParams[j].name, "UTF-8"))
          .append("=")
            .append(URLEncoder.encode(httpParams[j].value, "UTF-8"));
       } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
       }
     }
     return buf.toString();
   }
   
	public static Parameter[] convertJSON2ParameterArray(JSONObject parameter)
	  {
	    int size = parameter.size();
	    Parameter[] parameters = new Parameter[size];
	    int i = 0;
	    for (Iterator iterator = parameter.keys(); iterator.hasNext(); i++) {
	      String name = (String)iterator.next();
	      String value = parameter.getString(name);
	      parameters[i] = new Parameter(name, value);
	    }
	    return parameters;
	  }
	
	
   
 }

 