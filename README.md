# ontoseer-web

Steps to run the Server

1)Please make sure that the Ontoseer standalone version has been installed to take care of the dependency. 
The standalone version can be found at https://github.com/kracr/ontoseer-standalone. To install, simply clone the repository and run the command "mvn install".

2)Once the standalone version has been installed, please clone this repository and update the file paths listed in client.java and controller.java to point to a local file path on your system. 
Additionally, you can edit the application properties file to set the server to run through a proxy by specifying the port (the default is 8080).

3)To run the server, please use the command "mvn spring-boot:run"
