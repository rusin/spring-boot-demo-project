# Spring Boot demo project

# Prerequisites
The following programs have to be installed on local machine before and run this this project:

* Git in version 2.19 or higher. Make sure that git command in available, run git --version to verify it. 
* Java JRE in version 8 or higher. Make sure that Java is installed and availble to build the appliocation. Use java -version to check wheather Java is available. Make sure that JAVA_HOME variable is set as an environment variable.
# How th run and test the demo application
### Clone spring-demo application from git repository
> git clone https://github.com/rusin/spring-boot-demo-project.git

### Go to the project root directory
Open command line and move to project root directory.

### Build the project with Maven Wrapper
> mvnw.cmd clean package (on Windows, use ./mvnw clean package on Unix)

### Run the application locally with Java command
> java -jar -Dspring.profiles.active=dev target/demo-project-1.0.0.jar

Note, skip profile to run the app in the production mode, db file is stored in subfolder data.
### API documentation
The project API docuemntation is available through Swagger endoint. Please note that Swagger UI hasn't been enabled. 
> localhost:8080/v2/api-docs

### Use Postman collection to verify exposed REST api
Open *'api-sample-requests/Demo Project.postman_collection.json'* in Postman and run sample reuqest against the exposed resources. 

# Additional resources
### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
