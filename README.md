# WechatAimeBot
获取"舞萌|中二节奏"公众号玩家二维码并在QQ中发送，基于MiraiCore与uiautomation，代码参考WxAuto

## 使用方法
配置Main.java中的地址与Bot QQ号

微信打开舞萌|中二公众号并保持前台运行(建议在VPS/云服务器/挂机宝中运行)

理论上头文字D等WechatAime项目均可运行(未经测试)

## 新版微信Chrome内核内置浏览器造成捕获窗口失败的解决方案
### 方案1(推荐)
使用UISpy工具抓取新窗口的句柄
### 方案2
备份后删除
C:\Users\[用户名]\AppData\Roaming\Tencent\WeChat\XPlugin\Plugins\RadiumWMPF\6939\extracted\runtime\
中的WeChatAppEx.exe，迫使微信使用旧内置浏览器

(微信版本不同，路径可能有差异，可在任务管理器中右键浏览器进程，打开文件位置)