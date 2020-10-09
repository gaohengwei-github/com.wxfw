package com.wxfw.util;

import java.util.*;

/**
 * CollectionUtils
 *
 * @author gaohw
 * @date 2020/3/19
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static <T> List<T> setToList(Set<T> set) {
        return new ArrayList<T>(set);
    }

    public static String join(List<String> list, String flag) {
        if (isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(flag);
            }
        }
        return sb.toString();
    }
}
