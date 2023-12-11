# 1. 項目管理的角度

* Maven 提供如下功能：
    * 專案對像模型(POM)：將整個專案本身抽象化、封裝為應用程式中的一個對象，以便於管理與操作。
    * 全域性建置邏輯重複使用：Maven對整個建置流程進行封裝之後，程式設計師只需要指定配置信息即可完成建置。 讓
      恟達辻程從Ant的「編程式』升級到了Maven的『聲明式』。
    * 構件的標準集合：在Maven提供的標準框架體系內，所有的構件都可以依照統一的規範生成和使用。
    * 構件關係定義：Maven定義了構件之間的三種基本關係，讓大型應用系統可以使用Maven來進行管理
        * 繼承關係：透過以上到下的繼承關係，將各個子構件中的重複資訊擷取到父構件中統一管理
        * 聚合關係：將多個構件聚合為一個整體，便於統一操作 。
        * 依賴關係：Maven定義了依賴的範圍、依賴的傳遞、依賴的排除、版本仲裁機制等一系列規範和標準，
          讓大型專案可以有序容納數百甚至更多依賴 。
    * 插件目標系統：Maven核心程式定義抽象的生命週期，然後將插件的目標綁定到生命週期中的特定階段，實
      現了標準與具體實現解耩合，讓Maven程式極冥擴充性
    * 項目描述資訊的維護：我們不僅可以在POM中聲明項目描述信息，還可以將整個項目相關信息收集起來生成
      HTML頁面組成的一個可以直接存取的網站。 這些項目描述資訊包括：
        * 公司或組織訊息
        * 專案許可證
        * 開發成員訊息
        * issue管理訊息
        * SCM訊息

# 2. POM的四個層次

* 沒有明確聲明父POM的話，**默認的父POM是SuperPOM**
    * 類似JAVA的Object為所有類的父類的概念

## 2.1 超級POM(SuperPOM)

* **SuperPOM**是Maven的**預設POM**。
    * 除非明確設置，否則所有POM都擴展SuperPOM，這意味著SuperPOM中指定的配置由您為專案倉的POM繼承。

## 2.2 父POM

* 和Java類別一樣，`POM之間`其實也是**單繼承**的。
* 如果我們給POM指定了父POM那麼**繼承關係**：**當前POM** --> **父POM** --> **超級POM**

## 2.3 有效POM

### 2.3.1 概念

* 有效POM英文翻譯為effectivePOM
* 概念：在POM的繼承關係中，子POM可以覆寫父POM 中的配置，如果子POM沒有覆蓋，那麼父POM中的配置將會被繼承。
    * 依照這個規則，繼承關係中的**所有POM 疊加在一起**，就**得到了一個『最終生效』的POM**。
    * 顯然Maven實際運行過程中，**執行建置操作**就是`按照這個最終生效的POM來運作的`。
    * 這個最終生效的POM就是**有效POM**，英文叫**effectivePOM**

### 2.3.2 查看有效POM

* **指令**：`mvn help:effective-pom`

## 2.4 小節

* 綜上所述，平常我們**使用**和**配置**的`POM`其實大致是由**四個層次組成**的：
    * **超級POM**：所有POM**預設繼承**，只是有直接和間接之分。
    * **父POM**：這一層可能沒有，可能有一層，也可能有很多層。
    * 目前pom.xml配置的POM：我們最多關注且最多使用的一層。
    * **有效POM**：**隱合的一層**，但是`實際上真正生效的一層`。

# 3. 屬性的聲明和引用

## 3.1 help 插件的各個目標

| 目標                      | 說明                           |
|-------------------------|------------------------------|
| help:active-profile     | 列出當前己激活的profile              |
| help:all-profile        | 列出目前工程所有可用profile            |
| help:describle          | 描述一個插件和/或Mojo的厲性             |
| help:effective-pom      | 以XML格式展示有效POM                |
| help:effective-settings | 為目前工程以XML格式展示計算得到的settings配置 |
| help:evaluate           | 計算使用者在互動模式下給出的Maven表達式       |
| help:system             | 顯示平台詳細資訊列表，如係統厲性和環境變量        |

## 3.2 help:evaluate 查看屬性值

1. **定義屬性**
    ```xml
    <properties>
        <nicolas.hello>maven hello</nicolas.hello>
    </properties>
    ```
2. **運行命令**： mvn help:evaluate
    * `Enter the Maven expression i.e. ${project.groupId} or 0 to exit?` **可以輸入屬性值**
3. 互動區 輸入屬性值：${nicolas.hello}
4. return屬性值 => maven hello

## 3.3 通過Maven 訪問系統屬性

* 指令：mvn help:system

### 3.3.1 Java 訪問系統屬性

```java
import java.util.Properties;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        //JAVA 訪問系統屬性
        Properties properties = System.getProperties();
        Set<Object> porNameSet = properties.keySet();
        for (Object porName : porNameSet) {
            String porValue = properties.getProperty((String) porName);
            System.out.println(porName + " = " + porValue);
        }
    }
} 
```

## 3.4 訪問系統環境變量

* ${**env**.`系統環境變量`}
* **前提**：使用 mvn help:system

```text
[INFO] Enter the Maven expression i.e. ${project.groupId} or 0 to exit?:
${env.JAVA_HOME}
[INFO] 
/Users/nicolas/.jenv/versions/11.0
```

## 3.5 訪問project屬性

* **含義**：使用表達式 **$(project.xxx}** 可以 **存取目前POM中的元素值**。

### 3.5.1 訪問一級標籤

* **表達式**：**$(project.xxx}**
* **目標**：訪問 `artifactId標籤`
    * **${project.artifactId}**
    ```txt
    [INFO] Enter the Maven expression i.e. ${project.groupId} or 0 to exit?:
    ${project.artifactId}
    [INFO]
    pro01-maven-java
    ```

### 3.5.2 訪問子標籤

* **表達式**：**$(project.標籤名.子標籤名}**
* **目標**：訪問 `parent標籤中的groupId標籤`
    * **${project.parent.groupId}**
    ```text
    [INFO] Enter the Maven expression i.e. ${project.groupId} or 0 to exit?:
    ${project.parent.groupId}
    [INFO]
    com.nicolas.maven
    ```

### 3.5.3 訪問列表標籤

* **表達式**：**$(project.標籤名[下標]}**
* **目標**：訪問 **dependencies標籤**中的**第一個子標籤**
    * **${project.dependencies[0]}**
    ```text
    [INFO] Enter the Maven expression i.e. ${project.groupId} or 0 to exit?:
    ${project.dependencies[0]}
    ```

### 3.5.6 訪問 settings.xml 全局配置

* **表達式**：**$(setting.標籤名}**
* **目標**：訪問 **localRepository標籤**
    * **${settings.localRepository}**
    ```text
    [INFO] Enter the Maven expression i.e. ${project.groupId} or 0 to exit?:
    ${settings.localRepository}
    [INFO]
    /Users/nicolas/.m2/repository
    ```

### 3.5.7 用途

* 在目前pom.xml檔中引用屬性
* 資源過濾功能：在非Maven配置文件中引用屬性，由Maven在處理資源時將引用厲性的表達式替換為屬性值

# 4. build標籤詳解

* 我們配置的`build標籤`都是**超級POM配置**的**疊加**。
    * 那我們又為什麼要在預設配置的基礎上疊加呢？ **=>** 在`預設配置無法滿足需求`的時候**定製構建過程**。

## 4.1 Build 標籤的組成

* 包含**三大主體**

### 4.1.1 定義約定的目錄結構

* 參考

```xml

<project>
    <sourceDirectory>D:\idea2019workspace\atguigu-maven-test-prepare\src\main\java</sourceDirectory>
    <scriptSourceDirectory>D:\idea2019workspace\atguigu-maven-test-prepare\src\main\scripts</scriptSourceDirectory>
    <testSourceDirectory>D:\idea2019workspace\atguigu-maven-test-prepare\src\test\java</testSourceDirectory>
    <outputDirectory>D:\idea2019workspace\atguigu-maven-test-prepare\target\classes</outputDirectory>
    <testOutputDirectory>D:\idea2019workspace\atguigu-maven-test-prepare\target\test-classes</testOutputDirectory>

    <resources>
        <resource>
            <directory>D:\idea2019workspace\atguigu-maven-test-prepare\src\main\resources</directory>
        </resource>
    </resources>

    <testResources>
        <testResource>
            <directory>D:\idea2019workspace\atguigu-maven-test-prepare\src\test\resources</directory>
        </testResource>
    </testResources>
    <directory>D:lidea2019workspace\atquigu-maven-test-prepare\target</directory>
</project>
```

* 各目錄作用

  | 目錄名                   | 作用             |
  |-----------------------|----------------|
  | sourceDirectory       | 主體程序存放目錄       |
  | scriptSourceDirectory | 腳本源程序存放目錄      |
  | testSourceDirectory   | 測試源程序存放目錄      |
  | outputDirectory       | 主體原始程序編譯結果輸出目錄 |
  | testOutputDirectory   | 測試原始程序編譯結果輸出目錄 |
  | resources             | 主體資源文件存放目錄     |
  | testResources         | 測試資源文件存放目錄     |
  | directory             | 建置結果輸出目錄       |

### 4.1.2 備用插件管理

* pluginManagement標籤存放著幾個極少用到的插件：
    * maven-antrun-plugin
    * maven-assembly-plugin
    * maven-dependency-plugin
    * maven-release-plugin
* pluginManagement標籤 管理插件的方式就像 dependencyManagement標籤，子工程使用時，可以省略版本號
    * 被spring-boot-dependencies管理的播件
        ```xml
        <plugin>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-maven-plugin</artifactId>
           <version>2.6.2</version>
        </plugin>
        ```
    * **子工程**使用插件
        ```xml
         <build>
                <plugins>
                     <plugin>
                         <groupId>org.springframework.boot</groupId>
                         <artifactId>spring-boot-maven-plugin</artifactId>
                     </plugin>
            </plugins>
         </build>
        ```

### 4.1.3 生命週期插件

* **plugins標籤**：存放的是**默認生命週期中**實際會用到的插件。
* **plugins標籤-結構**：
    ```xml
    <plugin>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>3.1</version>
      <executions>
          <execution>
              <id>default-compile</id>
              <phase>compile</phase>
              <goals>
                  <goal>compile</goal>
              </goals>
          </execution>
          <execution>
              <id>default-testCompile</id>
              <phase>test-compile</phase>
              <goals>
                    <goal>testCompile</goal>
              </goals>
           </execution>
      </executions>
  </plugin>
  ```

#### 4.1.3.1 坐標部分

* **artifactId**、**version** 標籤，定義插件的坐標，`Maven的自帶插件省略groupId`

#### 4.1.3.2 執行部分

* **executions標籤**：可以配置多個execution標籤
* **execution標籤**：
    * **id**：唯一標示
    * **phase**：**綁定**一個生命週期階段
    * **goals** \ **goal**：指定生命週期的**目標**
        * goals標籤：可以配置`多個goal`，表示一個生命週期的環節可以對應多個目標
        * **通常配置ㄧ個goal**
* 另外插件目標的執行過程可以進行配置，例如 maven-site-plugin插件的sit目標
    ```xml
    <execution>
        <id>default-site</id>
        <phase>site</phase>
        <goals>
            <goal>site</goal>
        </goals>
        <configuration>
            <outputDirectory>D:\idea2019workspace\atguigu-maven-test-prepare\target\site</outputDirectory>
            <reportPlugins>
               <reportPlugin>
                   <groupId>org.apache.maven.plugins</groupId>
                   <artifactId>maven-project-info-reports-plugin</artifactId>
               </reportPlugin>
            </reportPlugins>
        </configuration>
    </execution> 
    ```
    * **configuration標籤**內進行設定時`使用的標籤是插件本身定義的`。
        * 就以**maven-site-plugin**插件為例：
            * 它的核心類是org.apache.maven.plugins.site.render.SiteMojo，在這個類別中我們看到了**outputDirectory屬性**
                *
              outputDirectory屬性：` <outputDirectory>D:\idea2019workspace\atguigu-maven-test-prepare\target\site</outputDirectory>`
            * SiteMojo的父類：AbstractSiteRenderingMojo，在父類中看到**reportPlugins屬性**

#### 4.1.3.3 結論

* 每個插件能夠做哪些設定都是各個插件規定的，無法一概而論。

## 4.2 指定JDK版本

### 4.2.1 提出問題

> 前面我們在settings.xml中配置了JDK版本，那麼將來把Maven工程部署都伺服器上，脫離了settings.xml配
> 置，如何保證程序正常運作呢？ 想法就是我們直接把JDK版本資訊告訴負責編譯操作的maven-compiler-plugin
> 插件，讓它在建置過程中，按照我們指定的資訊工作
>

### 4.2.2 暫時取消 settings.xml 配置

* 為了測試maven-compiler-plugin插件進行配置的效果，我們暫時取消settings.xml中的profile配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings>
    <!--<profiles>-->
    <!--    <profile>-->
    <!--        <id>jdk-11</id>-->
    <!--        <activation>-->
    <!--            <activeByDefault>true</activeByDefault>-->
    <!--            <jdk>11</jdk>-->
    <!--        </activation>-->
    <!--        <properties>-->
    <!--            <maven.compiler.source>11</maven.compiler.source>-->
    <!--            <maven.compiler.target>11</maven.compiler.target>-->
    <!--            <maven.compiler.compilerVersion>11</maven.compiler.compilerVersion>-->
    <!--        </properties>-->
    <!--    </profile>-->
    <!--</profiles>-->
</settings>
```

### 4.2.3 編譯使用lambda表達式的代碼

* 使用JDK-1.8的方式，編譯會出錯
    * 原因：maven 默認支持JDK-1.5

### 4.2.4 配置構建過程

```xml
<!--  build標籤：告訴Maven 你的建構行為，要開始訂製-->
<build>
    <!-- plugins標籤：建構過程需要使用到哪一些插件-->
    <plugins>
        <!-- plugin標籤：這是我要指定的一個具體的插件 -->
        <plugin>
            <!-- 插件的座標。 此處引用的maven-compiler-plugin插件不是第三方的，是一個Maven自帯的插件。-->
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <!-- configuration標籤：設定maven-compiler-plugin插件  -->
            <configuration>
                <!-- 具體配器資訊,會因為插件不同、需求不同而有所差異，目前看起有 maven-compiler-plugin有三個屬性 source、target、 encoding-->
                <source>1.8</source>
                <target>1.8</target>
                <encoding>UTF-8</encoding>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### 4.2.5 補充說明

* **source標籤**：調用java編譯器(javac)時，傳入-source參數
    * javac的-source參數：提供寫入**指定發行版**的**源兼容性**
        * 我們寫程式碼是按JDK1.8寫的一一這就是『**源兼容性**』裡的『**源**』。
        * **指定發行版**就是我們指定的JDK1.8。
        * 『**兼容性**』是誰`和誰兼容呢`？ 現在原始碼是既定的，所以就是**要求編譯器使用指定的JDK版本**來`相容我們的源代碼`
    * 可以在 **properties標籤**中，使用 **maven.compiler.source標籤** 來指定JDK版本
        ```xml
        <properties>
            <maven.compiler.source>11</maven.compiler.source>
            <maven.compiler.target>11</maven.compiler.target>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        </properties>
        ```

* **target標籤**：調用java編譯器(javac)時，傳入-target參數
    * javac的-target參數：生成特定VM版本的類文件
        * **VM**：JVM
        * **類文件**：『*.class字節碼』文件
        * **整體意思**：就是原始檔編譯後，產生的『*.class字節碼』檔案要`符合指定的JVM版本`

### 4.2.6 兩種配置方式比較

* **settings.xml**配置：**僅在本地生效**，如果脫離當前settings.xml能夠覆蓋的範圍，則無法生效
* 當前Maven工程 **pom.xml** 中配置：無論在**哪個環境執行**編譯等構建環境**都生效**

## 4.3 典型應用：SpringBoot訂製化打包

* 使用 **spring-boot-maven-plugin**：是S`pringBoot的提供的插件`，非Maven自帶插件
* **作用**：**改變打包行為**
    * **maven-jar-plugin**：生成的jar包，無法使用 java -jar xxx.jar的方式啟動
    * **spring-boot-maven-plugin**：使用 java -jar xxx.jar的方式啟動
        * 調用 spring-boot:repackage

### 4.3.1 spring-boot-maven-plugin 中 7個目標

* mvn 插件前綴:目標名稱

| 目標名稱          | 作用                                                                | mvn 指令                      |
|---------------|-------------------------------------------------------------------|-----------------------------|
| build-image   | 用於建置 Docker 映像。 透過使用該目標，您可以輕鬆地將 Spring Boot 應用程式打包到一個 Docker 映像中。 | mvn spring-boot:build-image |
| build-info    | 生成建構資訊文件，其中包含有關專案的建置和依賴關係的資訊。                                     | mvn spring-boot:build-info  |
| help          | 顯示有關插件和其目標的幫助信息                                                   | mvn spring-boot:help        |
| **repackage** | **重新打包可執行的 JAR 檔**                                                | mvn spring-boot:repackage   |
| run           | 使用 Maven 啟動 Spring Boot 應用程序                                      | mvn spring-boot:run         |
| start         | 啟動 一個已打包的 Spring Boot 應用程序                                        | mvn spring-boot:start       |
| stop          | 停止 一個已打包的 Spring Boot 應用程序                                        | mvn spring-boot:stop        |

## 4.4 典型應用：Mybatis 逆向工程

* 使用Mybatis的逆向工程需要使用以下配置
    * **MBG插件的特點**：是**需要提供插件所需的依賴**
        * org.mybatis.generator
        * com.mchange
        * mysql

```xml

<build>
    <plugins>
        <!--  具體插件，逆向工程的操作是以構建過程中插件的形式出現的-->
        <plugin>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-maven-plugin</artifactId>
            <version>1.3.0</version>
            <!-- 插件依賴-->
            <dependencies>
                <!--  逆向工程核心依賴-->
                <dependency>
                    <groupId>org.mybatis.generator</groupId>
                    <artifactId>mybatis-generator-core</artifactId>
                    <version>1.3.2</version>
                </dependency>

                <!--  DB連接-->
                <dependency>
                    <groupId>com.mchange</groupId>
                    <artifactId>c3p0</artifactId>
                    <version>0.9.2</version>
                </dependency>

                <!--  MySQL驅動-->
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <version>5.1.8</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```

# 5. 依賴配置補充

## 5.1 依賴範圍 scope

### 5.1.1 import

* **\<scope>import\</scope>**
* **管理依賴**：最基本的辦法是**繼承父工程**，但是和Java類別一樣，**Maven也是單繼承的**。
    * 如果不同體系的依賴資訊封裝在不同POM中了，`沒辦法繼承多個父工程怎麼辦？`這時就可以使用import依賴範圍
* **使用要求**：
    * 打包類型需為pom
    * 必須放在**dependencyManagement**中
* 範例：引入SpringBoot\SpringCloud的依賴
    * 將**spring-cloud-dependencies中所配置的依賴**替換掉spring-cloud-dependencies這個配置
    * `spring-cloud-dependencies`、`spring-cloud-alibaba-dependencies`、`spring-boot-dependencies` **本身都是POM**

  ```xml
      <dependencyManagement>
          <dependencies>

          <!--springCloud 依賴導入-->
              <dependency>
                  <groupId>org.springframework.cloud</groupId>
                  <artifactId>spring-cloud-dependencies</artifactId>
                  <version>Hoxton.SR1</version>
                  <type>pom</type>
                  <scope>import</scope>
              </dependency>
  
          <!--springCloud alibaba 依賴導入-->
              <dependency>
                  <groupId>com.alibaba.cloud</groupId>
                  <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                  <version>2.2.2.RELEASE</version>
                  <type>pom</type>
                  <scope>import</scope>
              </dependency>
              
            <!--springBoot 依賴導入-->
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.7.15</version>
                <type>pom</type>
                <scope>import</scope>
             </dependency>

          </dependencies>
      </dependencyManagement>
    ```

#### 5.1.1.1 spring-boot-dependencies vs  spring-boot-starter-parent

* 使用 **spring-boot-dependencies**：如果您的專案**已經有一個現有的父項目**，或者您希望**更自由地選擇專案的 Maven
  外掛程式和配置**
* 使用 **spring-boot-starter-parent**：**繼承** Spring Boot 的預設配置以及一些外掛程式配置，以及集中管理 Spring Boot 版本

### 5.1.2 system

* **\<scope>system\</scope>**
* 以Windows系統環境下開發為例，假設現在D:
  \tempare\atguigu-maven-test-aaa-1.0-SNAPSHOT.jar想要導入到我們的專案中，此時我們就可以將**依賴配置**為**system範圍**
    ```xml
    <dependency>
        <groupId>com.atguigu.maven</groupId>
        <artifactId>atquiqu-maven-test-aaa</artifactId>
        <version>1.0-SNAPSHOT</version>
        <systemPath>D:temparelatcuiqu-maven-test-aaa-1.0-SNAPSHOT.jar</systemPath>
        <scope>system</scope>
    </dependency>
    ```
* **問題**：這樣引入依賴**具備不可移植性**，所以**不要使用**

### 5.1.3 runtime

* 運行時起作用，實際開發用不上 => 例：DB連線、spring-boot-develops
    ```xml
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.27</version>
            <scope>runtime</scope>
        </dependency>
        <!-- springBoot 熱部署        -->
        <dependency>
             <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
    </dependencies>
    ```

## 5.2 可選依賴 optional

### 5.2.1 配置舉例 - SpringBoot熱部署

```xml
<!-- springBoot 熱部署   -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### 5.2.2 本質含義

* **可選**：**可有可無**
* **核心含義**：**ProjectX**依賴**ProjectA**，『`A中有部分的代碼依賴B，但該代碼X並無使用`』，那麼對X來說**B就是『可有可無』**

## 5.3 版本仲裁

### 5.3.1 最短路徑優先

* 對模組 `pro25-module-a` 來說，Maven會**採納1.2.12版本**。
    * pro25-module-a
        * pro26-module-b --> pro27-module-c --> log4j **1.2.17**
        * pro28-module-b --> log4j **1.2.12**

### 5.3.2 路徑相同時，先聲明者優先

* 使用哪一個，取決於在pro29-module-x中，對`pro30-module-y`和`pro31-module-z`兩個模組的依賴**哪一個先聲明**。
    * pro29-module-x
        * pro30-module-y --> log4j **1.2.17**
        * pro31-module-z --> log4j **1.2.12**

# 6. Maven 自定義插件

## 6.1 插件開發-步驟

### 6.1.1 創建工程

* **Maven工程名**需為：
    * **$(prefix)**-maven-plugin => hello-**maven**-plugin
    * maven-**$(prefix)**-plugin => maven-**hello**-plugin

### 6.1.2 設定打包方式

```xml

<packaging>maven-plugin</packaging>
```

### 6.1.3 引入依賴

* 下面方式**二選一**：**本質無差別**
    * 將來在文檔註釋中使用註解，**實現**`Mojo接口`或`AbstractMojo抽象類`
  ```xml
  <dependency>
  <groupId>org.apache.maven</groupId>
  <artifactId>maven-plugin-api</artifactId>
  <version>3.3.9</version>
  </dependency>
  ```
    * 將來直接使用註解，**只能實現Mojo接口**
  ```xml
  <dependency>
  <groupId>org.apache.maven.plugin-tools</groupId>
  <artifactId>maven-plugin-annotations</artifactId>
  <version>3.5.2</version>
  </dependency>
  ```

### 6.1.4 創建Mojo類

* Mojo類別是**一個Maven插件的核心類別**
    * 實現**Mojo接口**：每一個Mojo都需要實作`org.apache.maven.plugin.Mojo接口`。
    * **AbstractMojo抽象類**：
        * 實作Mojo接口比較因難
        * 只要實`作AbstractMojo抽象類`的**execute方法**即可。
        ```java
        package com.nicolas.maven;

        import org.apache.maven.plugin.AbstractMojo;
        import org.apache.maven.plugin.MojoExecutionException;
        import org.apache.maven.plugin.MojoFailureException;

        public class MyHelloPlugin extends AbstractMojo {
            @Override
            public void execute() throws MojoExecutionException, MojoFailureException {
                //getLog() 父類方法
                getLog().info("=====> This id First Plugin <======");
            }
        }
        ```

## 6.2 插件配置

### 6.2.1 Mojo類中的配置

* **文檔註釋**中使用註解
    * maven依賴對應：`maven-plugin-api`
    ```java
    import org.apache.maven.plugin.AbstractMojo;
    import org.apache.maven.plugin.MojoExecutionException;
    import org.apache.maven.plugin.MojoFailureException;

    /**
     * @goal sayHello
    */
    public class MyHelloPlugin extends AbstractMojo {
        //指定調用本類execute()方法的目標 >> mvn xxx:sayHello => 就會調用execute()
        @Override
        public void execute() throws MojoExecutionException, MojoFailureException {
            //getLog() 父類方法
            getLog().info("=====> This id First Plugin <======");
        }
    }
    ```
* 直接在**類上標記註解**
    * maven依賴對應：`maven-plugin-annotations`
    * 使用 **@Mojo** 註解
        * **name屬性**：`設定目標名稱`
    ```java
    import org.apache.maven.plugin.AbstractMojo;
    import org.apache.maven.plugin.MojoExecutionException;
    import org.apache.maven.plugin.MojoFailureException;
    import org.apache.maven.plugins.annotations.Mojo;

    //name屬性:指定目標名稱
    @Mojo(name = "sayHello")
    public class MyHelloPlugin extends AbstractMojo {
        //指定調用本類execute()方法的目標 >> mvn xxx:sayHello => 就會調用execute()
        @Override
        public void execute() throws MojoExecutionException, MojoFailureException {
            //getLog() 父類方法
           getLog().info("=====> This id First Plugin <======");
        }
    }
    ```

### 6.2.2  安裝插件

* 要在後續使用插件，就必須**至少將插件安裝到本地倉庫**。
* mvn clean **install**

### 6.2.3 註冊插件

* 將**插件座標**中的**groupld**部分**註冊到settings.xml中**。
    * **當前工程的坐標**
      ```xml
      <project>
          <groupId>com.nicolas.maven</groupId>
          <artifactId>pro99-module-my-plugin</artifactId>
      </project>
      ```
    * **註冊插件**(`settings.xml`)
        ```xml
        <pluginGroups>
            <pluginGroup>com.nicolas.maven</pluginGroup>
        </pluginGroups>  
         ```

## 6.3 使用插件

### 6.3.1 識別插件前綴

* Maven根據插件的`artifactId`來**識別插件前綴**，下列兩種情況

#### 6.3.1.1 前置匹配

* **符合規則**：**$(prefix)**-maven-plugin
* **artifactld**: hello-maven-plugin
* **前綴**：hello

#### 6.3.1.2 中間匹配

* **符合規則**：maven-**$(prefix)**-plugin
* **artitactld**：maven-good-plugin
* **前綴**：good

### 6.3.2 在命令行運行

* **命令**：mvn **插件前綴**:**目標名**
    * **插件前綴**：**hello** => 工程名為：**hello**-maven-plugin
    * **目標名**：**sayHello**
    * `mvn hello:sayHello`
* **打印輸出**：
    ```text
    [INFO] --- hello:1.0-SNAPSHOT:sayHello (default-cli) @ pro05-demo-all-one ---
    [INFO] =====> This id First Plugin <======
    ```

### 6.3.3 配置到build標籤中

* **目標**：在**clean**階段`觸發該插件`
    * **前綴**：hello（**hello**-maven-plugin）
    * **目標**：**sayHello**

```xml

<build>
    <plugins>
        <plugin>
            <groupId>com.nicolas.maven</groupId>
            <artifactId>hello-maven-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
            <!-- executions標籤 執行部分，可以有多個執行部分-->
            <executions>
                <execution>
                    <!--  id:唯一標示（插件前綴）-->
                    <id>hello</id>
                    <!-- 綁定一個生命週期的環境 >> clean-->
                    <phase>clean</phase>
                    <goals>
                        <goal>sayHello</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

* **結果輸出**：在pro28-module-b下 mvn clean
    ```text
    [INFO] --- clean:3.2.0:clean (default-clean) @ pro28-module-b ---
    [INFO] Deleting /Users/nicolas/MavenPractice/pro28-module-b/target
    [INFO]
    [INFO] --- hello:1.0-SNAPSHOT:sayHello (hello) @ pro28-module-b ---
    [INFO] =====> This id First Plugin <======
    ```
    * **自定義插件最少需 mvn install 到本地的Maven目錄**

# 7. profile標籤 詳解

## 7.1 profile 概述

### 7.1.1 單詞說明

* **翻譯**：側面
* **含義**： 項目中每一個運行環境，相當於是項目整體的一個策略

### 7.1.2  項目的不同運行環境

* 通常情況下，會有下列三種運行環境
    * 開發環境
    * 測試環境
    * 生產環境
* 在Maven中，使用**profile機制**來**管理不同環境下的設定**。
    * 但是解決同類問題的類似機制在其他框架中也有，而且從模組劃分的角度來說，持久化層的資訊放在建造工具中配置也違反了『高內聚，低糯合』的原則。

### 7.1.3  profile 聲明和使用的基本邏輯

* 首先為每個環境聲明一個profile
    * 環境A：profileA
    * 環境B:profileB
    * 環境C:profileC
* 然後激活某一個profile

### 7.1.3.1 Spring的方式

* 在 **application.properties** 或 **application.yml** 檔案中，透過設定 **spring.profiles.active** 屬性來『指定已啟動的設定文件』
    * **屬性值**：可以包含**一個**或**多個**用`逗號分隔的設定檔名`。
* **目標**：激活 `dev`、`mq`
    1. 添加兩個properties，檔名為
        * application-**dev**.properties
        * application-**mq**.properties
    2. 設定屬性值
        ```properties
        spring.profiles.active=dev,mq
        ```

### 7.1.4 默認profile

* 即使不配置profile標籤，也使用到profile標籤
    * **原因**：因為根標籤project下所有標籤相當於**都是在設定預設的profile**
    * `一定要有profile要被激活`
* project標籤下除了**modelVersion**和**座標**標籤之外，`其他標籤都可以配置到profile中 `

## 7.2 profile 配置

### 7.2.1 外部視角：配置文件

* **profile**可以在下面**兩種設定檔中設定**：
    * **settings.xml**：全局生效。 其中我們最熟悉的就是配置JDK1.8。
        ```xml
        <profiles>
            <profile>
            <!-- id標籤：唯一標示-->
                <id>jdk-11</id>
                <!-- activation標籤 激活方式-->
                <activation>
                    <!--  activeByDefault：默認激活(true)-->
                    <activeByDefault>true</activeByDefault>
                    <!--  jdk標籤：標示當前標籤可以根據 JDK版本 激活-->
                    <jdk>11</jdk>
                </activation>
                <!-- 其他標籤：當前profile 被激活後要採納的配置-->
                <properties>
                    <maven.compiler.source>11</maven.compiler.source>
                    <maven.compiler.target>11</maven.compiler.target>
                    <maven.compiler.compilerVersion>11</maven.compiler.compilerVersion>
                </properties>
            </profile>
        </profiles> 
        ```
    * **pom.xml**：當前POM生效

### 7.2.2 內部實現：具體標籤

#### 7.2.2.1  profiles/profile 標籤

* 由於profile天然代表眾多可選配置中的一個所以由複數形式的**profiles標籤統一管理**。
* 由於profile標籤**覆蓋了pom.xml中的預設配置**，所以profiles標籤**通常是pom.xml中的最後一個標籤**。

#### 7.2.2.2 id 標籤

* 每個profile都有一個id 標籤，指定**該profile的為一標示**
* 這個**id標籤的值**會在`命令行調用profile時被用到`。
    * **命令格式**：-D\<profileid>

#### 7.2.2.3 其他允許出現的標籤

* 一個profile可以**覆蓋專案的最終名稱**、**專案依賴**、**插件配置**等`各個方面以影響建構行為`
* build
    * defaultGoal
    * finalName
    * resources
    * testResources
    * plugins
* reporting
* modules
* dependencies
* dependencyManagement
* repositories
* pluginRepositories
* properties

## 7.3 激活 profile - activation標籤

### 7.3.1 默認配置 默認被激活

* POM中**沒有在profile標籤**裡的就是**預設的profile**，當然預設被啟動。

### 7.3.2 基於環境信息激活

* 環境信息包含：JDK版本，作業系統參數、檔案、屬性等各方面
* 一個profile一旦被激活，那麼它**定義的所有配置**都會**覆蓋原來POM中對應層次的元素**
* 激活條件結構：
  ```xml
   <profiles>
        <profile>
            <id>dev</id>
            <!--  指定多個激活條件-->
            <activation>
                <activeByDefault>false</activeByDefault>
                <jdk>1.5</jdk>
                <os>
                    <name>Windows XP</name>
                    <family>Windows</family>
                    <arch>x86</arch>
                    <version>5.1.2600</version>
                </os>
                <property>
                    <name>mavenVersion</name>
                    <value>2.0.5</value>
                </property>
                <file>
                    <exists>file2.properties</exists>
                    <missing>file1.properties</missing>
                </file>
            </activation>
        </profile>
    </profiles>
  ```
    * 目前**共有5個激活條件**
        * **activeByDefault**
        * **jdk**
        * **os**
        * **property**
        * **file**
    * **多個激活條件之間是什麼關係呢**？
        * **Maven 3.2.2之前**：滿足其中一個激活條件即可激活（**OR**）
        * **Maven 3.2.2開始**：每個條件都需要滿足（**AND**）

### 7.3.3 命令行激活

* **列出**活動的 profile
    * **命令行**：`mvn help:active-profiles`
* **指定**每個具體profile
    * **命令行**：`mvn compile -P<profile id>`

## 7.4 操作舉例

1. **編寫 Lambda 表達式**
    ```java 
    public class LamdbaTest {
        public static void main(String[] args) {
            new Thread(() -> {
                System.out.println("Hello");
            }).start();
        }
    }
    ```
2. **配置profile**
    * **profile id**：**myJDKProfile**
    * **說明**：使用JDK1.6的方式去編譯，覆蓋默認配置
    ```xml
    <profiles>
        <profile>
            <id>myJDKProfile</id>
            <!-- build 標籤：訂製構建過程-->
            <build>
                <!-- plugins 標籤：使用到哪一些插件-->
                <plugins>
                    <!--  plugin 標籤：指定一個具體的插件-->
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-compiler-plugin</artifactId>
                        <version>3.8.1</version>
                        <!--   configuration 標籤：配置 maven-compiler-plugin 插件-->
                        <configuration>
                            <!-- 每個插件可以配置的參數都是不同的-->
                            <source>1.6</source>
                            <target>1.6</target>
                            <encoding>UTF-8</encoding>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
    ```
3. **執行compile命令**：mvn clean compile -PmyJDKProfile
    * **目標**：`Lambda 表達式 為JDK1.8支持，所以JDK1.6會編譯失敗`

## 7.5 資源屬性過濾

* Maven為了能夠透過profile實現各不同運行環境切換，提供了一種『**資源厲性過濾**』的機制。
    * 透過**屬性替換**實現不同環境使用不同的參數。
* `不建議使用，由各自框架來處理`

### 7.5.1 操作演示

1. **配置profile**
    ```xml
    <!--資源過濾-->
    <profiles>
        <profile>
            <id>devJDBC</id>
            <properties>
                <dev.jdbc.user>root</dev.jdbc.user>
                <dev.jdbc.password>atguigus</dev.jdbc.password>
                <dev.jdbc.url>http://localhost:3306/db_good</dev.jdbc.url>
                <dev.jdbc.driver>com.mysql.jdbc.Driver</dev.jdbc.driver>
            </properties>
            <build>
                <resources>
                    <resource>

                        <!-- 指定的目錄開啟資源過濾功能 -->
                        <directory>src/main/resources</directory>

                        <!-- true：開啟資源過濾功能-->
                        <filtering>true</filtering>
                    </resource>
                </resources>
            </build>
        </profile>
    </profiles>
    ```
2. **創建待處理的資源文件**：jdbc.properties
    ```properties
    dev.user=${dev.jdbc.user}
    dev.password=${dev.jdbc.password}
    dev.url=${dev.jdbc.url}
    dev.driver=${dev.jdbc.driver}
    ```
3. **執行處理資源命令**
    * `mvn clean resources:resources -PdevJDBC`
4. **查看 target/classes/jdbc.properties**

### 7.5.2 includes、excludes 標籤

* **resource標籤**下的**includes**、**excludes** 標籤
    * includes：指定執行resource階段時**要包含**到目標位置的資源
    * excludes：指定執行resource階段時**要排除**的資源

```xml

<build>
    <resources>
        <resource>

            <!-- 指定的目錄開啟資源過濾功能 -->
            <directory>src/main/resources</directory>

            <!-- true：開啟資源過濾功能-->
            <filtering>true</filtering>

            <includes>
                <include>*.properties</include>
            </includes>

            <!-- 排除 happy.properties 資源-->
            <excludes>
                <exclude>happy.properties</exclude>
            </excludes>
        </resource>
    </resources>
</build>
```











