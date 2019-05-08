package sdk.facecamera.demo.fragment;

import java.util.HashMap;

/**
 * Created by 云中双月 on 2018/3/30.
 */

public class FragmentFactory {
    private static HashMap<Integer,BaseFragment> mHashMap = new HashMap<Integer,BaseFragment>();
    public static BaseFragment createFragment(int pos){
        BaseFragment fragment = mHashMap.get(pos);
        if (fragment == null){
            switch (pos){
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new FaceCompareFragment();
                    break;
                case 2:
                    fragment = new CameraInfoFragment();
                    break;
                case 3:
                    fragment = new PeopleManagerFragment();
                    break;
            }
            mHashMap.put(pos,fragment);
        }

        return fragment;
    }
}
