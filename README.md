# baidu-voice-tuling

一个语音助手的DEMO项目

用到了百度的语音识别和语音合成，还有图灵机器人

木前能实现一些简单的对话，还有就是图灵机器人自带的一些什么天气查询，快递查询之类的

没有多少实际功能

定位也是直接把地址固定的，以后有空加进去

代码也很乱，就做的一个demo，没去注意什么代码怎么样，能达到目的就行

IDE环境是Android Studio

在运行项目前，请修改AndroidManifest.xml 和 TulingRequest.java

``` xml
<meta-data
    android:name="com.baidu.speech.APP_ID"
    android:value="<百度APP_ID>" />
<meta-data
    android:name="com.baidu.speech.API_KEY"
    android:value="<百度API_KEY>" />
<meta-data
    android:name="com.baidu.speech.SECRET_KEY"
    android:value="<百度SECRET_KEY>" />
```

``` java
params.put("key","<图铃机器人key>");
```
