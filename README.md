# NetWindow
 
 这是一个展示网络请求数据的库，提供了一个默认的Okhttp的Interceptor拦截器使用，或者用户自定义自己的请求数据
 ## 效果
 <img src="https://github.com/fafawee/NetWindow/blob/master/pic/Screenshot_1578446922.png?raw=true" alt="Sample"  width="360" height="640" >
 <img src="https://github.com/fafawee/NetWindow/blob/master/pic/Screenshot_1578446922.png?raw=true" width = "400" height = "260" alt="图片名称" 
 align=center>
## 如何使用
#### 1、初始化NetWindow
在Application初始化NetWindow， NetWindow.init(this);
```
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        NetWindow.init(this);
        init(this);
    }

```
#### 2、配置NetWindowConfig
如果用NetWindow.init(this) 这个初始化，框架会默认使用默认的NetWindowConfig配置,
如果用户需要自定义，
```
    NetWindowConfig的属性如下（就上NET那个点击图标）
    //在屏幕上的位置
    private Point positionOnScreen;
    //logo的文本内容
    private String netLogoText;
    //logo本地资源图片
    private int netLogoImage;
    //logo的尺寸大小
    private FSize size;
    //这个要用户设置 从url获取接口名称的规则
    private InterfaceName interfaceName;

NetWindowConfig config=new NetWindowConfig();
config.setNetLogoImage(-1)
    .setNetLogoText("Net")
    .setPositionOnScreen(new Point((int)(NetWindowUtils.getScreenWidth()*0.05),(int)(NetWindowUtils.getScreenHeight()*0.8)))
    .setSize(new FSize(NetWindowUtils.dp2px(40),NetWindowUtils.dp2px(40)))
    .setInterfaceName(NetWindowConfig.defaultInterface);
 NetWindow.init(this,config);
```
setInterfaceName:这个页面上展示接口名称的配置接口，
(使用NetInterpoter 才需要配置)
(使用NetInterpoter 才需要配置)
(使用NetInterpoter 才需要配置)
```
 public static InterfaceName defaultInterface=new InterfaceName() {
        @Override
        public String getShowInterfaceName(String url) {
            return url;

        }
    };
```
用户设置如何从url 转出自己的接口显示的名称,如下：
```
NetWindow.init(this);
NetWindow.instance.getConfig().setInterfaceName(new InterfaceName() {
    @Override
    public String getShowInterfaceName(String url) {
        return null;
    }
});
```
#### 3. 如何发送请求消息
1. 使用NetInterpoter，设置到Okhttp的拦截器中就可以了（推荐使用，现在还有哪个同学不是用的Okhttp啊？）
```
  OkHttpClient.Builder builder = new OkHttpClient.Builder();
  builder.addInterceptor(new NetInterpoter());
```
2. 用户自定义发送请求消息

```
        //发送请求消息
        long start=System.currentTimeMillis();
        RequestMessage requestMessage=new RequestMessage();
        //接口名称，展示作用，自定义
        requestMessage.setInterfacename("api/weixin/deletecar/");
        requestMessage.setRequest("请求信息，用户自己定义,例如包含了url,参数、header");
        //这个是请求id，到时候需要和发送返回消息的id要一致，因为要配对，用户自己逻辑定义
        requestMessage.setId(222111);
        NetMessagePoster.defaultPoster.postRequest(requestMessage);
        /*
        *
        * *
        * *
        * 经过了一段时间的访问，马上有结果了...........loading
        * *
        * *
        * *
        * *
        *
         */
        ResponseMessage responseMessage=new ResponseMessage();
        responseMessage.setInterfacename("api/weixin/deletecar/");
        responseMessage.setResult("返回信息，用户自己定义,例如包含了body");
        responseMessage.setId(222111);
        long duration=System.currentTimeMillis()-start;
        responseMessage.setTime(duration+"");
        NetMessagePoster.defaultPoster.postResponse(responseMessage);
```

上面的代码展示了发送的流程，要点：
1. RequestMessage，ResponseMessage同一个请求和应答id必须一致
2. setInterfacename 最好也要一致
3. setTime 是响应时间，用户自己算
4. setResult，一般是json，本库已经提供了json格式化方法，可参考
NetWindowUtils.fromJson(String)
## 依赖方式
```
implementation 'com.github.fafawee:NetWindow:1.0.4'
 
```
需要jitpack仓库的支持
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
## 联系方式
1. qq:597536434
2. github:https://github.com/fafawee/

