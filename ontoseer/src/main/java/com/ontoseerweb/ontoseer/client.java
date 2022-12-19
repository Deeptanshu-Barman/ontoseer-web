package com.ontoseerweb.ontoseer;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import kracr.iiitd.ontoseer.Ontoseer;

@Component
public class client {
   	public Ontoseer instance;
	public void setpath(String path){
		Ontoseer ins=new Ontoseer();
		ins.path=path;
		ins.parseOntology();
		ins.reClass = new HashMap<>();
		ins.reProperty = new HashMap<>();
		instance=ins;
		System.out.println("OK");
	}

	public List<String> getclasslist(){
		return Ontoseer.classlist;
	}
	public String getpdf(){
		
	}

	public String heirarchyValidation(String q1,String q2,String q3,String q4){
		instance.argIndx=3;
		String a=" -chr ";
		a ="-p"+" "+instance.path.substring(34)+a;
		if(q1.equals("1")){a=a+"Y ";}
		else{a=a+"N ";}
		if(q2.equals("1")){a=a+"Y ";}
		else{a=a+"N ";}
		if(q3.equals("1")){a=a+"Y ";}
		else{a=a+"N ";}
		if(q4.equals("1")){a=a+"Y ";}
		else{a=a+"N ";}
		System.out.println(a);
		String[] words = a.split(" ");
		instance.arguments=words;
		instance.argLength=words.length;
		String b=instance.classHierarchyValidation();
		System.out.println(b);
		return b;
	}

	public HashMap<String,List<List<String>>> resultofvocab(String vocab){
		instance.argIndx=3;
		String a=" -vr ";
		a="-p"+" "+instance.path.substring(34)+a;
		String b=vocab.replace(',',' ');
		String q=a+b;
		System.out.println(q);
		String[] words = q.split(" ");
		instance.arguments=words;
		instance.argLength=words.length;
		HashMap<String,List<List<String>>>result=new HashMap<>();
		for(int i=3;i<instance.argLength;i++){
			instance.argIndx=i;
			List<List<String>> res=instance.vocabularyRecommendation();
			result.put(words[i],res);
		}
		System.out.println(result.size());
		return result;
	}
	
	public List<String> getaxiomlist(){
		List<String> result=new ArrayList<>();
		result.addAll(Ontoseer.classlist);
		result.addAll(Ontoseer.objectPropertyList);
		result.addAll(Ontoseer.dataPropertyList);
		return result;
	}
	public HashMap<String,HashMap<String,String>> resultofaxiom(String axioms){
		instance.argIndx=3;
		String a=" -ar ";
		a="-p"+" "+instance.path.substring(34)+a;
		String b=axioms.replace(',',' ');
		String q=a+b;
		System.out.println(q);
		String[] words = q.split(" ");
		instance.arguments=words;
		instance.argLength=words.length;
		HashMap<String,HashMap<String,String>>result=new HashMap<>();
		for(int i=3;i<instance.argLength;i++){
			instance.argIndx=i;
			HashMap<String,HashMap<String,String>>result1 = instance.axiomRecommendation();
			result.putAll(result1);
		}
		System.out.println(result.size());
		for(Map.Entry<String, HashMap<String,String>>mp : result.entrySet()) {
			System.out.println(mp.getKey());
			for(Map.Entry<String, String>submp : mp.getValue().entrySet()) {
				System.out.println("\t+"+submp.getKey()+"\t"+submp.getValue());
			}
		}
		return result;
	}

	public List<String> getpropertylist(){
		List<String> result=new ArrayList<>();
		result.addAll(Ontoseer.objectPropertyList);
		result.addAll(Ontoseer.dataPropertyList);
		return result;
	}

	public HashMap <String,List<String>> resultofprop(String property){
		instance.argIndx=3;
		String a=" -pr ";
		a="-p"+" "+instance.path.substring(34)+a;
		String b=property.replace(',',' ');
		String q=a+b;
		System.out.println(q);
		String[] words = q.split(" ");
		instance.arguments=words;
		instance.argLength=words.length;
		HashMap<String,List<String>> ans=instance.classNameRecommendation();
		List<String> ans1=new ArrayList<>();
		for(Map.Entry<String, List<String>> mp : ans.entrySet()) {
			ans1.add(mp.getKey()+":"+mp.getValue());
			System.out.println(mp.getKey());
			System.out.println("\t+"+mp.getValue());
		}
		return ans;
	}

	public HashMap<String,List<String>> resultofclass(String Class){
		instance.argIndx=3;
		String a=" -cr ";
		a="-p"+" "+instance.path.substring(34)+a;
		String b=Class.replace(',',' ');
		String q=a+b;
		System.out.println(q);
		String[] words = q.split(" ");
		instance.arguments=words;
		instance.argLength=words.length;
		HashMap<String,List<String>> ans=instance.propertyNameRecommendation();
		List<String> ans1=new ArrayList<>();
		for(Map.Entry<String, List<String>> mp : ans.entrySet()) {
			ans1.add(mp.getKey()+":"+mp.getValue());
			System.out.println(mp.getKey());
			System.out.println("\t+"+mp.getValue());
		}
		return ans;
	}

	public List<List<String>> resultofodp(String ontdesc,String ontdomain,String ontcompetency){
		instance.argIndx=3;
		String a=" -or ";
		a="-p"+" "+instance.path.substring(34)+a;
		if(ontdesc!=""){
			a=a+ontdesc+" ";
		}
		if(ontdomain!=""){
			a=a+ontdomain+" ";
		}
		if(ontcompetency!=""){
			a=a+ontcompetency;
		}
		String q=a;
		System.out.println(q);
		String[] words = q.split(" ");
		instance.arguments=words;
		instance.argLength=words.length;
		List<List<String>> recommendedODP=new ArrayList<>();
		if((instance.argIndx+2)<instance.argLength && !instance.isPresent(words[instance.argIndx+2])) {
			recommendedODP = instance.odpRecommendation();
			instance.argIndx = instance.argIndx+3;
	   }
		else {
			recommendedODP = instance.odpRecommendation();
			instance.argIndx +=2;
		}
		// List<String>ls1 = recommendedODP.get(0);
		// List<String>ls2 = recommendedODP.get(1);
		// System.out.println("\t1. "+ls1.get(0).toString()+"\n"+"\tIRI: "+ls2.get(0)+"\n"+"\n"+"\t2. "+ls1.get(1).toString()+"\n"+"\tIRI: "+
		// ls2.get(1)+"\n"+"\n"+"\t3. "+ls1.get(2).toString()+"\n"+"\tIRI: "+ls2.get(2)+"\n"+"\n"+"\t4. "+ls1.get(3).toString()+"\n"+"\tIRI: "+
		// ls2.get(3));
		return recommendedODP;
	}
}
