package sdk.facecamera.demo.crash;

/**
 * Created by lingxiao on 2017/12/26.
 */

public class ContentValue {
    /**
     *保存错误信息
     */
    public static String ERRORINFO = "error_info";
    public static String ERRORSTR = "error_string";

    /**
     *viewpager单页显示个数
     */
    public static int  ONE_PAGE_ITEM_COUNT = 3;

    public static final long TIME_DEFAULT_POOR = 60*1000; //默认等待60秒隐藏
    /**
     *在这个时间段内不做操作
     */
    public static final String TIME_START = "08:00:00"; //开始时间段
    public static final String TIME_END = "13:03:00";   //结束时间段

    /**
     * viewpager的状态
     */
    public static final int CARD_STATE_VISIBLE = 0;
    public static final int CARD_STATE_INVISIBLE = 1;

    /**
     * 相机ip
     */
    public static final String CAMERA_IP = "camera_ip";
}
