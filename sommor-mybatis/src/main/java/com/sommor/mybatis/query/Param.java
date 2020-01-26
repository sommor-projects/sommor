package com.sommor.mybatis.query;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
public class Param {

    private JSONObject json;

    private JSONObject json() {
        if (null == json) {
            json = (JSONObject) JSON.toJSON(this);
        }

        return json;
    }

    public <T> T get(String key) {
        return (T) json().get(key);
    }

}
