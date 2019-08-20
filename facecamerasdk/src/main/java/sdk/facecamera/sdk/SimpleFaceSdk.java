package sdk.facecamera.sdk;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.IntBuffer;

import sdk.facecamera.sdk.pojos.DeviceModel;
import sdk.facecamera.sdk.pojos.DeviceSystemInfo;
import sdk.facecamera.sdk.pojos.NetInfoEx;
import sdk.facecamera.sdk.sdk.ComHaSdkLibrary;
import sdk.facecamera.sdk.sdk.SystemNetInfoEx;
import sdk.facecamera.sdk.sdk.SystemVersionInfo;
import sdk.facecamera.sdk.sdk.WifiSignal;
import sdk.facecamera.sdk.sdk.ipscan_t;

public class SimpleFaceSdk {

    private static final String TAG = "SimpleFaceSdk";

    static {
        try {
            System.loadLibrary("hasdk");
            System.loadLibrary("jnidispatch");
        } catch (Exception ex) {
            Log.e("none", "static initializer: ", ex);
        }
    }

    //相机句柄
    private ComHaSdkLibrary.HA_Cam mCamera;

    private static ConnectEvent connectEvent = null;

    public interface ConnectEvent{
        void onConnect(String ip,short port,int usrParam);
        void onDisConnect(String ip,short port,int usrParam);

    }

    public static void setConnectEvent(ConnectEvent connectEvent){
        SimpleFaceSdk.connectEvent = connectEvent;
    }

    /**
     * 全局方法，只需要注册一次
     */
    public static class HA_ConnectEventCb implements ComHaSdkLibrary.HA_ConnectEventCb_t {

        @Override
        public void apply(ComHaSdkLibrary.HA_Cam cam, String ip, short port, int event, int usrParam) {
            Log.d(TAG, "连接状态：ip = " + ip + "event" + event);
            if (connectEvent != null){
                if (event == 1) {
                    connectEvent.onConnect(ip,port,usrParam);
                } else if (event == 2) {
                    connectEvent.onDisConnect(ip, port, usrParam);
                }
            }
        }
    }

    private volatile static SimpleFaceSdk mSimpleFaceSdk;

    public static SimpleFaceSdk getInstance() {
        if (mSimpleFaceSdk == null) {
            synchronized (SimpleFaceSdk.class) {
                if (mSimpleFaceSdk == null) {
                    mSimpleFaceSdk = new SimpleFaceSdk();
                }
            }
        }
        return mSimpleFaceSdk;
    }

    public void init(){
        ComHaSdkLibrary.INSTANCE.HA_Init();
        ComHaSdkLibrary.INSTANCE.HA_SetNotifyConnected(1);
        ComHaSdkLibrary.INSTANCE.HA_InitFaceModel((String) null);
        ComHaSdkLibrary.INSTANCE.HA_RegConnectEventCb(new HA_ConnectEventCb(), 0);
    }

    /**
     * 初始化相机连接
     * @param ip 相机ip
     * @return 结果
     */
    public boolean connect(String ip){
        IntBuffer connectErrorNum = IntBuffer.allocate(1);
        mCamera = ComHaSdkLibrary.INSTANCE.HA_ConnectEx(ip, (short) 9527, null, null, connectErrorNum, 0, 1);
        int errorNum = connectErrorNum.get();
        if (mCamera == null || ComHaSdkLibrary.INSTANCE.HA_Connected(mCamera) != 1) {
            Log.e(TAG, "初始化失败，Initialize, faild: errorCode: " + errorNum);
            return false;
        }
        return true;
    }

    public void disConnect(){
        ComHaSdkLibrary.INSTANCE.HA_DisConnect(mCamera);
    }

    public void unInitialize() {
        if (mCamera != null) {
            ComHaSdkLibrary.INSTANCE.HA_ClearAllCallbacks(mCamera);
            ComHaSdkLibrary.INSTANCE.HA_DisConnect(mCamera);
        }
    }

    /**
     * 注册设备搜索回调函数
     */

    public static void setSearchListener(OnSearchListener listener) {
        ComHaSdkLibrary.INSTANCE.HA_RegDiscoverIpscanCb(new HA_SearchDeviceCb(), 0);
        searchListener = listener;
    }

    private static OnSearchListener searchListener = null;

    public interface OnSearchListener {
        void onSearchResult(DeviceModel model);
    }

    /**
     * 触发搜索
     */
    public static void searchDevice() {
        ComHaSdkLibrary.INSTANCE.HA_DiscoverIpscan();
    }

    //搜索结果回调在这里面
    private static class HA_SearchDeviceCb implements ComHaSdkLibrary.discover_ipscan_cb_t {

        @Override
        public void apply(ipscan_t ipscan, int usr_param) {
            DeviceModel model = new DeviceModel();
            try {
                model.setDeviceMac(new String(ipscan.mac, "UTF-8").trim());
                model.setDeviceIp(new String(ipscan.ip, "UTF-8").trim());
                model.setNetMask(new String(ipscan.netmask, "UTF-8").trim());
                model.setManuFacturer(new String(ipscan.manufacturer, "UTF-8").trim());
                model.setPlatform(new String(ipscan.platform, "UTF-8").trim());
                model.setSystem(new String(ipscan.system, "UTF-8").trim());
                model.setVersion(new String(ipscan.version, "UTF-8").trim());
                if (searchListener != null) {
                    searchListener.onSearchResult(model);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取相机的网络参数
     * @return NetInfo
     *     private String mac; //网卡MAC地址
     *     private String ip; //网卡IP地址
     *     private String netmask; //网上子网掩码
     *     private String gateway; // 网关
     *     private String manufacturer; // 制造商名称
     *     private String platform;    // 平台名称
     *     private String system;     // 系统名称
     *     private String version; // 版本
     *     private String ip_2;      // 网卡2IP地址
     *     private String netmask_2;  // 网卡2子网掩码
     *     private String dns;        // 域名服务器地址
     *     private boolean dhcp_enable;    // DHCP开关
     * @return NetInfoEx
     */

    public NetInfoEx getNetInfo(){
        SystemNetInfoEx netInfo = new SystemNetInfoEx();
        int ret = ComHaSdkLibrary.INSTANCE.HA_GetNetConfigEx(mCamera,netInfo);
        NetInfoEx netInfoEx = new NetInfoEx();
        try {
            netInfoEx.setMac(new String(netInfo.mac,"UTF-8").trim());
            netInfoEx.setIp(new String(netInfo.ip,"UTF-8").trim());
            netInfoEx.setNetmask(new String(netInfo.netmask,"UTF-8").trim());
            netInfoEx.setGateway(new String(netInfo.gateway,"UTF-8").trim());
            netInfoEx.setManufacturer(new String(netInfo.manufacturer,"UTF-8").trim());
            netInfoEx.setPlatform(new String(netInfo.platform,"UTF-8").trim());
            netInfoEx.setSystem(new String(netInfo.system,"UTF-8").trim());
            netInfoEx.setVersion(new String(netInfo.version,"UTF-8").trim());
            netInfoEx.setIp_2(new String(netInfo.ip_2,"UTF-8").trim());
            netInfoEx.setNetmask_2(new String(netInfo.netmask_2,"UTF-8").trim());
            netInfoEx.setDns(new String(netInfo.dns,"UTF-8").trim());
            netInfoEx.setDhcp_enable(netInfo.dhcp_enable == 1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("ret = " + ret);
        return netInfoEx;
    }

    /**
     * 设置相机的网络参数
     */

    public boolean setNetInfo(NetInfoEx info){
        SystemNetInfoEx netInfoEx = new SystemNetInfoEx();
        try {
            System.arraycopy(info.getMac().getBytes("UTF-8"),0, netInfoEx.mac,0,info.getMac().getBytes("UTF-8").length);
            System.arraycopy(info.getIp().getBytes("UTF-8"),0, netInfoEx.ip,0,info.getIp().getBytes("UTF-8").length);
            System.arraycopy(info.getNetmask().getBytes("UTF-8"),0, netInfoEx.netmask,0,info.getNetmask().getBytes("UTF-8").length);
            System.arraycopy(info.getGateway().getBytes("UTF-8"),0, netInfoEx.gateway,0,info.getGateway().getBytes("UTF-8").length);
            System.arraycopy(info.getManufacturer().getBytes("UTF-8"),0, netInfoEx.manufacturer,0,info.getManufacturer().getBytes("UTF-8").length);
            System.arraycopy(info.getPlatform().getBytes("UTF-8"),0, netInfoEx.platform,0,info.getPlatform().getBytes("UTF-8").length);
            System.arraycopy(info.getSystem().getBytes("UTF-8"),0, netInfoEx.system,0,info.getSystem().getBytes("UTF-8").length);
            System.arraycopy(info.getVersion().getBytes("UTF-8"),0, netInfoEx.version,0,info.getVersion().getBytes("UTF-8").length);
            System.arraycopy(info.getIp_2().getBytes("UTF-8"),0, netInfoEx.ip_2,0,info.getIp_2().getBytes("UTF-8").length);
            System.arraycopy(info.getNetmask_2().getBytes("UTF-8"),0, netInfoEx.netmask_2,0,info.getNetmask_2().getBytes("UTF-8").length);
            System.arraycopy(info.getDns().getBytes("UTF-8"),0, netInfoEx.dns,0,info.getDns().getBytes("UTF-8").length);
            netInfoEx.dhcp_enable = (byte) (info.isDhcp_enable() ? 1:0 );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int ret = ComHaSdkLibrary.INSTANCE.HA_SetNetConfigEx(mCamera,netInfoEx);
        System.out.println("setNetInfo ret = " + ret);
        return ret == 0;
    }


    /**
     *获取设备信息
     */
    public DeviceSystemInfo getDeviceInfo(){
        SystemVersionInfo info = new SystemVersionInfo();
        int ret = ComHaSdkLibrary.INSTANCE.HA_GetFaceSystemVersionEx(mCamera,info);
        System.out.println("getDeviceSystemInfo ret =" + ret);
        if(ret == 0){
            try {
                DeviceSystemInfo systemInfo = new DeviceSystemInfo();
                systemInfo.setDevId(new String(info.dev_id,"UTF-8").trim());
                systemInfo.setAlgorithmVer(new String(info.algorithm_ver,"UTF-8").trim());
                systemInfo.setBuildTime(new String(info.build_time,"UTF-8").trim());
                systemInfo.setCodeVer(new String(info.code_ver,"UTF-8").trim());
                systemInfo.setFirmwareVer(new String(info.firmware_ver,"UTF-8").trim());
                systemInfo.setPlateform(new String(info.plateform,"UTF-8").trim());
                systemInfo.setSensorType(new String(info.sensor_type,"UTF-8").trim());
                systemInfo.setMinSdkVer(new String(info.min_sdk_ver,"UTF-8").trim());
                systemInfo.setSystempType(new String(info.systemp_type,"UTF-8").trim());
                systemInfo.setMinClientver(info.min_client_ver);
                return systemInfo;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *获取设备信息
     */
    public String getDeviceId(){
        SystemVersionInfo info = new SystemVersionInfo();
        int ret = ComHaSdkLibrary.INSTANCE.HA_GetFaceSystemVersionEx(mCamera,info);
        System.out.println("getDeviceId ret =" + ret);
        if(ret == 0){
            try {
                return new String(info.dev_id,"UTF-8").trim();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 连接wifi
     * @param SSID
     * @param password
     * @return
     */
    public int connectWifi(String SSID,String password){
        return ComHaSdkLibrary.INSTANCE.HA_ConnectWifi(mCamera, (byte) 1,SSID,password,(byte)1);
    }

    /**
     * 获取wifi信息
     */
    public void getWifiInfo(){
        WifiSignal wifiSignal = new WifiSignal();
        int ret = ComHaSdkLibrary.INSTANCE.HA_WifiInfor(mCamera,wifiSignal);
        if (ret == 0){
            try {
                String ssid = new String(wifiSignal.ssid,"UTF-8").trim();
                System.out.println("LogUtils getWifiInfo ssid  = " + ssid);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        System.out.println("LogUtils getWifiInfo  ret = " + ret);
    }

    /**
     * 当确定不在使用相机时再调用，在程序退出时
     */
    public static void deInit(){
        ComHaSdkLibrary.INSTANCE.HA_DeInit();
    }
}
