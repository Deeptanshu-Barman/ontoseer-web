package com.ontoseerweb.ontoseer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


public class FirstOwlAPI {
	public static List<String> classlist;
	public static List<String> objectPropertyList;
	public static List<String> dataPropertyList;
	public  String path;
	
	public void commands() {
		System.out.println("-h  -- Help");
		 System.out.println("-p <Ontology file path> -cr|-pr|-or|-ar|-chr|-vr <arguments for respective recommendation>");
		 System.out.println("\t-cr --ClassRecommendation <class1 class2 class3 .... >");
		 System.out.println("\t-pr --ProperRecommendation <Property1 Property2 ......>");
		 System.out.println("\t-or --ODP Recommendation <arg1 arg2 arg3>");
		 System.out.println("\t\targ1 -- Description of the ontology");
		 System.out.println("\t\targ2 -- Domain of ontology");
		 System.out.println("\t\targ3 -- Competency questions");
		 System.out.println("\t-ar --Axiom Recommendation <class|property>");
		 System.out.println("\t\tClass or Property for which you want the axiom recommendation");
		 System.out.println("\t-chr --Class Hierarchy Validation <arg1 arg2 arg3 arg4>(all arguments are mandatory)");
		 System.out.println("\t\targ1 -- Do the properties of superclass cease to exit in the future(temporal dependency)? (Y/N)");
		 System.out.println("\t\targ2 -- Do the properties of the sub class cease to exist in the future(temporal dependency)? (Y/N)");
		 System.out.println("\t\targ3 -- Are the properties of super-class and sub-class identical? (Y/N)");
		 System.out.println("\t\targ4 -- Are the properties of sub-class part of the properties whole class? (Y/N)");
		 System.out.println("\t-vr --Vocabulary Recommendation <arg1>");
		 System.out.println("\t\targ1 -- Class or Property");
	}
	
	public static void main(String[] args) {
		 OWLOntologyManager manager=OWLManager.createOWLOntologyManager();
		 FirstOwlAPI firstOwlAPI = new FirstOwlAPI();
		 
		 if(args[0].equalsIgnoreCase("-p") && args.length>=2) {
			 firstOwlAPI.path = args[1];
		 }
		 
		 else if(args[0].equalsIgnoreCase("-h") && args.length==1){
			 firstOwlAPI.commands();
			 System.exit(0);
		 }
		 else if(args.length==2) {
			 firstOwlAPI.commands();
			 return;
		 }
		 else {
			 System.out.println("Not a valid command....");
			 System.exit(0);
		 }
		 try {
		    // loading the axioms
			final OWLOntology owl=manager.loadOntologyFromOntologyDocument(new File(firstOwlAPI.path));
			 //System.out.println(owl.getAxiomCount());
			 Set<OWLEntity> ont = owl.getSignature();
			 Set<OWLClass>classes = owl.getClassesInSignature();
			 Set<OWLObjectProperty> prop;
	         Set<OWLDataProperty> dataProp;
	         Set<OWLNamedIndividual> individuals;
			 
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
				 //System.out.println("Class "+cls);
				 
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
			 	
			 // Class Name Recommendations
			 HashMap<String, String> reClass = new HashMap<>();
			 HashMap<String, String> reProperty = new HashMap<>();
			 if(args.length>2) {
			 switch(args[2]){
			 case "-cr":
				 	System.out.println("inside Class Recomendation");
 					NameConventionPanel namingPanel = new NameConventionPanel();
	 				reClass = namingPanel.vocab(classlist);
	 				for(int i=3;i<args.length;i++) {
	 					if(reClass.containsKey(args[i])) {
	 						System.out.println("Recommended class for "+args[i]+" is "+reClass.get(args[i]));
	 					}
	 					else {
	 						System.out.println(args[i]+ " class is not present in ontology");
	 					}
	 				}
	 				System.exit(0);
				 				
	 		 // Property Name Recommendation
			 case "-pr": 
	 				NameConventionPanel proPanel = new NameConventionPanel();
	 				reProperty = proPanel.vocab1(objectPropertyList);
	 				for(int i=3;i<args.length;i++) {
	 					if(reProperty.containsKey(args[i])) {
	 						System.out.println("Recommended property for "+args[i]+" is "+reProperty.get(args[i]));
	 					}
	 					else {
	 						System.out.println(args[i]+ " property is not present in ontology");
	 					}
	 				}
	 				System.exit(0);
				 				
			 // ODP Recommendation
			 case "-or":
				 
				 // List to contains all the entities 
				 List<String>temp = new ArrayList<String>();
				 for(Map.Entry<String, String> entry : reClass.entrySet()) {
					 temp.add(entry.getValue());
				 }
				 for(Map.Entry<String, String> entry : reProperty.entrySet()) {
					 temp.add(entry.getValue());
				 }
				 temp.addAll(dataPropertyList);
//			 System.out.println("\n============ ODP Recommendation ===============");
				 String[] str = temp.toArray(new String[0]);
				 OdpRecommendation odprcmd = new OdpRecommendation(str);
				 odprcmd.ODP(str, args[3], args[4], args[5]);
				 System.exit(0);
			 
			 
			 //parsing the axioms === Don't delete it
//			 System.out.println("\n=========================== Axioms =====================\n");
//			 owl.getClassesInSignature().forEach(i->{
//			        final PrintParse p=new PrintParse(i);
//			        owl.getReferencingAxioms(i).forEach(axiom->axiom.accept(p));
//			    });
//			 for (OWLEntity entity : ont) {
//			    System.out.println(entity);//this print only the entities
//			}
				 
				 
				 
		// Axiom Recommendation
			 case "-ar":
				 AxiomRecommendation axioms = new AxiomRecommendation();
				 axioms.recommendAxioms(args[3]);
				 System.exit(0);
		
		// Vocabulary Recommendation
			 case "-vr":
				 VocabRecommendation vocabRecommendation = new VocabRecommendation();
				 vocabRecommendation.findsimilarity(args[3]);
					//d.description(st);
				 vocabRecommendation.URI(args[3]);
				 System.exit(0);
			
		// Class Hierarchy validation
			 case "-chr":
				 ClassHierarchyValidation classHierarchyValidation = new ClassHierarchyValidation();
				 classHierarchyValidation.classHierarchy(args[3], args[4], args[5], args[6]);
				 System.exit(0);
				 
			default:
				 System.out.println("Invalid command....");
				 firstOwlAPI.commands();
				 System.exit(0);
		}
	}else {
		firstOwlAPI.commands();
		System.exit(0);
	}	 
	}catch (OWLOntologyCreationException e) {
		e.printStackTrace();
	}

}
}
