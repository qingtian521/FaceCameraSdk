package sdk.facecamera.sdk.pojos;

public class NetInfo {

    private String ipAddr;
    private String macAddr;
    private String gateway;
    private String netmask;

    public NetInfo(String ipAddr, String macAddr, String gateway, String netmask) {
        this.ipAddr = ipAddr;
        this.macAddr = macAddr;
        this.gateway = gateway;
        this.netmask = netmask;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

}
