package com.tj720.dao;


import com.tj720.model.common.MipArea;
import java.util.List;

public interface MipAreaMapper {

    List<MipArea> getAreaByPid(int pid);
}
