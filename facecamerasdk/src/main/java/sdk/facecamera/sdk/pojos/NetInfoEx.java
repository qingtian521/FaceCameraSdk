package sdk.facecamera.sdk.pojos;

public class NetInfoEx {

    private String mac; //网卡MAC地址
    private String ip; //网卡IP地址
    private String netmask; //网上子网掩码
    private String gateway; // 网关
    private String manufacturer; // 制造商名称
    private String platform;    // 平台名称
    private String system;     // 系统名称
    private String version; // 版本
    private String ip_2;      // 网卡2IP地址
    private String netmask_2;  // 网卡2子网掩码
    private String dns;        // 域名服务器地址
    private boolean dhcp_enable;    // DHCP开关

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIp_2() {
        return ip_2;
    }

    public void setIp_2(String ip_2) {
        this.ip_2 = ip_2;
    }

    public String getNetmask_2() {
        return netmask_2;
    }

    public void setNetmask_2(String netmask_2) {
        this.netmask_2 = netmask_2;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public boolean isDhcp_enable() {
        return dhcp_enable;
    }

    public void setDhcp_enable(boolean dhcp_enable) {
        this.dhcp_enable = dhcp_enable;
    }
}
