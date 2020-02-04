package com.sommor.mybatis.sql.field.type;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/2/13
 */
public class Location {

    /**
     * 纬度
     */
    @Getter
    @Setter
    private String longitude;

    /**
     * 经度
     */
    @Getter
    @Setter
    private String latitude;

    public Location(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Location() {
    }

    @Override
    public String toString() {
        return "POINT("+this.getLongitude()+","+this.getLatitude()+")";
    }
}
