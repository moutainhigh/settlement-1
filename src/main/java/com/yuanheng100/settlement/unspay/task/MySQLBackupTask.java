package com.yuanheng100.settlement.unspay.task;

import com.yuanheng100.util.MySQLBackup;
import org.springframework.stereotype.Component;

/**
 * Created by jlqian on 2016/8/19.
 */
@Component
public class MySQLBackupTask {

    public void backup(){
        MySQLBackup.backupAndSave("settlementbakup","yhst@203","settlement","127.0.0.1",null,null,null,null);
    }
}
