package demo.mongo.utils;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;

/**
 * 车牌号提取工具类
 */
public class VehicleIdUtil {

    public static final String car[] = {"A","B","C","D","E","F","G","H","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    /**
     *  根据车牌号首字母提取首字母
     * @param vehicleId
     * @return
     */
    public static int getVehType(String vehicleId){
        String sub = StrUtil.sub(vehicleId, 1, 2);
        int indexNum = Arrays.binarySearch(car, sub.toUpperCase());

        if(0<=indexNum && 2>indexNum){
            return 1;
        }else if(2<=indexNum && 4>indexNum){
            return 2;
        }else if(4<=indexNum && 6>indexNum){
            return 3;
        }else if(6<=indexNum && 10>indexNum){
            return 4;
        }else if(10<=indexNum && 14>indexNum){
            return 5;
        }else if(14<=indexNum && 19>indexNum){
            return 6;
        }else if(19<=indexNum && 24>=indexNum){
            return 7;
        }else {
            return 0;
        }

    }

}
