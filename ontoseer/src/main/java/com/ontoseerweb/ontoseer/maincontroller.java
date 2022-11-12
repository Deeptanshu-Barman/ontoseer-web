package com.ontoseerweb.ontoseer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@Controller
public class maincontroller {
    private static String UPLOADED_FOLDER = "C://Users//Deeptanshu Barman//Desktop//ontoseer-web//ontoseer//src//uploads//";

    @Autowired
    public client cli;

    @GetMapping("/")
	public String start(){
		return "findex";
	}
    @GetMapping("/start")
    public String start1(){
		return "index";
	}
    @PostMapping("/uploadtext")
    public String tupload(@RequestParam("pastebin") String text, RedirectAttributes redirectAttributes){
        if (text.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please paste Something");
            return "redirect:uploadStatus";
            }
        String fname=randomfilename();
        Path path =Paths.get(UPLOADED_FOLDER+fname);
        byte[] arr = text.getBytes();
        try {
            Files.write(path, arr);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }
        cli.setpath(UPLOADED_FOLDER+fname);
        return "redirect:/upload";
    }
	@PostMapping("/upload") 
    public String singleFileUpload(@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
        redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
        return "redirect:uploadStatus";
        }
        try {
        // Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        Files.write(path, bytes);
        cli.setpath(UPLOADED_FOLDER + file.getOriginalFilename());
        redirectAttributes.addFlashAttribute("message",
        "You successfully uploaded '" + cli.getpath()+ "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/upload";
    }
    
    @GetMapping("/upload")
    public String uploadStatus(Model model) {
        return "upload";
    }

    @GetMapping("/cr")
    public String classr(Model m){
        try{
            // cli.setpath(UPLOADED_FOLDER+"wine.rdf");
            System.out.println(cli.getpath());
            HashMap<String,String> ans=new HashMap<>();
            ans=cli.vocab();
            // if(ans.isEmpty()){
            //     ans.put("test","test");
            // }
            // System.out.println(ans.get("test"));
            m.addAttribute("map", ans);
            m.addAttribute("message", "CLASS RECOMMENDATION");
            m.addAttribute("message1", "CLASS");
            m.addAttribute("message2", "Recommendation");
        }catch(RuntimeException e){
            e.printStackTrace();
        }
        return "result";
    }
    @GetMapping("/pr")
    public String propr(Model m){
        try{
            // cli.setpath(UPLOADED_FOLDER+"wine.rdf");
            System.out.println(cli.getpath());
            HashMap<String,String> ans=new HashMap<>();
            ans=cli.vocab1();
            // if(ans.isEmpty()){
            //     ans.put("test","test");
            // }
            // System.out.println(ans.get("test"));
            m.addAttribute("map", ans);
            m.addAttribute("message", "Property RECOMMENDATION");
            m.addAttribute("message1", "property");
            m.addAttribute("message2", "Recommendation");
        }catch(RuntimeException e){
            e.printStackTrace();
        }
        return "result";
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
            
}
