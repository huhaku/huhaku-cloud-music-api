![img](assets/v2-8197b602374a12fb6f7b86ff08f4ef37_720w.jpg)

![img](assets/v2-82e32347e81bd3363debd3c32f133cb9_720w.jpg)

嗯呐~



# 琥珀云音乐

### 介绍
琥珀云音乐后端工程

### 开发

#### 依赖
> java >= 1.8_211 mysql >= 5.7

#### 打包dist
> mvn clean package -Dmaven.test.skip=true
#### 启动

> java -Xms512M -Xmx512M -Xmn300M -jar music-0.0.1-SNAPSHOT.jar

### 部署步骤

1.建立music目录(0644),用来存放歌曲文件

2.建立log目录(0644),用来存放日志文件

目录结构

```
-music|
	  -default.jpg #默认封面
	  -musicCache #封面之类的内容
	  -xxxx| #一个目录作为一个歌单
		   -index.jpg #目录里面的index.jpg作为歌单封面
		   -xxxx.mp3 #只识别目录里面的mp3文件,注意大小写,文件名字为歌名
		   -xxxx.lrc #与歌曲同名lrc文件为歌词
```

3.导入sql目录中的sql文件

4.根据需要修改application.properties文件,打包,上传并运行jar文件

5.按目录上传歌曲文件

6.请求flashCache刷新歌曲信息

### 需要注意的部分

1.后端没有任何权限验证,如果不想url被滥用,请修改server.servlet.context-path为一串随机值,并注意不要泄漏

2.歌词抓取的api来自网易云NodeJS 版 API项目,请自行搭建 (https://neteasecloudmusicapi.vercel.app/#/)

