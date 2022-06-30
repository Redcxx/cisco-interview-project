------------------------------------------------------------------
Build and run the application
------------------------------------------------------------------
(1) Requirement
- Java 1.8
- Maven 3.2+

(2) Build and Run
Option 1: Unzip and open folder in Intellij and run it.
Option 2: Run `mvnw spring-boot:run` from the commandline in the project folder. Use `./mvnw spring-boot:run` if you are on linux.

(3) Querying
Option 1:
    Open `localhost:8080` in a browser.
    Enter url 1 and 2 accordingly and hit compute button.
Option 2:
    Query REST API at `localhost:8080/api`.
    Sample query: `curl -d 'url1=https://cisco.com&url2=https://weilueluo.com' -X POST localhost:8080/api`

------------------------------------------------------------------
Choose 3 websites and compute similarity
------------------------------------------------------------------
(1) https://cisco.com
(2) https://luoweilue.com
(3) https://example.com

(1) and (2) = 0.03180212014134275
(1) and (3) = 0.021739130434782608
(2) and (3) = 0.04
