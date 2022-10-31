package com.ontoseerweb.ontoseer;


/*code for class hierarchy validation*/
public class ClassHierarchyValidation{
     
    public ClassHierarchyValidation() {
//        System.out.println("Class Hierarchy Validation");
    }
     
    public void classHierarchy(String supClassRigidity, String subClassRigidity, String identity, String unity) {
    	if(supClassRigidity.equalsIgnoreCase("")||subClassRigidity.equalsIgnoreCase("")||identity.equalsIgnoreCase("")||unity.equalsIgnoreCase("")) {
    		System.out.println("All mandatory fields are not filled");
    	}
    	else {
    	//The cases for Y and N are ignored. Y is yes and N is No
    	/*Rigidity if superclass is Y then sub class can be Y||N. If superclass is N then subclass has to be N*/
    	if(supClassRigidity.equalsIgnoreCase("y")) {
    		if(subClassRigidity.equalsIgnoreCase("y")||subClassRigidity.equalsIgnoreCase("N")) {
    			System.out.println("Rigidity is correctly maintained.");
    		}
    		
    	}
    	else if(supClassRigidity.equalsIgnoreCase("N")) {
    		if(subClassRigidity.equalsIgnoreCase("N")) {
    			System.out.println("Rigidity is correctly maintained.");
    		}
    		else if(subClassRigidity.equalsIgnoreCase("Y")) {
    			System.out.println("Rigidity is not correctly maintained. Subclass hierarchy is not correct.");
    		}
    	}
    	
    	/*If identity is Y then they are identical else not*/
    	if(identity.equalsIgnoreCase("y")) {
    		
    			System.out.println("Identity is correctly maintained.");
    		
    		
    	}
    	else if(identity.equalsIgnoreCase("N")) {
    		    System.out.println("Identity is not correctly maintained. Subclass hierarchy is not correct.");
    	
    	}
    	
    	if(unity.equalsIgnoreCase("y")) {
    		
    			System.out.println("Unity is correctly maintained.\n");
    		
    		
    	}
    	
    	/*If Unity is Y then they are identical else not*/
    	else if(unity.equalsIgnoreCase("N")) {
    		
    		
    			System.out.println("Unity is not correctly maintained. Subclass hierarchy is not correct.\n");
    		
    	}
    
    	}	
    }
    
    public static void main(String[] args) {
     
//    		ClassHierarchyValidation j=new ClassHierarchyValidation();
//    		j.classHierarchy("y", "y", "n", "n");
//    		j.classHierarchy("Y", "y", "N", "N");
    }
}

