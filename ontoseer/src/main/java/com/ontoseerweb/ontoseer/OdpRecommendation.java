package com.ontoseerweb.ontoseer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class OdpRecommendation {
	//JFileChooser fileChooser = new JFileChooser();
    
    public List<String>ct1;
    public String ct2[]=new String[50000];
    
    
    public OdpRecommendation(String ct[]) {
        //super("ODP Recommendation");
        System.out.println("ODP Recommendation");
        ct1=new ArrayList<>();
    	ct2=ct;
    }
     
    public List<String> ODP(String ct2[], String S1, String S2, String S4) {
    	List<String>ls1=new ArrayList<String>();
    	List<String>ls2=new ArrayList<String>();
    	
    	OdpDescription o=new OdpDescription();
    	
    	String s;
    	s = S1+" "+S2+" "+" "+S4;

    	ls1.addAll(o.findsimilarity(s, ct2));
    	ls2.addAll(o.findsimilarity1(s, ct2));
    	
    	System.out.println("1. "+ls1.get(0).toString()+"\n"+"IRI: "+ls2.get(0)+"\n"+"\n"+"2. "+ls1.get(1).toString()+"\n"+"IRI: "+
    	ls2.get(1)+"\n"+"\n"+"3. "+ls1.get(2).toString()+"\n"+"IRI: "+ls2.get(2)+"\n"+"\n"+"4. "+ls1.get(3).toString()+"\n"+"IRI: "+
    	ls2.get(3));
    	return ls1;	
    }
    
    public static void main(String[] args) {
    	System.out.println("ODP Recommendation");
    }
}


