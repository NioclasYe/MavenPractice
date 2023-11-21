# 1. 生命週期

* **作用**：**提高構建過程的自動化程度**
* Maven **設定了三個生命週期**，生命週期中的**每一個環節**對應`建置過程中的一個操作`

## 1.1 三個生命週期

1. **Clean**：**清理**相關操作
    1. pre-clean
    2. clean
    3. post-clean
2. **Site**：**生成站點**相關
    1. pre-site
    2. site
    3. post-site
    4. deploy-site
3. **Default**：**主要構建**過程
    1. validate 檢查
    2. generate-sources：原始碼
    3. process-sources：複製並處理原始碼，至目標目錄，準備打包。
    4. generate-resources：資源文件
    5. process-resources：複製並處理資源文件，至目標目錄，準備打包。
    6. **compile**：**編譯**專案 `main 目錄`下的原始程式碼。
    7. process-classes
    8. generate-test-sources
    9. process-test-sources
    10. generate-test-resources
    11. process-test-resources 複製並處理資源文件，至目標測試目錄。
    12. test-compile 編譯測試原始碼。
    13. process-test-classes
    14. **test**：使用合適的單元測試框架來執行測試。 這些測試程式碼不會被打包或部署。
    15. prepare-package：準備打包
    16. **package**：接受編譯好的程式碼，**打包**成可發佈的格式，如JAR。
    17. pre-integration-test
    18. integration-test
    19. post-integration-test
    20. verify
    21. **install**：將**套件安裝至本地倉庫**，以讓它項目依賴。
    22. **deploy**：**將最終的套件複製到遠端的倉庫**，以讓它開發人員共用；或部署到伺服器上運行
        * **需使用插件**，例如：`cargo`

## 1.2 特點

* 前面三個生命週期**彼此是獨立的**。
* **在任何一個生命週期內部，執行任何一個具體環節的操作，都是從本週期最初的位置開始執行，直到指定的地方**。

# 2. 插件和目標

* Maven 的核心程式只是負責宏觀調度，不做具體工作
* **插件**：**具體工作**都是由 Maven `插件完成`的
    * 編譯是由 maven-compiler-plugin-3.1.jar 插件來執行的。
* **目標**：一個插件**可以對應多個目標**，而`每個目標`都和`生命週期中的某一個環節`**對應**。
    * 執行對應的生命週期

# 3. 倉庫

* **本地倉庫**：在目前電腦上，為電腦上所有 Maven 工程服務
* **遠端倉庫**：需要**聯網**
    * 區域網路：我們自己搭建的 Maven 私服，例如使用 Nexus 技術。
    * Internet
        * 中央倉庫
        * 鏡像倉庫：內容和中央倉庫保持一致，但是能夠分擔中央倉庫的負載，同時讓使用者能夠就近訪問提高下載速度
* **建議**：`不要中央倉庫和阿里雲鏡像混用，否則 jar 包來源不純，彼此衝突`。