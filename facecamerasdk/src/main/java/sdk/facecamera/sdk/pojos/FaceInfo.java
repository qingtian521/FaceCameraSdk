package sdk.facecamera.sdk.pojos;

import android.graphics.Rect;

import java.util.Date;

/**
 * 人脸模板数据
 */
public final class FaceInfo {
    private String id;
    private String name;
    private int role;
    private long wiegandNo;
    private long expireDate; // 0表示未启用 0xFFFFFFFF表示永不过期
    private short featureCount;
    private short featureSize;
    private byte[][] imageData;
    private byte[][] twisBgrs;


    private long sequenceID;
    private String cameraID;
    private String addrID;
    private String addrName;
    private Date captureTime;
    private boolean isRealtime;
    private boolean isPersonMatched;
    private int matchScore;
    private int[] faceRegionInEnvironment = new int[4];
    private Rect faceRegionInFeature;
    private byte[] videoData;
    private Date videoStartTime;
    private Date videoEndTime;
    private byte sex;
    private byte age;
    private byte standardNum; //标准度
    private float[] featureData;
    private byte[] environmentImageData; //全景图
    private byte[] modelImageData; //模板图
    private byte[] captureImageData;  //抓拍图
    private int[] featureRectInBg = new int[4]; //rect


    /** 获取数据包序列号 */
    public long getSequenceID() {
        return sequenceID;
    }

    /** 设置数据包序列号 */
    public void setSequenceID(long sequenceID) {
        this.sequenceID = sequenceID;
    }

    /** 获取设备编号 */
    public String getCameraID() {
        return cameraID;
    }

    /** 设置设备编号 */
    public void setCameraID(String cameraID) {
        this.cameraID = cameraID;
    }

    /** 获取地点编号 */
    public String getAddrID() {
        return addrID;
    }

    /** 设置地点编号 */
    public void setAddrID(String addrID) {
        this.addrID = addrID;
    }

    /** 获取地点名称 */
    public String getAddrName() {
        return addrName;
    }

    /** 设置地点名称 */
    public void setAddrName(String addrName) {
        this.addrName = addrName;
    }

    /** 获取抓拍时间 */
    public Date getCaptureTime() {
        return captureTime;
    }

    /** 设置抓拍时间 */
    public void setCaptureTime(Date captureTime) {
        this.captureTime = captureTime;
    }

    /** 是否实时抓拍数据 */
    public boolean isRealtime() {
        return isRealtime;
    }

    /** 设置实时抓拍数据 */
    public void setRealtime(boolean isRealtime) {
        this.isRealtime = isRealtime;
    }

    /** 是否匹配的设备库中的人员 */
    public boolean isPersonMatched() {
        return isPersonMatched;
    }

    /** 设置是否匹配成功 */
    public void setPersonMatched(boolean isPersonMatched) {
        this.isPersonMatched = isPersonMatched;
    }

    /** 获取匹配度1~100 */
    public int getMatchScore() {
        return matchScore;
    }

    /** 设置匹配度 */
    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    /** 获取环境图（大图）图片；可能为null表示前端未回传大图 */
    public byte[] getEnvironmentImageData() {
        return environmentImageData;
    }

    /** 设置环境图（大图）图片 */
    public void setEnvironmentImageData(byte[] environmentImageData) {
        this.environmentImageData = environmentImageData;
    }

    /** 获取人脸在环境图（大图）中的坐标 */
    public int[] getFaceRegionInEnvironment() {
        return faceRegionInEnvironment;
    }

    /** 设置人脸在环境图（大图）中的坐标 */
    public void setFaceRegionInEnvironment(int x, int y, int w, int h) {
        this.faceRegionInEnvironment[0] = x;
        this.faceRegionInEnvironment[1] = y;
        this.faceRegionInEnvironment[2] = w;
        this.faceRegionInEnvironment[3] = h;
    }

    /** 获取特写图数据；可能为null表示未回传特写图 */
    public byte[] getCaptureImageData() {
        return captureImageData;
    }

    /** 设置特写图数据 */
    public void setCaptureImageData(byte[] featureImageData) {
        this.captureImageData = featureImageData;
    }

    /** 获取人脸在特写图中的区域 */
    public Rect getFaceRegionInFeature() {
        return faceRegionInFeature;
    }

    /** 设置人脸在特写图中的区域 */
    public void setFaceRegionInFeature(Rect faceRegionInFeature) {
        this.faceRegionInFeature = faceRegionInFeature;
    }

    /** 获取视频数据 */
    public byte[] getVideoData() {
        return videoData;
    }

    /** 设置视频数据 */
    public void setVideoData(byte[] videoData) {
        this.videoData = videoData;
    }

    /** 获取视频开始时间 */
    public Date getVideoStartTime() {
        return videoStartTime;
    }

    /** 设置视频开始时间 */
    public void setVideoStartTime(Date videoStartTime) {
        this.videoStartTime = videoStartTime;
    }

    /** 获取视频结束时间 */
    public Date getVideoEndTime() {
        return videoEndTime;
    }

    /** 设置视频结束时间 */
    public void setVideoEndTime(Date videoEndTime) {
        this.videoEndTime = videoEndTime;
    }

    /**
     * 获取性别
     *
     * @return 0: 无此信息 1：男 2：女
     */
    public byte getSex() {
        return sex;
    }
    /**
     * 设置性别
     *
     * @param sex 0: 无此信息 1：男 2：女
     */
    public void setSex(byte sex) {
        this.sex = sex;
    }
    /**
     * 获取年龄
     *
     * @return 0: 无此信息 其它值：年龄
     */
    public byte getAge() {
        return age;
    }
    /**
     * 设置年龄
     *
     * @param age 0: 无此信息 其它值：年龄
     */
    public void setAge(byte age) {
        this.age = age;
    }

    /** 获取（抓拍到）人脸特征值数据 */
    /*public float[] getFeatureData() {
        return featureData;
    }

    *//** 设置（抓拍到）人脸特征值数据 *//*
    public void setFeatureData(float[] featureData) {
        this.featureData = featureData;
    }*/

    /** 获取匹配到的人脸模板图 */
    public byte[] getModelImageData() {
        return modelImageData;
    }

    /** 设置匹配到的人脸模板图 */
    public void setModelImageData(byte[] modelImageData) {
        this.modelImageData = modelImageData;
    }

    /**
     * 获取q值
     * @return
     */
    public byte getStandardNum() {
        return standardNum;
    }

    /**
     * 设置q值
     * @param standardNum
     */
    public void setStandardNum(byte standardNum) {
        this.standardNum = standardNum;
    }

    public int[] getFeatureRectInBg() {
        return featureRectInBg;
    }

    public void setFeatureRectInBg(int x, int y, int w, int h) {
        this.featureRectInBg[0] = x;
        this.featureRectInBg[1] = y;
        this.featureRectInBg[2] = w;
        this.featureRectInBg[3] = h;
    }

    /**
     * 获取人员编号
     *
     * @return 编号
     */
    public String getId() {
        return id;
    }
    /**
     * 设置人员编号
     *
     * @param id 编号
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 获取人员姓名
     *
     * @return 姓名
     */
    public String getName() {
        return name;
    }
    /**
     * 设置人员姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 获取人员角色
     *
     * @return 角色
     */
    public int getRole() {
        return role;
    }
    /**
     * 设置人员角色
     *
     * @param role 角色
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * 获取人员韦根卡号
     *
     * @return 韦根卡号，其实类型是unsigned int
     */
    public long getWiegandNo() {
        return wiegandNo;
    }
    /**
     * 设置人员韦根卡号
     *
     * @param wiegandNo 韦根卡号，其实类型是unsigned int
     */
    public void setWiegandNo(long wiegandNo) {
        this.wiegandNo = wiegandNo;
    }

    /**
     * 获取过期(截止)时间
     *
     * @return 过期时间
     */
    public long getExpireDate() {
        return expireDate;
    }
    /**
     * 设置过期(截止)时间
     *
     * @param expireDate 过期时间
     */
    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }
    /**
     * 获取特征值数量
     *
     * @return 特征值数量
     */
    public short getFeatureCount() {
        return featureCount;
    }
    /**
     * 设置特征值数量
     *
     * @param featureCount 特征值数量
     */
    public void setFeatureCount(short featureCount) {
        this.featureCount = featureCount;
    }
    /**
     * 获取每组特征值元素数量
     *
     * @return 特征值组内元素个数
     */
    public short getFeatureSize() {
        return featureSize;
    }
    /**
     * 设置每组特征值元素数量
     *
     * @param featureSize 特征值组内元素个数
     */
    public void setFeatureSize(short featureSize) {
        this.featureSize = featureSize;
    }
    /**
     * 获取特征值数据
     *
     * @return 特征值数据
     */
    public float[] getFeatureData() {
        return featureData;
    }
    /**
     * 设置特征值数据
     *
     * @param featureData 特征值数据
     */
    public void setFeatureData(float[] featureData) {
        this.featureData = featureData;
    }
    /**
     * 获取图像数据
     *
     * @return 图像数据
     */
    public byte[][] getImageData() {
        return imageData;
    }
    /**
     * 设置图像数据
     *
     * @param imageData 图像数据
     */
    public void setImageData(byte[][] imageData) {
        this.imageData = imageData;
    }
    /**
     * 获取人脸归一化图像数据
     *
     * @return 归一化图像数据
     */
    public byte[][] getTwisBgrs() { return twisBgrs; }
    /**
     * 设置人脸归一化图像数据
     *
     * @param twisBgrs 归一化图像数据
     */
    public void setTwisBgrs(byte[][] twisBgrs) { this.twisBgrs = twisBgrs; }
}
