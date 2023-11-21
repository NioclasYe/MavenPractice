# 1. 核心程序

* [MAC\Linux 的 maven 安裝](https://jovepater.com/article/macos-maven-installation/)

## 1.1 下載

* [Maven 官網](https://maven.apache.org)
* [Maven 下載位置](https://maven.apache.org/download.cgi)
    * 選擇 Binary zip archive：
        * **mac/window**：apache-maven-版本號-**bin.zip**
        * **linux**：apache-maven-版本號-**bin.tar.gz**

## 1.2 解壓鎖

1. **解壓縮** apache-maven-版本號-**bin.zip**
2. **移動至** /Users/nicolas/Library/maven/
    * 沒有maven資料夾，自己定義一個

## 1.3 配置 setting.xml

* maven中**存在兩個settings.xml**
    * 位於maven的安裝目錄maven/conf中，作為**全域性性配置**，保證所有的團隊成員都擁有相同的配置
    * {user}/.m2/的目錄中，需要複製conf的settings.xml

### 1.3.1 設定本地倉庫

* `<localRepository>本地倉庫路徑</localRepository>`
* **maven會預設自動產生**：到**當前用戶下**的`.m2/repository`
    * **例**：`/Users/nicolas/.m2/repository`

### 1.3.2 配置鏡像倉庫

* 在**mirrors標籤**中，`添加一個鏡像倉庫`
* **實作**：
    * **註釋** 原有鏡像倉庫範例
    * **添加** 阿里雲鏡像倉庫
        ```xml
        <mirrors>
            <!-- 阿里雲鏡像倉庫 -->
            <mirror>
                <id>nexus-aliyun</id>
                <name>Nexus aliyun</name>
                <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
                <mirrorOf>central</mirrorOf>
            </mirror>
            <!-- 預設 -->
        <!--            <mirror>-->
        <!--                <id>maven-default-http-blocker</id>-->
        <!--                <mirrorOf>external:http:*</mirrorOf>-->
        <!--                <name>Pseudo repository to mirror external repositories initially using HTTP.</name>-->
        <!--                <url>http://0.0.0.0/</url>-->
        <!--                <blocked>true</blocked>-->

        <!--            </mirror>-->
        </mirrors>
        ```

### 1.3.3 配置JDK版本

* 在**profiles標籤**中，`配置JDK版本`
    * maven默認 JDK版本為1.5
* **實作**：配置 JDK 11
    ```xml
      <profiles>
        <profile>
          <id>jdk-11</id>
          <activation>
          <activeByDefault>true</activeByDefault>
          <jdk>11</jdk>
          </activation>
          <properties>
            <maven.compiler.source>11</maven.compiler.source>
            <maven.compiler.target>11</maven.compiler.target>
            <maven.compiler.compilerVersion>11</maven.compiler.compilerVersion>
          </properties>
        </profile>
      </profiles>
    ```

# 2. 環境變數

## 2.1 配置MAVEN_HOME

* export **MAVEN_HOME**=`/Users/nicolas/Library/maven/apache-maven-3.9.5`
* export **PATH**=`"$MAVEN_HOME/bin:$PATH"`

## 2.2 測試

* **指令**：mvn -v
    * 顯示當前 Maven版本
* **結果輸出**：
    ```text 
    Apache Maven 3.9.5 (57804ffe001d7215b5e7bcb531cf83df38f93546)
    Maven home: /Users/nicolas/Library/maven/apache-maven-3.9.5
    Java version: 11.0.19, vendor: Eclipse Adoptium, runtime: /Library/Java/JavaVirtualMachines/temurin-11.jdk/Contents/Home
    Default locale: zh_TW_#Hant, platform encoding: UTF-8
    OS name: "mac os x", version: "13.2.1", arch: "aarch64", family: "mac"
    ```
  

