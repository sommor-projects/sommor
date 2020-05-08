package com.sommor.core.component.status;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
public class StatusManager {

    private static Map<String, Map<Integer, StatusEnum>> statusMappedByName = new HashMap<>();

    public static void register(String statusName, StatusEnum... statusEnums) {
        Map<Integer, StatusEnum> statusEnumMap = new HashMap<>();
        for (StatusEnum statusEnum : statusEnums) {
            statusEnumMap.put(statusEnum.getCode(), statusEnum);
        }
        statusMappedByName.put(statusName, statusEnumMap);
    }

    public static StatusEnum statusOf(String statusName, Integer statusCode) {
        Map<Integer, StatusEnum> statusEnumMap = statusMappedByName.get(statusName);
        if (null != statusEnumMap) {
            return statusEnumMap.get(statusCode);
        }
        return null;
    }

}
