# Micro-services with Spring Cloud

This application is a Spring Boot application that demonstarate how to develop Micro-services with Spring Cloud Technologies. I have used the following tools in this project:

- Spring Boot - wiring the services
- Eureka - for service discovery
- Hystrix - for Circuit Breaking

The project consists of 4 applications/services, each a Spring Boot application.When the services are running, we will be able to see `Boook`s read by a `Student`, this operation is controlled and made possible by all the four services. Below I have listed the services and their functions.

- ms-discovery-server - Eureka (Netflix Service Discovery Server)
- ms-library - the REST API that is recieving Request from the fron-end application
- ms-book-service - Handles all `Book`s Request
- ms-members-services - Handles all `Member`s Request


# Configurations

### Eureka Server(ms-discovery-server)
To include Eureka, Netflix Service Discovery Server, in the classpath of my Discovery Server, I have included the `spring-cloud-starter-netflix-eureka-server` dependecny in my `pom.xml` file and annotated the main class of this service with __@EnableEurekaServe__. The __@EnableEurekaServe__ instructs the Spring Boot application to act as Eureka Server and sets up the registry for us. For this Server to be located by other services, we have to confirgure `eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/`

### Other Services
The other services will register themselves with the Eureka Server, and for that to happen I included the `spring-cloud-starter-netflix-eureka-client` in the class path. This dependency allows us to pull the __@EnableEurekaClient__ to the main class of each services. This annotation turns the Spring Boot application into a __Eureka Instance__ (this means that it can regsiter itself with Eureka) and __Eureka Cleint__ (this means that it can query the registry for other services). The following properties are important:
````xml
 1. eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
 2. spring.application.name=ms-books-service
````

The first one tell the service where Eureka is located so that it can register itself. The second one bears the name(the service id) of the services. This name is important, it is the one that will be kept in the registry by Eureka and used by other services to locate it. For example to __GET__ a `Book` by it's __ID__ from the `ms-book-service` application, the URL becomes `http://ms-books-service/book-id`. The beauty of service discovery is that we do not need to hard-code the __IP__ and __Port__ of the `ms-books-service`, rahter, wwe use the __service id__, and tha is all!

# The REST API
For the purpose of demonstrating the architecture of micro-services & the Spring Cloud components, this project has `GET` end-points. I will discuss the important ones below.

#### Books Service
##### B.1 Retrieve books read by a particlular Student
````java
@RequestMapping(value = "/readby/{mbId}", method = RequestMethod.GET)
  public List<Book> getBkReadByMb(@PathVariable("mbId") Integer mbId) {
  return  this.bookService.getBksReadByMb(mbId);
}
````
		
This end-point gives a list of all `Book`'s read by a `Student`.

##### B.2 Get a Book's details
````java
@RequestMapping(value = "/{bkId}", method = RequestMethod.GET)
  public Optional<Book> getBk(@PathVariable("bkId") Integer bkId) {
  return this.bookService.getBk(bkId);
}
````

#### Members Service

##### M.1 Get a Member's details
````java
@RequestMapping(value = "/{mbId}", method = RequestMethod.GET)
  public Member getMb(@PathVariable("mbId") Integer mbId) {
  return memberService.getMb(mbId);
}
````

#### Library Service

##### L.1 Retrieve History
````java
@RequestMapping(value = "/mb/history/{mbId}", method = RequestMethod.GET)
Reading getBksReadByMb(@PathVariable("mbId") Integer mbId) {
//1. Get member details
//2. Get Books that the member read
Reading reading=new Reading();

Optional<Member> optionalMember = Optional.ofNullable(this.libraryMemberService.getMb(mbId));
optionalMember.ifPresent((res)->{
    reading.setMember(optionalMember.get());
    reading.setBks(this.libraryBookService.getBksReadByMb(mbId));
});
return reading;
}
````    
    
This end-point is one that bundles all the functions of this project together, hiting it causes multiple process and threads to be initiated. First. it is supposed to fetch `Member`s details from the `ms-members-service` -__end-point M.1__. Upon a successful response, a `Member` has has been returned, the application goes on to fetch the `Book`s that the `Member` read. This requires firing a __GET__ request to the `ms-book-service` - __end-point B.1__. All the results are bundled into a `Reading` object and returned to the caller.

The `ms-members-service` is called by following method using a __WebClient API__.
````java
public Member getMb(Integer mbId) {
  return this.client
		.build()
		.get()
		.uri("http://ms-members-service/lib/v1/mb/" + mbId)
		.retrieve()
		.bodyToMono(Member.class)
		.block();
    }
 ````

The `ms-book-service` is called by following method using a __WebClient API__.
````java
public List<Book> getBksReadByMb(Integer mbId) {
     return this.client
	.build()
	.get()
	.uri("http://ms-books-service/lib/v1/bk/readby/" + mbId)
	.retrieve()
	.bodyToFlux(Book.class).toStream().collect(Collectors.toList());
 }
````
As can be seen, in both cases, we are using _service-ids/application_ names to call the services. Even if we change the IP and Ports of these services, as long as they register themselves with Eureka, we will be able to find them in the registry by calling their __service-ids__.


 ## Load Balacing
 ## Circuit Breaking





