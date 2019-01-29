package com.tj720.service.impl;/**
 * Created by Administrator on 2018/9/25.
 */

import com.tj720.dao.MipAreaMapper;
import com.tj720.model.common.MipArea;
import com.tj720.service.MipAreaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wyf
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
@Service
public class MipAreaServiceImpl implements MipAreaService {

    @Autowired
    private MipAreaMapper mipAreaMapper;

    @Override
    public List<MipArea> getAreaByPid(int pid) {
        return mipAreaMapper.getAreaByPid(pid);
    }
}
