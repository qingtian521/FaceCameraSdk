package sdk.facecamera.demo.model;

/**
 * Created by 云中双月 on 2018/3/30.
 */

public class PersonModel {
    public String name;
    private int faceAngle;
    private int faceAngleFlat;
    private byte age;
    private byte sex;
    private byte[] img;
    private boolean result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFaceAngle() {
        return faceAngle;
    }

    public void setFaceAngle(int faceAngle) {
        this.faceAngle = faceAngle;
    }

    public int getFaceAngleFlat() {
        return faceAngleFlat;
    }

    public void setFaceAngleFlat(int faceAngleFlat) {
        this.faceAngleFlat = faceAngleFlat;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
