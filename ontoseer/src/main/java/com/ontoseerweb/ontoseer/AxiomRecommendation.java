package com.ontoseerweb.ontoseer;

/*file for axiom recommendation*/

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/*running over all the indexed tuples and matching each of the three term and storing most relevant terms in hash-set*/
public class AxiomRecommendation {

	public OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

	public List<String> retrieveFilePaths() {
		File filePath = new File("C:/Users/ankit/Downloads");
		File filesList[] = filePath.listFiles();
		// System.out.println(filesList);
		List<String> paths = new ArrayList<String>();
		for (File file : filesList) {
			if (file.isFile() && file.getName().endsWith(".owl")) {
				paths.add(file.getAbsolutePath().replace("\\", "/"));
			}
		}
//		for (String s : paths) {
//			System.out.println(s);
//		}
		return paths;
	}

	// function to add value in a set
	public void addToSet(HashMap<String, Set<String>> map, String setName, String value) {
		if (!map.containsKey(setName)) {
			map.put(setName, new HashSet<String>());
		}
		Set<String> values = map.get(setName);
		values.add(value);
	}

	public void recommendAxioms(String className) {
		System.out.println("\nAxioms Recommendation");
		List<String> paths = retrieveFilePaths();
		List<String> axioms = new ArrayList<>();
		// List<String[]>subClassOf = new ArrayList<>();
		HashMap<String, Set<String>> subClassOf = new HashMap<String, Set<String>>();
		HashMap<String, Set<String>> disjointClassOf = new HashMap<String, Set<String>>();
		HashMap<String, Set<String>> equivalentClassOf = new HashMap<String, Set<String>>();
		List<String> objectPropertyList = new ArrayList<String>();
		for (String path : paths) {
			try {
				OWLOntology owl = manager.loadOntologyFromOntologyDocument(new File(path));
				String iri = owl.getOntologyID().getOntologyIRI().toString();
				iri = iri.substring(9, iri.length() - 1);
				
				// Object property
				for (OWLObjectPropertyDomainAxiom op : owl.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {                        
                    //if (op.getDomain().equals(cls)) {   
                        for(OWLObjectProperty oop : op.getObjectPropertiesInSignature()){
                             //System.out.println("\t\t +: " + oop.getIRI().getShortForm());
                             objectPropertyList.add(oop.getIRI().getShortForm());
                        }
//                        System.out.println("\t\t +: " + op.getProperty().getNamedProperty().getIRI().getShortForm());
                    //}
                }
				
				for (OWLAxiom ax : owl.getLogicalAxioms()) {
					String temp = "";
					temp += ax;
					//System.out.println(ax);
					if (temp.contains("SubClassOf") || temp.contains("DisjointClasses")
							|| temp.contains("EquivalentClasses")) {
						temp = temp.replace("<", "");
						temp = temp.replace(">", "");
						temp = temp.replace("(", " ");
						temp = temp.replace(")", " ");
						temp = temp.replace(iri, "");
						temp = temp.replace("#", "");

						String temp1[] = temp.split(" ");
//						System.out.println(temp1);
						if (temp.contains("SubClassOf")) {
							if (temp1.length == 3) {
								addToSet(subClassOf, temp1[1], temp1[2]);
								axioms.add(temp);
							}
						} else if (temp.contains("DisjointClasses")) {
							axioms.add(temp);
							String disjointcls[] = temp.split(" ");
							for (int i = 1; i < disjointcls.length; i++) {
								String key = disjointcls[i];
								for (int j = 1; j < disjointcls.length; j++) {
									if (i != j) {
										addToSet(disjointClassOf, key, disjointcls[j]);
									}
								}
							}
						}
						else if(temp.contains("EquivalentClasses")) {
							axioms.add(temp);
							String eqlcls[] = temp.split(" ");
//							for(String cls:eqlcls)
//								System.out.println(cls);
//							for (int i = 1; i < eqlcls.length; i++) {
//								String key = eqlcls[i];
//								for (int j = 1; j < eqlcls.length; j++) {
//									if (i != j) {
//										addToSet(disjointClassOf, key, eqlcls[j]);
//									}
//								}
//							}
						}
//						for(int i=0;i<temp1.length;i++) {
//							System.out.println(temp1[i]);
//						}
						
					}
				}
			} catch (OWLOntologyCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		// Print the axioms
//		for (Map.Entry<String, Set<String>> entry : subClassOf.entrySet()) {
//			System.out.println(entry.getKey() + " subclass Of " + entry.getValue());
//		}
//
//		for (Map.Entry<String, Set<String>> entry : disjointClassOf.entrySet()) {
//			System.out.println(entry.getKey() + " disjoint with " + entry.getValue());
//		}
		
		// Recommended sub class of given class
		if(subClassOf.get(className) != null)
		{
			for(String str : subClassOf.get(className))
				System.out.println(className +" sub class of "+ str);
			System.out.println("");
		}
		
		// Recommended disjoint classes for given class
		if(disjointClassOf.get(className) != null) {
			for(String str : disjointClassOf.get(className))
				System.out.println(className +" disjoint class of "+ str);
			System.out.println("");
		}
	}

	/*
	 * public void re(String a,String folder="C:\Users\ankit\Downloads\Ontology") {
	 * String s; Set<String> hash_Set = new HashSet<String>(); Set<String> al = new
	 * HashSet<String>();
	 * 
	 * try{ File file = new File(folder); File [] files = file.listFiles(); running
	 * over entire indexed tuples for (int i = 0; i < files.length; i++){
	 * 
	 * File fstream = new File(files[i].toString()); Scanner in = null; in = new
	 * Scanner(fstream); int linecount=0;
	 * 
	 * while(in.hasNext()) { String line=in.nextLine(); if(line.contains(a)){
	 * al.add(files[i].toString()); hash_Set.add(line); } }in.close(); }
	 * Iterator<String> i1 = al.iterator(); while (i1.hasNext())
	 * System.out.println(i1.next());
	 * 
	 * 
	 * Iterator<String> i = hash_Set.iterator(); int count=0; while (i.hasNext()) {
	 * count++; if(count==1) { System.out.println(i.next()); } } }catch (Exception
	 * e){ e.printStackTrace(); } }
	 */

	/*
	 * similarity finding of terms with any of the three indexed terms of each
	 * axioms
	 */
	/*
	 * public List<String> match(String name) {
	 * 
	 * AxiomTriples a = new AxiomTriples(); List<String>axioms = new
	 * ArrayList<String>(); axioms.addAll(a.axioms()); List<String> hash_Set = new
	 * ArrayList<String>(); List<Double>dd = new ArrayList<>(); int count=0;
	 * 
	 * //JaroWinkler similarity approach has been used
	 * StringEqualityPercentCheckUsingJaroWinklerDistance c = new
	 * StringEqualityPercentCheckUsingJaroWinklerDistance(); try { for(int
	 * i=0;i<axioms.size();i++) {
	 * 
	 * String s[]=axioms.get(i).split(" ");
	 * 
	 * 
	 * for(int j=0;j<3;j++) {
	 * 
	 * double d = c.similarity(s[j], name);
	 * 
	 * //Threshold is kept at 0.85 after multiple iterations if(d>=0.95&&count<8) {
	 * 
	 * hash_Set.add(axioms.get(i)); count++; } } }
	 * 
	 * return hash_Set;
	 * 
	 * }catch(Exception e) { return null; } }
	 * 
	 * public List<String>filtering(String s1){ List<String>s=new ArrayList<>();
	 * List<String>s11=new ArrayList<>(); List<String>s12=new ArrayList<>();
	 * s.addAll(match(s1)); for(int i=0;i<s.size();i++) { String
	 * s2[]=s.get(i).split(" ");
	 * 
	 * if(s2[0].equalsIgnoreCase(s2[4])) { s11.add(s.get(i));
	 * 
	 * } else { s12.add(s.get(i)); } } return s12;
	 * 
	 * }
	 */

//	public static void main(String args[]) {
//		AxiomRecommendation obj = new AxiomRecommendation();
//		obj.recommendAxioms("HalfMove");
//	}
}
