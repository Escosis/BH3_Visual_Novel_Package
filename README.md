**本应用用于 Android 用户在本地阅读崩三的三部视觉小说，Windows 用户请移步 [BH3-Visual-Novels](https://github.com/Escosis/BH3-Visual-Novels) 仓库。二者均可以直接访问 [BH3-Visual-Novels](https://escosis.github.io/BH3-Visual-Novels/) 进行预览。）**

## 改动：
1. 重定向所有资源路径至本地，移除网络请求
2. 在 game.js 和 gameDurandal.js 末尾添加代码，使得存档使用 file 协议能利用的 localStorage 存储，并且将成就请求重定向至本地，默认全成就（神州折剑录本来用的就是 localStorage 所以不用改）
3. 修复 gameDurandal.js 一处错误（ tryAudio 误写为 tryAudioPlay ）以及`视觉小说目录/index.htm`中一处警告（width=750px 中 px 是多余的）
4. 神州折剑录文件较混乱，进行微调，设置 achievements 文件夹，其余资源都放在 contentweb 文件夹
