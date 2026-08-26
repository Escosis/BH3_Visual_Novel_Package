**本应用用于安卓用户在本地阅读崩三的三部视觉小说，Windows端用户可下载 https://github.com/Escosis/BH3-Visual-Novels 仓库的所有文件并以以下方法打开进行阅读：（也可以直接访问 https://escosis.github.io/BH3-Visual-Novels/ 进行预览。）**

## 方法一：在电脑浏览器中打开htm/html直接阅读，但需要禁用浏览器的CORS策略才可正常使用，否则将由于该策略限制无法访问资源文件。

**禁用方法一：**  
在浏览器地址栏输入chrome://flags/或edge://flags/，搜索CORS找到Block insecure private network requests.这个选项，改为Disabled，重启浏览器。

**禁用方法二：**  
创建一个新目录作为数据目录，在浏览器的某个快捷方式属性栏的目标后添加 --disable-web-security --user-data-dir=新数据目录路径，使用以此快捷方式打开的浏览器进行阅读。  
例如，浏览器快捷方式的目标原本是：  
"C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe"  
创建C:\EdgeDevData作为数据目录，将此栏改为：  
"C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe" --disable-web-security --user-data-dir=C:\EdgeDevData  
（注意双引号后有一个空格）

**禁用方法三：**  
新建一个bat文件，内容如下：（也可根据情况修改）
```batch
@echo off
start "" "C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe" --disable-web-security --user-data-dir="C:\EdgeDevData" --allow-file-access-from-files "%~1"
```
然后可使用BatToExeConverter转为exe并设置为htm/html的打开方式，之后直接点开即可

## 方法二：启动一个本地服务器，从中访问htm/html

## 改动：
1. 重定向所有资源路径至本地，移除网络请求
2. 在game.js和gameDurandal.js末尾添加代码，使得存档使用file协议能利用的localStorage存储，并且将成就请求重定向至本地，默认全成就（神州折剑录本来用的就是localStorage所以不用改）
3. 修复gameDurandal.js一处错误（tryAudio误写为tryAudioPlay）以及视觉小说目录/index.htm中一处警告（width=750px中px是多余的）
4. 神州折剑录文件较混乱，进行微调，设置achievements文件夹，其余资源都放在contentweb文件夹
