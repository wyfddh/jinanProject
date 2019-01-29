package com.tj720.service.impl;

import com.tj720.dao.SysDictMapper;
import com.tj720.model.common.SysDict;
import com.tj720.service.SysDictSevice;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/9/20.
 */
@Service
public class SysDictSeviceImpl implements SysDictSevice{

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public List<SysDict> getDictListByKeys(List<String> keys) {
        if (keys != null) {
            if (keys.size() > 0) {
                return sysDictMapper.getDictListByKeys(keys);
            }
        }
        return null;
    }

    @Override
    public List<SysDict> getDictListByKey(String key, String dictCode, String dictName) {

        return sysDictMapper.getDictListByKey(key,dictCode,dictName);
    }

    @Override
    public List<SysDict> getDictListByKey(String key) {
        List<SysDict> dictListByKey = getDictListByKey(key, null, null);
        return dictListByKey;
    }

    @Override
    public Map<String, Object> getDictListByArr(String[] arr) {
        List<String> keys = Arrays.asList(arr);
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < keys.size(); i++) {
            List<SysDict> list = getDictListByKey(keys.get(i));
            map.put(keys.get(i), list);
        }
        return map;
    }
}
