# Internet store (example for recruiters) **DEPLOYED**
https://eshop-kosonm.herokuapp.com/
(at first it might take up to ~30s to load because it's 'asleep' if not used)
### Technologies used:
* Spring Boot 2.1.x
* Spring Security 5.1.x
* Hibernate 5.4.x
* Thymeleaf
* MySQL
* IDE: Visual Studio Code
* OS: Ubuntu 18
* deployed on heroku

##### Steps to setup this app are under the screenshots :)

#### This is how the main page looks (pretty much the same instructions) :
![MainPage](/src/main/resources/static/images/main_page.png)

#### As a customer (not logged in user) add products to your shopping cart, then continue your shopping or enter billing information to complete purchase.
![MainPage](/src/main/resources/static/images/cart.png)

#### As an manager see list of orders and create/update/delete products (employee can only see the list). 
![MainPage](/src/main/resources/static/images/create_product.png)
![MainPage](/src/main/resources/static/images/orderlist_1.png)
![MainPage](/src/main/resources/static/images/orderlist_2.png)

### If you want to setup this app:
1. #### Clone this repository
`git clone https://github.com/kosonm/OnlineStore-Deployed-.git` 

2. #### Create MySql database
 `create database eshop`
 
3. #### Change MySQL username and password as per your MySQL installation
    - open `src/main/resources/application.properties` file
    - comment out (add '#' before it) `jdbc.driverClassName=com.mysql.jdbc.Driver` and `server.port=${PORT:5000}`
    - 'un-comment'(delte '#' before it) every value that's commented out
    - change spring.datasource.username and spring.datasource.password properties as per your mysql installation

4. #### Change hibernate configuration. Right now it's set for 'JawsDB' plugin in heroku deployment. 
    - open `src/main/java/com/eshop/kosonm/Configuration/HibernateConfiguration.java`
    - in `public DataSource dataSource()` function 
        - comment out everything between '// 2' comments
        - 'un-comment' the '// 1' part

5. #### Run the App:
    - You can run the spring boot app by typing the following command `mvn spring-boot:run`
     The server will start on port 8080.

    - You can also package the application in the form of a jar file and then run it like so
`mvn package`
`java -jar target/[appname].jar`


##### if you got any problems or questions reach out to me: marcin.koson@hotmail.com
