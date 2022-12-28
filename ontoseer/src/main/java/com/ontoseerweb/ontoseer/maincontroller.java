package com.ontoseerweb.ontoseer;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    private WebApplicationContext context;

    public client getcli(){
        return (client)context.getBean("client");
    }

    @GetMapping("/")
	public String start(){
        System.out.println(getcli().hashCode());
		return "findex";
	}
    @GetMapping("/about")
	public String about(){
		return "aboutus";
	}
    @PostMapping("/uploadtext")
    public @ResponseBody List<String> tupload(String pastebin){
        getcli().clean(UPLOADED_FOLDER,".owl");
        String fname=randomfilename()+".owl";
        Path path =Paths.get(UPLOADED_FOLDER+fname);
        byte[] arr = pastebin.getBytes();
        try {
            Files.write(path, arr);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }
        getcli().setpath(UPLOADED_FOLDER+fname);
        return getcli().getclasslist();
    }
    @PostMapping("/uploadurl")
    public @ResponseBody List<String> urlupload(String URL){
        String fname=randomfilename();
        getcli().clean(UPLOADED_FOLDER,".owl");
        fname=fname+".owl";
        new File(UPLOADED_FOLDER+fname);
        try{
            downloadUsingNIO(URL, UPLOADED_FOLDER+fname);
            getcli().setpath(UPLOADED_FOLDER+fname);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return Collections.emptyList();
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
        System.out.println(fname);
        return getcli().getclasslist();
    }
	@PostMapping("/upload")
    public @ResponseBody List<String> fileUpload(MultipartFile file) {
        getcli().clean(UPLOADED_FOLDER,".owl");
        getcli().clean(UPLOADED_FOLDER,".rdf");
        List<String> Class_list=new ArrayList<String>();
        try {
            // create a path from the file name
            Path path = Paths.get(UPLOADED_FOLDER, file.getOriginalFilename());
            Files.write(path, file.getBytes());
            getcli().setpath(UPLOADED_FOLDER+file.getOriginalFilename());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Class_list.add("NA");
            return getcli().getclasslist();
        }
        return getcli().getclasslist();
    }
    @GetMapping("/cr")
    public @ResponseBody List<String> getclass(){
        return getcli().getclasslist();
    }

    @PostMapping("/cr")
    public @ResponseBody HashMap<String,List<String>> getclassname(String ClassList) {
        return getcli().resultofclass(ClassList);
    }
    @PostMapping("/pr")
    public @ResponseBody HashMap<String,List<String>> getpropertyname(String reqpropname) {
        return getcli().resultofprop(reqpropname);
    }
    @GetMapping("/pr")
    public @ResponseBody List<String> propr(){
        return getcli().getpropertylist();
    }
    @GetMapping("/ar")
    public @ResponseBody List<String> axiomr(){
        return getcli().getaxiomlist();
    }
    @GetMapping("/vr")
    public @ResponseBody List<String> vocabr(){
        return getcli().getaxiomlist();
    }
    @PostMapping("/vr")
    public @ResponseBody HashMap<String,List<List<String>>> getavocab(String reqvocab){
        return getcli().resultofvocab(reqvocab);
    }
    @PostMapping("/ar")
    public @ResponseBody HashMap<String,HashMap<String,String>> getaxiom(String reqaxiom){
        return getcli().resultofaxiom(reqaxiom);
    }
    @PostMapping("/odp")
    public @ResponseBody List<List<String>> getodp(String ontdesc,String ontdomain,String ontcompetency) {
        return getcli().resultofodp(ontdesc,ontdomain,ontcompetency);
    }
    @PostMapping("/hv")
    public @ResponseBody List<String> gethv(String q1,String q2,String q3,String q4) {
        List<String>ans =new ArrayList<String>();
        ans.add(getcli().heirarchyValidation(q1,q2,q3,q4));
        return ans;
    }

    @GetMapping("/download")
    public ResponseEntity downloadFileFromLocal() throws IOException {
        String fileName=getcli().getpdf();
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
