package com.yuanheng100.settlement.common.model;

import com.yuanheng100.settlement.common.model.system.SysStaff;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by qianjl on 2016-7-5.
 */
public class LoginStaff implements Serializable{

    private SysStaff sysStaff;

    public SysStaff getSysStaff() {
        return sysStaff;
    }

    public void setSysStaff(SysStaff sysStaff) {
        this.sysStaff = sysStaff;
    }
}
