package com.ontoseerweb.ontoseer;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
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
    public static List<String> classlist;
	public static List<String> objectPropertyList;
	public static List<String> dataPropertyList;
	HashMap<String, String> reClass;
	HashMap<String, String> reProperty;
	private String path;
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
		NameConventionPanel namingPanel = new NameConventionPanel();
	 	reClass = namingPanel.vocab(classlist);
		return reClass;
	}
	public HashMap<String,String> getnr(){
		reProperty=new HashMap<>();
		NameConventionPanel proPanel = new NameConventionPanel();
	 	reProperty = proPanel.vocab1(objectPropertyList);
		return reProperty;
	}


    
}
