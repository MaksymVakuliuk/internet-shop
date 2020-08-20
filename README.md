# Internet shop
   * [Short Description](#description)
   * [Project Structure](#structure)
   * [Setup Guide](#setup)
   * [Authors](#authors)
# <a name="description"></a> Short Description 
   This Web project of simple internet shop.
### Functions available for all users:
   * view the main page
   * view list of products
   * log in
   * sign up
   * log out
#### Functions available for users with "ADMIN" role:
   * delete orders of users
   * view orders list and order information of all users
   * add/delete products to shop
   * view list of all users
   * delete users
### Functions available for users with "USER" role:
   * add/delete products to shopping cart
   * complete orders
   * view own orders
# <a name="description"></a> Project Structure
  * java 11
  * Maven 4.0.0
  * javax.servlet-api 3.1.0
  * jstl 1.2
  * log4j 2.13.3
  * mysql-connector-java 8.0.20
  * maven-checkstyle-plugin
# <a name="description"></a> Setup Guide
##### To run this project you need to have installed:
  * Java 11+
  * Tomcat 
  * MySql
##### Setup project
* Add this project to your IDE as Maven project.
* Configure java sdk in ProjectStructure
* Add new Tomcat Server configuration and select **war-exploded artifact** to deploy.
Set application context parameter to "/".
* Change path to your log file in **src/main/resources/log4j.properties** on line 9.
* Execute queries listed in **src/main/resources/init_db.sql** in MySQL RDBMS 
in order to create the schema and all the tables required.
* Enter your own username and password in
**src/main/java/com/internet/shop/util/ConnectionUtil.java** in line 20,21.
* Run the project via Tomcat configuration.
* Go to **"/insert"** page to add admin user (name = "admin", login = "admin", password = "apass")
and user (name = "user", login = "user", password = "pass") to DB. **(optional)**  
# <a name="authors"></a> Authors
Maksym Vakuliuk