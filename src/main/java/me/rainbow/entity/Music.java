package me.rainbow.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guojinpeng
 * @date 18.1.8 13:06
 */
public class Music {
    private String id;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, String> getSongs() {
        HashMap<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(value)) {
            JSONObject parse = (JSONObject) JSON.parse(value);
            for (Map.Entry<String, Object> objectEntry : parse.entrySet()) {
                map.put(objectEntry.getKey(), String.valueOf(objectEntry.getValue()));
            }
        }
        return map;
    }
}
