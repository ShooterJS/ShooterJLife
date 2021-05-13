package com.shooterj.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;

public class MapUtil {

    public static <T> void delByStartKey(Map<String,T> map, String key){
        if(key == null){
            map.remove(null);
            return ;
        }
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String k = iterator.next();
            // 如果k刚好在要排除的key的范围中
            if (StringUtils.isNotEmpty(k) && k.startsWith(key) ) {
                iterator.remove();
                map.remove(k);
            }
        }

    }
}
