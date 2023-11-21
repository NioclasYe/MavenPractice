# 1. 根據坐標 創建Maven工程

## 1.1 Maven核心概念 - 坐標

### 1.1.1 向量說明

* **向量說明**：使用三個『**向量**』在『**Maven的倉庫**』中**唯一**的`定位到一個『jar』包`。
    * **groupId**：公司或組織的 id
    * **artifactId**：一個**專案**或**專案中的一個模組**的 id(`比較偏向專案中的一個模組或一個沒有進行分割的專案`)
    * **version**：版本號

### 1.1.2 三個向量的取值方式

* 三個向量的**取值方式**：
    * **groupId**：公司或組織網域的倒序，通常也會加上專案名稱(為了區別專案這一層)
        * **例如**：**com.atguigu.maven**
    * **artifactId**：**模組的名稱**，將來作為 Maven 工程的工程名
    * **version**：**模組的版本號**，依照自己的需求設定
        * 例如：**SNAPSHOT** 表示**快照**版本，正在迭代過程中，不穩定的版本
        * 例如：**RELEASE** 表示**正式**版本
* **舉例**：
    * **groupId**：com.atguigu.maven
    * **artifactId**：pro01-atguigu-maven
    * **version**：1.0-SNAPSHOT

### 1.1.3  座標和倉庫中 jar 套件的儲存路徑之間的對應關係

* 坐標：
    * **groupId**：javax.servlet
    * **artifactId**：servlet-api
    * **version**：2.5
* **上面座標對應的 jar 套件在 Maven 本地倉庫中的位置**：
    * **Maven本地倉庫的根目錄**/**javax**/**servlet**/**servlet-api**/**2.5**/**servlet-api-2.5.jar**

## 1.2 實現操作-步驟

### 1.2.1 建立目錄作為後面操作的工作空間

* 一個普通目錄（`本地工作空間`）：/Users/nicolas/MavenPractice
* **目前已經有了三個目錄**，分別是：
    * Maven 核心程序：中軍大帳
    * Maven 本地倉庫：兵營
    * 本地工作空間：戰場

### 1.2.2 在工作空間目錄下開啟命令列窗口

### 1.2.3 使用命令生成Maven工程

* **指令**： **mvn** `archetype:generate`
    * **mvn**：主命令
    * **archetype:generate**：子命令
        * **archetype**： 插件
        * **generate**：目標
* **指令運行**
    1. **開始下載需要的插件**、jar包、pom文件
        * Downloading：下載中
        * Downloaded：下載OK
    2. **模式選擇**：
        * 7：quickstart快速開始 (`默認選項`)
    3. **根據下列提示操作**
        ```text
        Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): 7:【Enter,使用默认值】
  
        Define value for property 'groupId': 設定groupId => com.nicolas.maven
  
        Define value for property 'artifactId': 設定artifactId => pro01-maven-java
  
        Define value for property 'version' 1.0-SNAPSHOT: :【Enter，使用默认值】
  
        Define value for property 'package' com.nicolas.maven: :【Enter，使用默认值】
  
        Confirm properties configuration: 
            groupId: com.nicolas.maven 
            artifactId: pro01-maven-java version: 1.0-SNAPSHOT 
            package: com.nicolas.maven 
            Y: :【Enter，表示確認。 如果前面有輸入錯誤，想要重新輸入，則輸入 N 再回車。 】
        ```

### 1.2.4 調整

* Maven 預設產生的工程，對 **junit** 依賴的是`較低的 3.8.1 版本`，我們可以改成`較適合的 4.12 版本`。
    * **原本依賴**：
        ```xml
        <dependencies>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>3.8.1</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
        ```
    * **調整後**：
      ```xml
      <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
      </dependencies>
      ```
* **刪除** 自動產生的 `App.java` 和 `AppTest.java`。

### 1.2.5 pom.xml 文件解讀

* 解讀 maven自動產生的 pom.xml

```xml
<!--project標籤：根標籤、project(工程)，表示對當前工程進行配置、管理 -->
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
            http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <!-- modelVersion標籤：從Maven 2後，都固定是4.0.0。 -->
    <!-- 代表當前pom.xml所採用的標籤結構    -->
    <modelVersion>4.0.0</modelVersion>

    <!-- 坐標信息(GAV)    -->
    <!-- groupId 標籤：代表公司或組織 開發的某一個項目    -->
    <groupId>com.nicolas.maven</groupId>
    <!--  artifactId 標籤：代表項目下的某一個模塊 -->
    <artifactId>pro01-maven-java</artifactId>
    <!--  version 標籤：代表當前模塊的版本  -->
    <version>1.0-SNAPSHOT</version>
    <!-- packaging標籤：打包方式    -->
    <!-- 取值 jar：生成jar包，一個 java工程-->
    <!-- 取值 war：生成war包，一個 web工程-->
    <!-- 取值 pom：說明這個工程是用來管理其他的工程的工程-->
    <packaging>jar</packaging>

    <!--  name標籤：  -->
    <name>pro01-maven-java</name>
    <url>http://maven.apache.org</url>

    <!-- 在Maven中定義屬性值  -->
    <properties>
        <!--  project.build.sourceEncoding：maven屬性，用於讀取源碼的字符集      -->
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <!-- dependencies標籤：配置具體依賴信息，可以包含多個dependency標籤  -->
    <dependencies>
        <!-- dependency標籤：配置一個具體的依賴信息-->
        <dependency>
            <!-- 坐標信息：導入哪個jar包，就配置哪一個依賴信息-->
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>

            <!-- scope 標籤：配置當前依賴的範圍 -->
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

## 1.3 Maven核心概念-POM

* **意義**：**POM**：Project Object Model `專案物件模型`。
    * 和 POM 類似的是：DOM（Document Object Model），**文件物件模型**。 它們都是`模型化思想的具體展現`。
* **模型化思想**：**POM** 表示**將工程抽象化為一個模型**，再用程式中的物件來描述這個模型。
    * 這樣我們就可以用程式來管理專案了。
    * 我們在開發過程中，最基本的做法就是將現實生活中的事物抽象化為模型，然後封裝模型相關的資料作為一個對象，這樣就可以在程式中計算與現實事物相關的資料。
* **對應的設定檔**： POM 理念**集中體現在 Maven 工程根目錄下 pom.xml 這個設定檔**。
    * 所以這個 **pom.xml 設定檔**就是 **Maven 工程的核心設定檔**。
      其實`學習 Maven 就是學這個檔案怎麼配置，各個配置有什麼用`。

## 1.4 Maven核心概念-約定的目錄結構

* 目前開發領域的**技術發展趨勢**為：**約定大於配置**，**配置大於編碼**。

### 1.4.1 各個目錄的作用

* **各個目錄的作用**
    ```text
    └── pro01-maven-java
    ├── pom.xml
    └── src (源碼目錄)
        ├── main (主体程序目錄目錄)
        │   ├── java (Java源代碼)
        │   │   └── com (package目錄)
        │   └── resources (配置文件)
        └── test (測試程序目錄目錄)
            └── java (Java源代碼)
                └── com (package目錄)
    ```
* 另外還有一個 **target 目錄**：專門`存放建置操作輸出的結果`

### 1.4.2 約定目錄結構的意義

* Maven 為了讓**建置過程**盡可能『**自動化完成**』(`提高自動化程度`、`配置實現`)，所以`必須約定目錄結構的功能`。
    * 例如：Maven 執行編譯操作，必須先去 Java 原始程式目錄讀取 Java 原始碼，然後執行編譯，最後把編譯結果存放在 target 目錄。

### 1.4.3 約定大於配置

* **Maven** 對於**目錄結構這個問題**，沒有採用配置的方式，而是**基於約定**。
    * 這樣會讓我們在開發過程中非常方便。
    * `如果每次建立Maven工程後，還需要針對各個目錄的位置進行詳細的配置，那肯定非常麻煩`。

# 2. 在Maven工程中 編寫代碼

* **主体程序**：項目中真正要使用的程序
    * **放置的位置**：/Users/nicolas/MavenPractice/pro01-maven-java/src/**main**/java/com/nicolas/maven
        ```java
        package com.atguigu.maven;

        public class Calculator {
            public int sum(int i, int j){
                return i + j;
            }
        }
        ```
* **測試程序**：`測試`項目中真正要使用的程序
    * **放置的位置**：/Users/nicolas/MavenPractice/pro01-maven-java/src/**test**/java/com/nicolas/maven
        ```java
        package com.atguigu.maven;

        import org.junit.Test;
        import com.atguigu.maven.Calculator;

        // 靜態導入的效果是將Assert類別中的靜態資源導入目前類別
        // 這樣一來，在目前類別中就可以直接使用Assert類別中的靜態資源，不需要寫類別名
        import static org.junit.Assert.*;
        public class CalculatorTest{

            @Test
            public void testSum(){

                // 1. 建立Calculator對象
                Calculator calculator = new Calculator();
    
                // 2. 調用Calculator物件的方法，取得到程式運行實際的結果
                int actualResult = calculator.sum(5, 3);
    
                // 3. 聲明一個變量，表示程式運行期待的結果
                int expectedResult = 8;
    
                // 4. 使用『斷言』來判斷實際結果和期待結果是否一致
                // 如果一致：測試通過，不會拋出例外
                // 如果一致：測試通過，不會拋出例外
                assertEquals(expectedResult, actualResult);
            }
        }
        ```

# 3. 執行Maven 的構建命令

## 3.1 要求

* 當**執行 Maven 建置操作相關的指令**時，**必須進入到 pom.xml 所在的目錄**。
* **如果沒有**在 pom.xml 所在的目錄執行 Maven 的建置命令，那麼就會`看到下面的錯誤訊息`
  ```text
  The goal you specified requires a project to execute but there is no POM in this directory
  ```

## 3.2 清除 clean

* **指令**：**mvn clean**
* **效果**：刪除target目錄（`編譯後產生target目錄`）

## 3.3 編譯 compile

* **主程序編譯**：**mvn compile**
    * 編譯結果存放的目錄：target/`classes`
* **測試程序編譯**：**mvn test-compile**
    * 編譯結果存放的目錄：target/`test-classes`

## 3.4 測試 test

* **指令**：**mvn test**
    * 進行**測試操作**，Maven會**自動執行主程式和測試程式**的**編譯操作**
        * resources:3.3.1:resources (default-resources)：**複製**主程序的resources，到target
        * compiler:3.11.0:compile (default-compile)：**編譯**主程序
        * resources:3.3.1:testResources (default-testResources)：**複製**測試的resources，到target
        * compiler:3.11.0:testCompile (default-testCompile)：**編譯**測試
        * surefire:3.1.2:**test** (default-test)：**執行測試**
* **測試報告存放的目錄**：target/`surefire-reports`

## 3.5 打包 package

* **指令**：**mvn package**
    * **打包**：Maven會**自動執行** 主程式和測試程式的`編譯操作`、`測試程式`
* jar包\war包 **存放的目錄**：**target**

## 3.6 安裝 install

* **指令**：**mvn install**
    * **安裝**：Maven會**自動執行** 主程式和測試程式的`編譯操作`、`測試程式`、`打包`
    * **輸出**：將pom.xml、pro01-maven-java-1.0-SNAPSHOT.jar **複製一份至** Maven 儲存package的目錄
        ```text
        [INFO] Installing /Users/nicolas/MavenPractice/pro01-maven-java/pom.xml to /Users/nicolas/.m2/repository/com/nicolas/maven/pro01-maven-java/1.0-SNAPSHOT/pro01-maven-java-1.0-SNAPSHOT.pom
        [INFO] Installing /Users/nicolas/MavenPractice/pro01-maven-java/target/pro01-maven-java-1.0-SNAPSHOT.jar to /Users/nicolas/.m2/repository/com/nicolas/maven/pro01-maven-java/1.0-SNAPSHOT/pro01-maven-java-1.0-SNAPSHOT.jar
        ```
* **效果**：**是將本地建置過程中產生的jar包，存入 Maven 本地倉庫**。
    * 這個jar包在Maven倉庫中的路徑是**根據它的『座標』產生的**
    * **目前坐標信息**：
        ```text
         <groupId>com.nicolas.maven</groupId>
         <artifactId>pro01-maven-java</artifactId>
         <version>1.0-SNAPSHOT</version>
        ```
    * **存入的目錄**：/Users/nicolas/.m2/repository/
      **com/nicolas/maven/pro01-maven-java/1.0-SNAPSHOT/pro01-maven-java-1.0-SNAPSHOT.jar**
        * Maven 儲存package的路徑：`/Users/nicolas/.m2/repository`

# 4. 創建Maven版的Web工程

## 4.1 說明

* 需要**使用一個特定的archetype，來生成Web工程**
* **作法**：參數 `archetypeGroupId`、`archetypeArtifactId`、`archetypeVersion` 用來 **指定現在使用的 maven-archetype-webapp
  的座標**

## 4.2 操作

* **指令**：mvn archetype:
  generate `-DarchetypeGroupId=org.apache.maven.archetypes` `-DarchetypeArtifactId=maven-archetype-webapp` `-DarchetypeVersion=1.4`
* **設定坐標**：
    * **groupId**：com.nicolas.maven
    * **artifactId**：pro02-maven-web
    * **version**：Enter，使用預設

## 4.3 pom.xml

* 打包方式為**war包**：`<packaging>war</packaging>`

## 4.4 生成的Web工程的目錄結構

```text
pro02-maven-web
    ├── pom.xml
    └── src
        └── main
            └── webapp
                ├── WEB-INF
                │   └── web.xml
                └── index.jsp
```

* webapp 目錄下有 index.jsp
* WEB-INF 目錄下有 web.xml

## 4.5 創建 Servlet

1. 在 **main 目錄**下建立 java 目錄
2. 在 **java 目錄**下建立 Servlet 類別所在的套件的目錄(`/com/nicolas/maven`)
3. 在**包**下建立 Servlet 類
    ```java
    package com.nicolas.maven;

    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.ServletException;
    import java.io.IOException;

    public class HelloServlet extends HttpServlet {

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.getWriter().write("hello maven web");
        }
    }
    ```

## 4.6  在 web.xml 中註冊 Servlet

* **註冊HelloServlet**

```xml

<web-app>
    <servlet>
        <servlet-name>helloServlet</servlet-name>
        <servlet-class>com.nicolas.maven.HelloServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>helloServlet</servlet-name>
        <url-pattern>/helloServlet</url-pattern>
    </servlet-mapping>
</web-app>
```

## 4.7 在 index.jsp 頁面上編寫超鏈接

```html
<!DOCTYPE html>
<html lang="en">
<body>
<h2>Hello World</h2>
<a href="helloServlet">Access Servlet</a>
</body>
</html>
```

## 4.8 編譯 Compile

* 目前有使用到 javax.servlet，POM需要添加依賴
    * 沒有添加依賴，mvn compile => 報ERROR 缺少依賴
* **添加依賴**
    ```xml
    <!-- 為了正常使用 HttpServlet，導入servlet-api的依賴-->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>
    ```
    * **版本選擇**：`使用最新版`或`較多人使用`

## 4.9 Web工程 打包 Package

* **指令**：**mvn package**

## 4.10 將 war 套件部署到 Tomcat 上運行

* 將 **pro02-maven-web.war** 複製到 Tomcat/**webapps**目錄下
    * **訪問路徑**：`http://localhost:8080/pro02-maven-web/`

# 5. 讓Web工程 依賴 Java工程

## 5.1 觀念

* **明確一個意識**：從來**只有 Web 工程依賴 Java 工程**，沒有反過來 Java 工程依賴 Web 工程。
    * 本質上來說，Web 工程所依賴的 Java 工程**其實就是 Web 工程裡導入的 jar 套件**。 最後 Java 工程會變成 jar 包，**放在
      Web 工程的 WEB-INF/lib 目錄下**。

## 5.2 操作

* 依賴pro01-maven-java
    ```xml
    <dependency>
        <groupId>com.nicolas.maven</groupId>
        <artifactId>pro01-maven-java</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    ```

## 5.3 在 Web 工程中，編寫測試代碼

* **目標**：`證明在Web工程中可以使用Java工程中建立的那個類別 => Calculator`
* **步驟**：
    1. 建立測試的目錄
    2. 把 Java 工程的 CalculatorTest.java 類別複製到 pro02-maven-wb

## 5.4 執行Maven命令

1. **測試**：**mvn test**
2. **打包**：**mvn package** OR **mvn clean package**(`清除＋打包`)
    * 引入的Java工程，在`打包後會顯示在這個目錄下`：/pro02-maven-web/**target**/pro02-maven-web/**WEB-INF/lib**
3. **查看目前 Web 工程所依賴的 jar 包的列表**：**mvn dependency:list**
    ```text
    [INFO] The following files have been resolved:
    [INFO]    junit:junit:jar:4.11:test -- module junit (auto)
    [INFO]    org.hamcrest:hamcrest-core:jar:1.3:test -- module hamcrest.core (auto)
    [INFO]    javax.servlet:javax.servlet-api:jar:3.1.0:provided -- module javax.servlet.api (auto)
    [INFO]    com.nicolas.maven:pro01-maven-java:jar:1.0-SNAPSHOT:compile -- module pro01.maven.java (auto)
    ```
    * **樹形結構**：**mvn dependency:tree**
         ```text
         [INFO] com.nicolas.maven:pro02-maven-web:war:1.0-SNAPSHOT
         [INFO] +- junit:junit:jar:4.11:test
         [INFO] |  \- org.hamcrest:hamcrest-core:jar:1.3:test
         [INFO] +- javax.servlet:javax.servlet-api:jar:3.1.0:provided
         [INFO] \- com.nicolas.maven:pro01-maven-java:jar:1.0-SNAPSHOT:compile
         ```

# 6. 測試依賴範圍 Scope標籤

* **通過import 語句將要測試的類引入當前類**，引入後
    * 編譯**成功**：這個範圍依賴對當前類`有效`
    * 編譯**失敗**：這個範圍依賴對當前類`無效`

## 6.1 依賴範圍

* **標籤的位置**：dependencies/dependency/**scope**
* **標籤的可選值**：
    * **compile**（`默認值`）
    * **test**
    * **provided** 已提供的
    * system
    * runtime
    * **import**
* **說明**：
    * **main目錄（空間）**、**test目錄（空間）**：
        * **有效**：編譯`成功`
        * **無效**：編譯`失敗`
    * **開發過程（時間）**：使用**圖型開發工具**是否`能import該package`
        * 有效：可以import
    * **部署到服務器（時間）**：是否`參與打包`
        * 有效：參與打包
        * **無效**：`不參與打包`

## 6.2 compile 和 test 對比

|                    | main目錄（空間） | test目錄（空間） | 開發過程（時間） | 部署到服務器（時間） |
|--------------------|------------|------------|----------|------------|
| **compile**（`默認值`） | `有效`       | 有效         | 有效       | `有效`       |
| **test**           | `無效`       | 有效         | 有效       | `無效`       |

## 6.3 compile 和 provided 對比

|                     | main目錄（空間） | test目錄（空間） | 開發過程（時間） | 部署到服務器（時間） |
|---------------------|------------|------------|----------|------------|
| **compile** （`默認值`） | 有效         | 有效         | 有效       | `有效`       |
| **provided**        | 有效         | 有效         | 有效       | `無效`       |

* **provided使用案例**：將SpringBoot打包成war檔時，需要排除內部的Tomcat，需要將此內部的依賴設定為provided
    * **原因**：`實際開發需要使用到，但部署卻不需要`
* **provided** `已提供的`，專案部署到伺服器，`伺服器中已經提供了的jar包，就不需要進行額外的打包到伺服器 `

## 6.4 結論

* **compile**：通常使用的第三方框架的 jar包。
* **test**：**測試過程**中使用的 jar包，以 test 範圍依賴進來。 如 junit。
* **provided**：**開發過程中需要用到**的「`伺服器上的 jar包`」通常以 **provided** 範圍依賴進來。
    * 例如 **servlet-api**、**jsp-api**。 而這個範圍的 jar 包之所以不參與部署、不放進 war 包，就是`避免和伺服器上已有的同類
      jar 包產生衝突，同時減輕伺服器的負擔`。 說白了就是：“伺服器上已經有了，你就別帶啦！”
        ```xml
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
        ```

# 7. 測試依賴的傳遞性

* **概念**：**A依賴B，B依賴C**
    * **那麼在 A 沒有配置對 C 的依賴的情況下，A 裡面能不能直接使用 C**？
* **傳遞的原則**：**在 A 依賴 B，B 依賴 C 的前提下**，C 是否能夠傳遞到 A，**取決於 B 依賴 C 時『所使用的依賴範圍』**。
    * B 依賴 C 時使用 **compile** 範圍：**可以傳遞**
    * B 依賴 C 時使用 **test** 或 **provided** 範圍：**不能傳遞**
        * 所以需要這樣的 jar 包時，就必須在需要的地方明確配置依賴才可以。

## 7.1 測試 - 使用 compile 範圍依賴 spring-core

* **測試**：pro01-maven-web 依賴spring-core
* **操作**：POM添加spring-core
    ```xml
    <!-- compile 範圍導入spring-core，測試依賴傳遞性 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>4.0.0.RELEASE</version>
    </dependency>
    ```
    * **顯示當前依賴結構**(`樹形結構`)： mvn dependency:**tree**
      ```text
      [INFO] com.nicolas.maven:pro01-maven-java:jar:1.0-SNAPSHOT
      [INFO] +- junit:junit:jar:4.12:test
      [INFO] |  \- org.hamcrest:hamcrest-core:jar:1.3:test
      [INFO] \- org.springframework:spring-core:jar:4.0.0.RELEASE:compile
      [INFO]    \- commons-logging:commons-logging:jar:1.1.1:compile
      ```
    * **當前依賴列表**：mvn dependency:**list**
        ```text
        [INFO]    junit:junit:jar:4.12:test -- module junit (auto)
        [INFO]    org.hamcrest:hamcrest-core:jar:1.3:test -- module hamcrest.core (auto)
        [INFO]    org.springframework:spring-core:jar:4.0.0.RELEASE:mav -- module spring.core (auto)
        [INFO]    commons-logging:commons-logging:jar:1.1.1:compile -- module commons.logging (auto)
        ```

# 8. 測試依賴的排除

* **阻斷依賴的傳遞**
* **概念**：當 A 依賴 B，B 依賴 C 而且 C 可以傳遞到 A 的時候，**A 不想要 C，需要在 A 裡面把 C 排除掉**。
    * **而往往這種情況都是為了避免 jar 包之間的衝突**。

## 8.1 測試 - 排除spring-core中的commons-logging

* 使用 **exclusions標籤**、**exclusion標籤**
    * **指定要排除的依賴的座標**（`不需要寫version`）
        ```xml
        <!-- 依賴Java包 -->
       <dependency>
            <groupId>com.nicolas.maven</groupId>
            <artifactId>pro01-maven-java</artifactId>
            <version>1.0-SNAPSHOT</version>
            <!-- 配置依賴排除 -->
            <exclusions>
                <!-- 在exclusion標籤中配置一個具體的排除，排除commons-logging，不要傳遞到當前的 pro02-maven-web -->
                <exclusion>
                      <!-- 指定要排除的依賴的座標（不需要寫version） -->
                     <!-- 不管哪個版本都會被排除 -->
                      <groupId>commons-logging</groupId>
                      <artifactId>commons-logging</artifactId>
                </exclusion>
              </exclusions>
        </dependency>
        ```
* 排除後，**顯示當前依賴結構**(`樹形結構`)： mvn dependency:**tree**
    ```text
    [INFO] com.nicolas.maven:pro02-maven-web:war:1.0-SNAPSHOT
    [INFO] +- junit:junit:jar:4.11:test
    [INFO] |  \- org.hamcrest:hamcrest-core:jar:1.3:test
    [INFO] +- javax.servlet:javax.servlet-api:jar:3.1.0:provided
    [INFO] \- com.nicolas.maven:pro01-maven-java:jar:1.0-SNAPSHOT:compile
    [INFO]    \- org.springframework:spring-core:jar:4.0.0.RELEASE:compile
    ```

# 9. 繼承

## 9.1 概念

* Maven工程之間，**A 工程繼承 B 工程**
    * **B 工程**：**父工程**
    * **A 工程**：**子工程**
* 本質上是 `A 工程的 pom.xml 中的配置繼承了 B 工程中 pom.xml 的配置`。

## 9.2 作用

* 在**父工程**中**統一管理專案中的依賴訊息**，具體來說是管理依賴資訊的版本。

## 9.3 舉例

* 在一個工程中**依賴多個 Spring 的 jar 包**
    * 使用 Spring 時要**求所有 Spring 自己的 jar 套件版本必須一致**。
    * 為了能夠**對這些 jar 套件的版本進行統一管理**，我們`使用繼承`這個機制，將`『所有版本資訊』統一在『父工程』中進行管理`。

## 9.4 操作

### 9.4.1 創建父工程

* **工程名**：pro03-maven-parent
* **指令**： **mvn archetype:generate**
* 創建完成父工程後
    * **打包方式**：調整成`pom`
        * `只有打包方式為 pom 的 Maven 工程能夠管理其他 Maven 工程`
    * 移除 src資料夾：打包方式為 pom 的 Maven 工程中**不寫業務代碼**，專門管理其他工程的工程

### 9.4.2 創建模塊工程(子工程)

* **目標**：**創建3個子工程**
    * pro04-maven-module
    * pro05-maven-module
    * pro06-maven-module
* **步驟**：
    1. 進入pro03-maven-parent目錄
    2. 執行 **mvn archetype:generate** 指令：建立模組工程

### 9.4.3 查看被新增內容的父工程 pom.xml

* 下面 **modules** 和 **module** 標籤是**聚合功能的配置**，Maven自動配置
    ```xml
    <!-- 聚合配置 -->
    <modules>
        <module>pro04-maven-module</module>
        <module>pro05-maven-module</module>
        <module>pro06-maven-module</module>
    </modules>
    ```

### 9.4.4 解讀子工程的 pom.xml

* 子工程的 groupId 和 父工程的groupId 一樣，可以省略
* 子工程的 version 和 父工程的version 一樣，可以省略

```xml
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <modelVersion>4.0.0</modelVersion>

    <!-- parent標籤，給當前工程配置父工程 -->
    <parent>
        <!-- 指定父工程坐標，找到父工程 -->
        <groupId>com.nicolas.maven</groupId>
        <artifactId>pro03-maven-parent</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <!-- 子工程的 groupId 和 父工程的groupId 一樣，可以省略 -->
    <!-- <groupId>com.nicolas.maven</groupId> -->
    <!-- 子工程的version 和 父工程的version一樣，可以省略 -->
    <!-- <version>1.0-SNAPSHOT</version> -->

    <!-- 省略groupId、version 標籤，此工程的坐標『可以只保留artifactId』 -->
    <artifactId>pro04-maven-module</artifactId>

</project>
```

### 9.4.5 在父工程中配置依賴的統一管理

* **使用**：
    * 使用**dependencyManagement**標籤**配置對依賴的管理**
    * 被管理的依賴**並沒有真正被引入到每個子工程**
    * 在子工程中也要指定依賴
* **父工程 pom.xml**

```xml
<!-- 父工程統一管理依賴資訊 -->
<!-- 使用dependencyManagement標籤配置對依賴的管理 -->
<!-- 被管理的依賴並沒有真正被引入到每個子工程-->
<!-- 在子工程中也要指定依賴 -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>4.0.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>4.0.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>4.0.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-expression</artifactId>
            <version>4.0.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>4.0.0.RELEASE</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

* **子工程 pom.xml**
    * 對於父工程所管理的依賴，子工程可以不寫version
        * **省略**version標籤：子工程`採用父工程管理的版本`
        * **不省略**version標籤：
            * 配置的版本與父工程**一至**
            * 配置的版本與父工程**不一至**：子工程所指定的版本`，覆蓋父工程指定的版本`
    ```xml
    <dependencies>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-expression</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aop</artifactId>
    </dependency>
  </dependencies>
    ```

### 9.4.6  在父工程中升级依赖信息的版本

* **一處修改出出生效**
* **spring-core**：4.0.0.RELEASE => 4.1.0.RELEASE
    ```xml
    <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>4.1.0.RELEASE</version>
    </dependency>
    ```
* 子工程的spring-core版本號：org.springframework:spring-core:jar:4.1.0.RELEASE

### 9.4.6  在父工程中稱聲明自定義屬性

* 在**properties**標籤中**聲明自定義屬性**
    * 標籤名：屬性名
    * 標籤值：屬性值
* 在需要的地方**使用${}的形式**來**引用自訂的屬性名稱**
* 例：
    * **設定屬性**
      ```xml
      <properties>
          <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
          <!-- 創建自定義的屬性標籤 -->
          <!-- 標籤名：屬性名 -->
          <!-- 標籤值：屬性值 -->
          <spring.version>4.1.0.RELEASE</spring.version>
      </properties>
      ```
    * 引用 **spring.version 屬性**：**${標籤名}** (`屬性表達式`)
      ```xml
      <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
         <!-- 通過引用屬性表達式設定版本號，這樣版本號就成了一個動態值 -->
         <!-- 通過屬性名解析後，才知道具體是什麼值 -->
        <version>${spring.version}</version>
      </dependency>
      ```

# 10.聚合

## 10.1 Maven 中的聚合

* 使用一個『**總工程**』將各個『**模組工程**』`匯集`起來，作為一個整體對應完整的專案。
    * 項目：整體
    * 模組：部分
* **概念的對應**：
    * 從**繼承關係**角度來看：
        * 父工程
        * 子工程
    * 從**聚合關係**角度來看：
        * 總工程
        * 模組工程

## 10.2 聚合配置

* 在總工程中配置 **modules**：
  ```xml
    <!-- 聚合配置 -->
    <modules>
        <module>pro04-maven-module</module>
        <module>pro05-maven-module</module>
        <module>pro06-maven-module</module>
    </modules>
    ```

## 10.3 循環依賴問題

* 如果 A 工程依賴 B 工程，B 工程依賴 C 工程，**C 工程反過來依賴 A 工程**，那麼在**執行建置作業時會報下面的錯誤**：
    * `[ERROR] [ERROR] The projects in the reactor contain a cyclic reference:`

# 11. -D 參數

* **-D**：表示後面要**附加命令參數**
    * **字母 D** 和**後面的參數**是`緊鄰`的，**中間沒有任何其它字符**

## 11.1 跳過測試

* 參數：maven.test.skip=true
* 指令：mvn clean package -Dmaven.test.skip=true
    ```text
    [INFO] --- surefire:3.1.2:test (default-test) @ pro01-module-java ---
    [INFO] Tests are skipped.
    ```