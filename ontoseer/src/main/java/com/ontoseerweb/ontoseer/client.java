package com.ontoseerweb.ontoseer;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

import owlapi.tutorial.msc.ClassNameConvention;
import owlapi.tutorial.msc.OdpRecommendation;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
@Component
public class client {
    public List<String> classlist;
	public List<String> objectPropertyList;
	public List<String> dataPropertyList;
	private String path;
	public String ontology_description;
	public String ontology_domain;
	public String ontology_question;
	OWLOntologyManager manager=OWLManager.createOWLOntologyManager();
	OWLOntology owl;
	Set<OWLEntity> ont;
	Set<OWLClass> classes;
	Set<OWLObjectProperty> prop;
	Set<OWLDataProperty> dataProp;
	Set<OWLNamedIndividual> individuals;
	
	public void setpath(String path){
		this.path=path;
		// loading the axioms
		try{
			owl=manager.loadOntologyFromOntologyDocument(new File(this.path));
		}
		catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		//System.out.println(owl.getAxiomCount());
		ont = owl.getSignature();
		classes = owl.getClassesInSignature();
		prop = owl.getObjectPropertiesInSignature();
		dataProp = owl.getDataPropertiesInSignature();
	    individuals = owl.getIndividualsInSignature();
	    classlist = new ArrayList<String>();
	    objectPropertyList = new ArrayList<String>();
	    dataPropertyList = new ArrayList<String>();
	         
	    //System.out.println("\n**********#### Classes ######*************\n");
		for(OWLClass cls : classes) {
		//System.out.println("+: " + cls.getIRI().getShortForm());
		classlist.add(cls.getIRI().getShortForm());
		// System.out.println("Class "+cls);
		
		//System.out.println(" \tObject Property Domain");
		for (OWLObjectPropertyDomainAxiom op : owl.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {                        
				if (op.getDomain().equals(cls)) {   
					for(OWLObjectProperty oop : op.getObjectPropertiesInSignature()){
							//System.out.println("\t\t +: " + oop.getIRI().getShortForm());
							objectPropertyList.add(oop.getIRI().getShortForm());
					}
					//System.out.println("\t\t +: " + op.getProperty().getNamedProperty().getIRI().getShortForm());
				}
			}

			//System.out.println(" \tData Property Domain");
			for (OWLDataPropertyDomainAxiom dp : owl.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {
				if (dp.getDomain().equals(cls)) {   
					for(OWLDataProperty odp : dp.getDataPropertiesInSignature()){
							//System.out.println("\t\t +: " + odp.getIRI().getShortForm());
							dataPropertyList.add(odp.getIRI().getShortForm());
					}
					//System.out.println("\t\t +:" + dp.getProperty());
				}
			}
		}
	}
		
	public String getpath(){
		System.out.println(this.path);
		return this.path;
	}

	public HashMap<String,String> getcr(){
		HashMap<String,String> reClass=new HashMap<>();
		// NameConventionPanel namingPanel = new NameConventionPanel();
	 	// reClass=namingPanel.vocab(classlist);
		return reClass;
	}
	public List<String> vocab(String classname) {
		List<String> ans=new ArrayList<>();
    	HashMap<String, String>reClass = new HashMap<>();
	    ClassNameConvention v = new ClassNameConvention();
	    reClass = v.classRecommendation(classlist);
		if (reClass.containsKey(classname)){
			ans.add(reClass.get(classname));
		}
		if (ans.isEmpty()){
			ans.add("NOTFOUND");
		}
	    return ans;
    }
	public List<String> vocab1(String propname) {
    	List<String>ls=new ArrayList<String>();
    	HashMap<String, String>reProperty = new HashMap<>();
    	ClassNameConvention v = new ClassNameConvention();    	
    	reProperty = v.propertiesRecommendation(objectPropertyList);
		if(reProperty.containsKey(propname)){
			ls.add(reProperty.get(propname));
		}
		if(ls.isEmpty()){
			ls.add("NOT FOUND");
		}
    	return ls;
	}

	public List<String> getodp(String desc,String domain,String Competency){
		List<String>temp = new ArrayList<String>();
		HashMap<String, String>reProperty = new HashMap<>();
    	ClassNameConvention v = new ClassNameConvention();
		HashMap<String, String>reClass = new HashMap<>();    	
    	reProperty = v.propertiesRecommendation(objectPropertyList);
	    reClass = v.classRecommendation(classlist);
		for(Map.Entry<String, String> entry : reClass.entrySet()) {
			temp.add(entry.getValue());
		}
		for(Map.Entry<String, String> entry : reProperty.entrySet()) {
			temp.add(entry.getValue());
		}
		temp.addAll(dataPropertyList);
		String[] str = temp.toArray(new String[0]);
		OdpRecommendation odprcmd = new OdpRecommendation(str);
		return odprcmd.ODP(str, desc,domain,Competency);
	}

	


}
