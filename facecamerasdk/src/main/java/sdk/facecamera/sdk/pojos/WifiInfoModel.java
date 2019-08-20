package sdk.facecamera.sdk.pojos;

public class WifiInfoModel {

    private String SSID; //wifi名称
    private int frequency;// 频率 2.4G或5G
    private int rssi;// 信号强度 信号强度在[-126, 0]范围递增，频率为5G时[156, 200)递增
    private String mac;// MAC地址
    private String encryptMethod;// 加密方式
    private int speed;// 连接速度

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getEncryptMethod() {
        return encryptMethod;
    }

    public void setEncryptMethod(String encryptMethod) {
        this.encryptMethod = encryptMethod;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
