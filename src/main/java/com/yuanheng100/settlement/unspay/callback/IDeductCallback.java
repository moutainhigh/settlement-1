package com.yuanheng100.settlement.unspay.callback;

import com.yuanheng100.settlement.unspay.model.UnspayDeduct;

/**
 * Created by jlqian on 2016/8/4.
 */
public interface IDeductCallback {

    void call(UnspayDeduct unspayDeduct);

}
