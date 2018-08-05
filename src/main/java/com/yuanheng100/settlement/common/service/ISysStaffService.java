package com.yuanheng100.settlement.common.service;

import com.yuanheng100.settlement.common.model.system.SysStaff;

/**
 * Created by qianjl on 2016-7-5.
 */
public interface ISysStaffService {

    SysStaff login(Integer id , String passwd);

    void edit(SysStaff sysStaff);

}
