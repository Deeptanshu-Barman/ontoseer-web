package com.ontoseerweb.ontoseer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Controller
public class maincontroller {
    private static String UPLOADED_FOLDER = "C:/Users/Deeptanshu Barman/Desktop/ontoseer-web/ontoseer/src/uploads/";

    @Autowired
    public client cli;

    @GetMapping("/")
	public String start(){
		return "findex";
	}
    @GetMapping("/about")
	public String about(){
		return "aboutus";
	}
    @PostMapping("/uploadtext")
    public @ResponseBody List<String> tupload(String pastebin){
        String fname=randomfilename();
        Path path =Paths.get(UPLOADED_FOLDER+fname);
        byte[] arr = pastebin.getBytes();
        try {
            Files.write(path, arr);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }
        cli.setpath(UPLOADED_FOLDER+fname);
        return Collections.emptyList();
    }
    @PostMapping("/uploadurl")
    public @ResponseBody List<String> urlupload(String URL){
        String fname=randomfilename();
        File myObj = new File(UPLOADED_FOLDER+fname);
        fname=fname+".owl";
        try{
            downloadUsingNIO(URL, UPLOADED_FOLDER+fname);
        }catch(IOException e){
            e.printStackTrace();
        }
        cli.setpath(UPLOADED_FOLDER+fname);
        System.out.println(fname);
        return cli.getclasslist();
    }
	@PostMapping("/upload")
    public @ResponseBody List<String> fileUpload(MultipartFile file) {
        List<String> Class_list=new ArrayList<String>();
        try {
            // create a path from the file name
            Path path = Paths.get(UPLOADED_FOLDER, file.getOriginalFilename());
            Files.write(path, file.getBytes());
            cli.setpath(UPLOADED_FOLDER+file.getOriginalFilename());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Class_list.add("NA");
            return cli.getclasslist();
        }
        return cli.getclasslist();
    }
    @PostMapping("/cr")
    public @ResponseBody HashMap<String,List<String>> getclassname(String ClassList) {
        return cli.resultofclass(ClassList);
    }
    @PostMapping("/pr")
    public @ResponseBody HashMap<String,List<String>> getpropertyname(String reqpropname) {
        return cli.resultofprop(reqpropname);
    }
    @GetMapping("/pr")
    public @ResponseBody List<String> propr(){
        return cli.getpropertylist();
    }
    @GetMapping("/ar")
    public @ResponseBody List<String> axiomr(){
        return cli.getaxiomlist();
    }
    @GetMapping("/vr")
    public @ResponseBody List<String> vocabr(){
        return cli.getaxiomlist();
    }
    @PostMapping("/vr")
    public @ResponseBody HashMap<String,List<List<String>>> getavocab(String reqvocab){
        return cli.resultofvocab(reqvocab);
    }
    @PostMapping("/ar")
    public @ResponseBody HashMap<String,HashMap<String,String>> getaxiom(String reqaxiom){
        return cli.resultofaxiom(reqaxiom);
    }
    @PostMapping("/odp")
    public @ResponseBody List<List<String>> getodp(String ontdesc,String ontdomain,String ontcompetency) {
        return cli.resultofodp(ontdesc,ontdomain,ontcompetency);
    }
    @PostMapping("/hv")
    public @ResponseBody List<String> gethv(String q1,String q2,String q3,String q4) {
        List<String>ans =new ArrayList<String>();
        ans.add(cli.heirarchyValidation(q1,q2,q3,q4));
        return ans;
    }

    @GetMapping("download")
    public ResponseEntity downloadFileFromLocal() {
        String fileName=cli.getpdf();
        Path path = Paths.get(fileName);
        UrlResource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
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
    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
            
}
