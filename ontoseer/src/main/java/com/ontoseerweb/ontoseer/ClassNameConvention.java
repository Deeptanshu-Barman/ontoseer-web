package com.ontoseerweb.ontoseer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClassNameConvention {

	/*converting to camel case*/
	public  String convertToCamelCase(String sentence) {
		String sentence1=sentence; 
		// Extract all words
		String words[] = sentence.split("[  _]+");
		
		// Creating an empty string of type StringBuilder so that modification of string is possible.
		StringBuilder sb = new StringBuilder();
		
		// Iterating through words
		for(String word : words){
			//Extracting first char
			char firstChar = word.charAt(0);
			// Checking if firstchar is not in upper case already
			if (!Character.isUpperCase(firstChar)) {
				// Convert first char into upper case and then append remaining characters of words. 
				sb.append(Character.toUpperCase(firstChar)).append(word.substring(1));
			} else
				sb.append(word.substring(0));
			
		}
		// Iterating through words
		// Converting StringBuilder to String. trim() is needed to trim last space appended. 
		String result = sb.toString().trim();
		return result;
	}

	/*convert to inverse camel case*/
	public  String convertToReverseCamelCase(String sentence) {
		String sentence1=sentence; 
		// Extract all words
		String words[] = sentence.split("[  _]+");
		
		// Creating an empty string of type StringBuilder so that modification of string is possible.
		StringBuilder sb = new StringBuilder();
		
		// Iterating through words
		boolean flag = true;
		String splChrs = "-/@#$%^&_+=()";
		for (int i=0;i<words.length;i++) {
			//Extracting first char
			String word=words[i];
			char firstChar = word.charAt(0);
			String first = ""+firstChar;

			if(flag && (first.matches("[" + splChrs + "]+") || first.matches("[0-9]+")))
				continue;
			if(flag) {
				if (Character.isUpperCase(firstChar)) {
					// Convert first char into upper case and then append remaining characters of words. 
					sb.append(Character.toLowerCase(firstChar)).append(word.substring(1));
				} else
					sb.append(word.substring(0));
				flag = false;
			}
			else{
				// Checking if firstchar is not in upper case already
				if (!Character.isUpperCase(firstChar)) {
					// Convert first char into upper case and then append remaining characters of words. 
					sb.append(Character.toUpperCase(firstChar)).append(word.substring(1));
				} 
				else
					sb.append(word.substring(0));
			}
		}
		// Iterating through words
	
		// Converting StringBuilder to String. trim() is needed to trim last space appended. 
		String result = sb.toString().trim();
		return result;
	}

	/*checking whether string is alphabet*/
	public  String isStringOnlyAlphabet(String str) 
	{ 
		String d=str;
	    if(!str.equals("")&& (str != null)&& (str.matches(".*\\d.*"))){
	    	 d=str.replaceAll("\\d","");
	    }
	    //System.out.println(d);
	    return d;        
	}

	public boolean t(String s) {
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(s);
		boolean found = matcher.find();
		return found;
	}
	
	public static void main(String args[]) {
		ClassNameConvention s=new ClassNameConvention();
		List<String>ls=new ArrayList<String>();
		List<String>ls1=new ArrayList<String>();
	
//		ls.addAll(s.propertiesRecommendation("has_under"));				
//		ls.addAll(s.propertiesRecommendation("Person news"));
//		ls.addAll(s.propertiesRecommendation("@#%"));
//		ls.addAll(s.propertiesRecommendation("1234"));
//		ls.addAll(s.propertiesRecommendation("1234_"));
//		ls.addAll(s.propertiesRecommendation("_1234"));
//		ls.addAll(s.propertiesRecommendation("12_34"));
//		ls.addAll(s.propertiesRecommendation("1234_Person news"));
//		ls.addAll(s.propertiesRecommendation("1234 Person"));
//		ls.addAll(s.propertiesRecommendation("Person_news world\n"));

//		ls1.addAll(s.classRecommendation("Person#!%$&_under"));
//		ls1.addAll(s.classRecommendation("Person news_world"));
//		ls1.addAll(s.classRecommendation("@#%"));
//		ls1.addAll(s.classRecommendation("1234"));
//		ls1.addAll(s.classRecommendation("1234_"));
//		ls1.addAll(s.classRecommendation("_1234"));
//		ls1.addAll(s.classRecommendation("12_34"));
//		ls1.addAll(s.classRecommendation("1234_Person news_world 123"));
//		ls1.addAll(s.classRecommendation("1234 person"));
//		ls1.addAll(s.classRecommendation("Person_news world"));

		System.out.println(ls.size());
		System.out.println(ls1.size());

		
	for(int i=0;i<ls.size();i++) {
			System.out.println(ls.get(i));
			}

	for(int i=0;i<ls1.size();i++) {
		System.out.println(ls1.get(i));
	}
	}

	// Recommand a class name in CamelCase
	public HashMap<String, String> classRecommendation(List<String> classnames) {
		List<String>ls=new ArrayList<String>();
		HashMap<String, String>reClass = new HashMap<>();
		 String EMPTY = "";
		String splChrs = "-/@#$%^&_+=()";
		String chrs = "-/@#$%^&_+=()0123456789" ;
		
		for(int i=0;i<classnames.size(); i++) {
			boolean found = classnames.get(i).matches("[" + splChrs + "]+");
	
			if(found) {
				ls.add("Classname only contains Special characters Please change.");
				reClass.put(classnames.get(i), "Classname only contains Special characters Please change.");
				//return ls;
			}
			else if(classnames.get(i).matches("[0-9]+") && classnames.get(i).length() > 2) {
		          System.out.println("");
		          ls.add("Classname only contains digits Please change.");
		          reClass.put(classnames.get(i), "Classname only contains digits Please change.");
		          //return ls;
		         // classname="";
		      }
		    else if(classnames.get(i).matches("[" + chrs + "]+"))
	    	{
	    		System.out.println("");
		        ls.add("Classname only contains digits and special characters Please change.");
		        reClass.put(classnames.get(i), "Classname only contains digits and special characters Please change.");
		        //return ls;
	    	}
			else{ 
			ClassNameConvention s=new ClassNameConvention();
			String x=s.convertToCamelCase(classnames.get(i));
			String x1=s.remove_spaceproperties(x);
			String y=s.isStringOnlyAlphabet(x1);
			ls.add(y);
			reClass.put(classnames.get(i), y);
			}
		//return ls;
		}
		return reClass;
	}
	
	// Recommand the function name in reverse camelCase
	public HashMap<String, String> propertiesRecommendation(List<String> propertylist) {
		List<String>ls=new ArrayList<String>();
		HashMap<String, String>reProperty = new HashMap<>();
		 String EMPTY = "";
		String splChrs = "-/@#$%^&_+=()";
		String chrs = "-/@#$%^&_+=()0123456789";
		for(int i=0;i<propertylist.size();i++) {
			boolean found = propertylist.get(i).matches("[" + splChrs + "]+");
			if(found) {
				ls.add("Property name only contains Special characters Please change.");
				reProperty.put(propertylist.get(i), "Property name only contains Special characters Please change.");
				//return ls;
			}
			else if(propertylist.get(i).matches("[0-9]+") && propertylist.get(i).length() > 2) {
		          System.out.println("");
		          ls.add("Property name only contains digits Please change.");
		          reProperty.put(propertylist.get(i), "Property name only contains digits Please change.");
		          //return ls;
		         // classname="";
		      }
		    else if(propertylist.get(i).matches("[" + chrs + "]+"))
	    	{
	    		System.out.println("");
		        ls.add("Property name only contains digits and special characters Please change.");
		        reProperty.put(propertylist.get(i), "Property name only contains digits and special characters Please change.");
		        //return ls;
	    	}
			else { 
			ClassNameConvention s=new ClassNameConvention();
			String x=s.convertToReverseCamelCase(propertylist.get(i));
			String x1=s.remove_spaceproperties(x);
			String y=s.isStringOnlyAlphabet(x1);
			ls.add(y);
			reProperty.put(propertylist.get(i), y);
			}
		}
		return reProperty;
	}

	public String remove_spaceproperties(String classname) {

		classname=classname.replaceAll("\\p{Punct}","");
	       char[] charArray = classname.toCharArray();
	      
	       String stringWithoutSpaces = "";
	       for (int i = 0; i < charArray.length; i++) 
	       {
	           if ( (charArray[i] != ' ') && (charArray[i] != '\t') )
	               stringWithoutSpaces = stringWithoutSpaces + charArray[i];
	       }
	       return stringWithoutSpaces;
		}
}
