package com.tj720.service;

import com.tj720.model.common.SysDict;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/9/20.
 */
@Service
public interface SysDictSevice {

    /**
     *
     * @param keys 字典类型
     * @return 返回符合条件字典集合
     */
    List<SysDict> getDictListByKeys(List<String> keys);

    /**
     *
     * @param key  单个字典类型
     * @param dictCode  字典code
     * @param dictName  字典名
     * @return 返回符合条件字典集合
     */
    List<SysDict> getDictListByKey(String key,String dictCode,String dictName);

    /**
     *
     * @param key 单个字典类型
     * @return 返回符合条件字典集合
     */
    List<SysDict> getDictListByKey(String key);


    Map<String,Object> getDictListByArr(String[] arr);
}
