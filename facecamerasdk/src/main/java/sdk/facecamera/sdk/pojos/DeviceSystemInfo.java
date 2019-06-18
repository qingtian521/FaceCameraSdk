package sdk.facecamera.sdk.pojos;

public class DeviceSystemInfo {

    /**
     * char dev_id[32];        //设备序列号
     *     char protocol_ver[8];   //协议版本
     *     char firmware_ver[16];  //固件版本
     *     char code_ver[8];       //应用程序版本
     *     char build_time[20];    //应用编译时间
     *     char resv[16];          //保留
     *     char systemp_type[16];  //系统类型
     *     char plateform[16];     //硬件平台
     *     char sensor_type[16];   //传感器型号
     *     char algorithm_ver[16]; //算法版本
     *     char min_sdk_ver[16];   //最低sdk版本
     *     unsigned int min_client_ver;//最低客户端版本
     * 	char kernel_version[128];  //内核版本
     * 	char lcd_type[32]; //LCD屏型号
     * 	char recv[512]; //保留
     */

    private String devId;
    private String protocolVer;
    private String firmwareVer;
    private String codeVer;
    private String buildTime;
    private String systempType;
    private String plateform;
    private String sensorType;
    private String algorithmVer;
    private String minSdkVer;
    private int minClientver;
    private String kernelVersion;
    private String lcdType;

    public String getKernelVersion() {
        return kernelVersion;
    }

    public void setKernelVersion(String kernelVersion) {
        this.kernelVersion = kernelVersion;
    }

    public String getLcdType() {
        return lcdType;
    }

    public void setLcdType(String lcdType) {
        this.lcdType = lcdType;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getProtocolVer() {
        return protocolVer;
    }

    public void setProtocolVer(String protocolVer) {
        this.protocolVer = protocolVer;
    }

    public String getFirmwareVer() {
        return firmwareVer;
    }

    public void setFirmwareVer(String firmwareVer) {
        this.firmwareVer = firmwareVer;
    }

    public String getCodeVer() {
        return codeVer;
    }

    public void setCodeVer(String codeVer) {
        this.codeVer = codeVer;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getSystempType() {
        return systempType;
    }

    public void setSystempType(String systempType) {
        this.systempType = systempType;
    }

    public String getPlateform() {
        return plateform;
    }

    public void setPlateform(String plateform) {
        this.plateform = plateform;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getAlgorithmVer() {
        return algorithmVer;
    }

    public void setAlgorithmVer(String algorithmVer) {
        this.algorithmVer = algorithmVer;
    }

    public String getMinSdkVer() {
        return minSdkVer;
    }

    public void setMinSdkVer(String minSdkVer) {
        this.minSdkVer = minSdkVer;
    }

    public int getMinClientver() {
        return minClientver;
    }

    public void setMinClientver(int minClientver) {
        this.minClientver = minClientver;
    }


}
