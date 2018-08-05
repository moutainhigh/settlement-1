package com.yuanheng100.settlement.common.service.impl;

import com.yuanheng100.settlement.common.mapper.SysStaffMapper;
import com.yuanheng100.settlement.common.model.system.SysStaff;
import com.yuanheng100.settlement.common.service.ISysStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qianjl on 2016-7-5.
 */
@Service("sysStaffService")
public class SysStaffServiceImpl implements ISysStaffService {

    @Autowired
    private SysStaffMapper sysStaffMapper;

    @Override
    public SysStaff login(Integer id, String passwd) {
        return sysStaffMapper.selectByIdAndPasswd(id,passwd);
    }

    @Override
    public void edit(SysStaff sysStaff) {
        sysStaffMapper.updateByIdSelective(sysStaff);
    }
}
