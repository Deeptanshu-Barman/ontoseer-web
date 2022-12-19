package com.ontoseerweb.ontoseer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import kracr.iiitd.ontoseer.Ontoseer;

@Component
public class client {
	public String downloadpath="C:/Users/Deeptanshu Barman/Desktop/ontoseer-web/ontoseer/src/downloads/";
   	public Ontoseer instance;
	public HashMap<String,List<String>> printclassname;
	public HashMap<String,List<String>> printpropname;
	public HashMap<String,HashMap<String,String>> printaxiom;
	public HashMap<String,List<List<String>>> printvocab;
	public List<List<String>> printodp;
	public String printvalid;
	public void clean(String path,String ext){
		File folder = new File(path);
        if (folder.exists()) {
            File[] listAllFiles = folder.listFiles();
            long Deletion = System.currentTimeMillis() -( 5* 60 *1000L);   // clean files later than 5 min in milisec                       
            for (File listFile: listAllFiles) {
                if (listFile.getName().endsWith(ext) &&
                    listFile.lastModified() < Deletion) {
                    if (!listFile.delete()) {
                        System.out.println("Sorry can't delete");
                    }
                }
            }
        }
	}
	public void setpath(String path){
		clean(downloadpath,".pdf");
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
	public String randomfilename() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
    
        return generatedString;
    }
	public String getpdf() throws IOException{
		String dest =downloadpath+randomfilename()+".pdf";       
      	PdfWriter writer = new PdfWriter(dest);
		PdfDocument pdfDoc = new PdfDocument(writer);
		pdfDoc.addNewPage();
		Document document = new Document(pdfDoc);
		String cls="CLASS NAMING RECOMMENDATION";
		String brk="-------Not Generated-------";
		Paragraph paragraph1 = new Paragraph(cls);
		document.add(paragraph1);
		
		if (printclassname!=null){
			float [] pointColumnWidths = {100F, 300F};   
			Table table = new Table(pointColumnWidths);
			table.addCell(new Cell().add("Class Name"));       
			table.addCell(new Cell().add("Recommendation"));
			for(Map.Entry<String, List<String>> mp : this.printclassname.entrySet()) {
				table.addCell(new Cell().add(mp.getKey()));
				table.addCell(new Cell().add(mp.getValue().toString()));
			}
			document.add(table);
		}
		else{
			document.add(new Paragraph(brk));
		}
		document.add(new Paragraph("PROPERTY NAMING RECOMENDATION"));
		if (printpropname!=null){
			float [] pointColumnWidths = {100F, 300F};   
			Table table = new Table(pointColumnWidths);
			table.addCell(new Cell().add("Property Name"));       
			table.addCell(new Cell().add("Recommendation"));
			for(Map.Entry<String, List<String>> mp : this.printpropname.entrySet()) {
				table.addCell(new Cell().add(mp.getKey()));
				table.addCell(new Cell().add(mp.getValue().toString()));
			}
			document.add(table);
		}
		else{
			document.add(new Paragraph(brk));
		}
		document.add(new Paragraph("ODP RECOMENDATION"));
		if (printodp!=null){
			float [] pointColumnWidths = {100F, 200F};   
			Table table = new Table(pointColumnWidths);
			table.addCell(new Cell().add("Class/Property Name"));       
			table.addCell(new Cell().add("IRI"));
			List<String> name=printodp.get(0);
			List<String> iri=printodp.get(1);
			for(int i=0;i<Math.min(name.size(),iri.size());i++){
				table.addCell(new Cell().add(name.get(i)));       
				table.addCell(new Cell().add(iri.get(i)));
			}
			document.add(table);
		}
		else{
			document.add(new Paragraph(brk));
		}
		document.add(new Paragraph("AXIOM RECOMENDATION"));
		if (printaxiom!=null){
			float [] pointColumnWidths = {100F,100F,200F};   
			Table table = new Table(pointColumnWidths);
			table.addCell(new Cell().add("Class/Property Name"));       
			table.addCell(new Cell().add("Recommendation"));
			table.addCell(new Cell().add("IRI"));
			for(Map.Entry<String, HashMap<String,String>>mp : printaxiom.entrySet()) {
				System.out.println(mp.getKey());
				for(Map.Entry<String, String>submp : mp.getValue().entrySet()) {
					System.out.println("\t+"+submp.getKey()+"\t"+submp.getValue());
					table.addCell(new Cell().add(mp.getKey()));       
					table.addCell(new Cell().add(submp.getKey()));
					table.addCell(new Cell().add(submp.getValue()));
				}
			}
			document.add(table);
		}
		else{
			document.add(new Paragraph(brk));
		}
		document.add(new Paragraph("CLASS HEIRARCHY VALIDATION"));
		if (printvalid!=null){
			document.add(new Paragraph(printvalid));
		}
		else{
			document.add(new Paragraph(brk));
		}
		document.add(new Paragraph("VOCABULARY RECOMMENDATION"));
		if(printvocab!=null){
			float [] pointColumnWidths = {100F,100F,200F};   
			Table table = new Table(pointColumnWidths);
			table.addCell(new Cell().add("Class/Property Name"));       
			table.addCell(new Cell().add("Source"));
			table.addCell(new Cell().add("IRI"));
			for(Map.Entry<String, List<List<String>>>mp : printvocab.entrySet()){
				List<String> rec=mp.getValue().get(0);
				List<String> iri=mp.getValue().get(1);
				for(int i=0;i<rec.size();i++){
					table.addCell(new Cell().add(mp.getKey()));       
					table.addCell(new Cell().add(rec.get(i)));
					table.addCell(new Cell().add(iri.get(i)));
				}
			}
			document.add(table);
		}
		else{
			document.add(new Paragraph(brk));
		}
		document.close();
		return dest;             
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
		this.printvalid=b;
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
		this.printvocab=result;
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
		this.printaxiom=result;
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
		this.printpropname=ans;
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
		this.printclassname=ans;
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
		this.printodp=recommendedODP;
		return recommendedODP;
	}
}
