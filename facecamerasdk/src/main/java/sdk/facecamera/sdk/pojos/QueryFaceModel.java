package sdk.facecamera.sdk.pojos;

import java.util.Arrays;

public class QueryFaceModel {
    private String id;
    private String name;
    private int role;
    private int wiegandNo;
    private long expireDate; // 0表示未启用 0xFFFFFFFF表示永不过期
    private short featureCount;
    private short featureSize;
    private long effectTime;
    private long effectStartTime;
    private byte[][] imageData;
    private byte[][] twisBgrs;
    private float[] feature;
    public float[] getFeature() {
        return feature;
    }
    public void setFeature(float[] feature) {
        this.feature = feature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getWiegandNo() {
        return wiegandNo;
    }

    public void setWiegandNo(int wiegandNo) {
        this.wiegandNo = wiegandNo;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public short getFeatureCount() {
        return featureCount;
    }

    public void setFeatureCount(short featureCount) {
        this.featureCount = featureCount;
    }

    public short getFeatureSize() {
        return featureSize;
    }

    public void setFeatureSize(short featureSize) {
        this.featureSize = featureSize;
    }

    public byte[][] getImageData() {
        return imageData;
    }

    public void setImageData(byte[][] imageData) {
        this.imageData = imageData;
    }

    public byte[][] getTwisBgrs() {
        return twisBgrs;
    }

    public void setTwisBgrs(byte[][] twisBgrs) {
        this.twisBgrs = twisBgrs;
    }

    public long getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(long effectTime) {
        this.effectTime = effectTime;
    }

    public long getEffectStartTime() {
        return effectStartTime;
    }

    public void setEffectStartTime(long effectStartTime) {
        this.effectStartTime = effectStartTime;
    }

    @Override
    public String toString() {
        return "QueryFaceModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", wiegandNo=" + wiegandNo +
                ", expireDate=" + expireDate +
                ", featureCount=" + featureCount +
                ", featureSize=" + featureSize +
                ", effectTime=" + effectTime +
                ", effectStartTime=" + effectStartTime +
                ", imageData=" + Arrays.toString(imageData) +
                ", twisBgrs=" + Arrays.toString(twisBgrs) +
                ", feature=" + Arrays.toString(feature) +
                '}';
    }
}
