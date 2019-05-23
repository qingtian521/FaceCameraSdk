# 人脸识别相机SDK

## 1.导入SDK
本文基于AndroidStudio 3.2进行介绍。

### 1 将其添加到存储库末尾的根build.gradle中
~~~
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
~~~
### 2 添加依赖项
~~~
dependencies {
	        implementation 'com.github.renlei521:FaceCameraSdk:1.1.3'
	}
~~~
## 2.接口即调用过程

### 获取FaceSdk实例
~~~
FaceSdk faceSdk = FaceSdk.getInstance();

FaceSdk.getInstance(); 每次只使用一个实例。

FaceSdk faceSdk = new FaceSdk();
new FaceSdk();可创建多个对象
~~~
### 初始化
~~~
boolean ret=faceSdk.Initialize(getApplicationContext(),ipStr);
~~~

传入参数为Context，可以是activity，this之类的；ipStr为需要连接的相机ip地
址。

返回参数ret为boolean类型，ture表示初始化成功,false表示初始化失败。

注：初始化函数只需要调用一次即可，不可重复调用。


### 获取连接状态
~~~
faceSdk.setConnectCallBack(new FaceSdk.ConnectCallBack() {
 @Override
 public void onConnected(String ip, short port, int usrParam) {
}
 @Override
public void onDisConnected(String ip,short port,int usrParam){
}
});
~~~
onConnected表示连接成功，onDisConnected表示连接失败。

### 播放视频
~~~
faceSdk.startVideoPlay(surfaceHolder);
~~~

传入显示SurfaceView所持有的surfaceHolder。

### 抓拍数据回调
~~~
faceSdk.setFaceInfoCallBack(new FaceSdk.FaceInfoCallBack() {
 @Override
 public void onFaceInfoResult(boolean inside, FaceInfo info, String errMsg) {     
    }
});
~~~
实体类FaceInfo即为抓拍人员信息。

### 检测人脸
~~~
faceSdk.haveFace(thumb, new FaceSdk.HaveFaceCallBack() {
  @Override
  public void onFaceSuccess(byte[] faceImg, byte[] twistImg) {
  }
  @Override
  public void onFaceFaild(int errorcode) {
  }
});
~~~
thumb表示需要检测的图片数据

OnFaceSuccess 即检测到人脸，Faild即未检测到人脸。
twistImg为归一图，需要在注册时传入。

### 人员注册
~~~
int ret = faceSdk.addPersonPacket(id,name,role,wiegandNo,faceImg,twistImg);
~~~

参数说明：

id : 人员id，大小20字节，不可重复。

name : 人员姓名，大小16字节。

role : 人员角色，‘0’普通人员、‘1’白名单、‘2’黑名单。

wiegandNo : 门禁卡号。

faceImg : 人脸原图。

twistImg : 检测完成后的归一化图。

注：人脸注册之前需要先检测人脸是否存在，检测完成之后再进行注册。这样可以保证注册的人脸照片是有效的。同时传入归一化图片减少注册时对图片的处理时间。

~~~
int ret = faceSdk.addPerson(idStr,nameStr,typeNum,wiegandNo,thumb,defaultTime);
~~~

参数同上，该方法直接注册，不用先检测图片，会在内部进行检测，相比上面的方法注册时间会增加一些。

### 通过ID查询人员

~~~
faceSdk.queryFaceById(mId);
~~~

mId ：待查询的人员ID。

注：此方法为查询触发事件，数据需通过后面的回调方法传回。

### 通过ID查询事件回调
~~~
faceSdk.setQueryCallBack( newFaceSdk. QueryCallBack() {
 @Override
 public void onQueryCallBack(boolean success, 
	QueryFaceModel model) {
    	}
});
~~~
success : ture表示查询成功。

model : 查询结果数据。

### 通过页面查询人员
~~~
faceSdk.getPersonList(pageNo, role, pageSize);
~~~
pageNo : 从第几页开始。

role : 加载人员类型，‘0’普通人员、‘1’白名单、‘2’黑名单。

pageSize : 每次加载多少个。

注：此方法为查询触发事件，数据需通过后面的回调方法传回。

### 通过页面查询事件回调
~~~
faceSdk.setQueryPageCallBack(new FaceSdk.QueryPageCallBack() {
    @Override
    public void onQueryPageCallBack(boolean success, List<QueryFaceModel> modelList) {
    }
});
~~~
success : ture表示查询成功。

modelList : 查询结果数据集合。

### 修改人员信息
~~~
boolean ret = faceSdk.modifyFaceInfoById(model);
~~~
Model : 为需要修改的实体类。

注：ID不可修改。
### 检测控制
~~~
faceSdk.setMatchEnable(matchSwitch)
~~~
可以通过 setMatchEnable控制检测开关，ture为开，反之关。
~~~
faceSdk.getMatchEnable()
~~~
通过getMatchEnable获取当前检测状态，ture为开，反之关。

### 手动开闸
~~~
faceSdk.openCp()
~~~
当相机和闸机连接时，可以通过此方法手动打开闸机

返回值为boolean ，‘ture’打开成功，‘false’ 打开失败。

### 注销
~~~
faceSdk.UnInitialize();
~~~
使用完之后记得注销
## 1.1.3 新增功能
### 活体检测开关
~~~
//获取活体检测开关状态
getAliveDetectEnable
//设置活体检测开关状态
setAliveDetectEnable
~~~
### 对比确定分数
~~~
getMatchScore   // 获取人脸比对确性分数，-1表示获取失败
setMatchScore  //  设置人脸比对确信分数（0-100分）
~~~
### 输出图像的品质
~~~
getOutputImageQuality // 查看输出图像的品质 jpg图像品质[1~100]
setOutputImageQuality  // 设置输出图像的品质 value jpg图像品质[1~100]
~~~
### 去重复开关
~~~
getRepeatConfig // 获取去重复开关 -1 为关 ， 大于0 为开，并且该值就是重复超时
setRepeatConfig // 设置深度去重复开关 enable 开关 timeout 超时时间(1s~60s)
~~~
### 大角度人脸过滤开关
~~~
getFaceAngleEnable // 获取大角度人脸过滤开关  int  -1 为关，> 0为开，并且值为角度
setFaceAngleEnable  // 设置大角度人脸角度开关 enable 开关 true开 angle 角度值
~~~
### 用户校验码
~~~
writeCustomerAuthCode // 注入用户校验码 auth 校验码数据
readCustomerAuthCode // 读取用户校验码 String 校验码
~~~
