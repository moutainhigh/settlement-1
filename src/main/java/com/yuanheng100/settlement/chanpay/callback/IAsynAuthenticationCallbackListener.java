package com.yuanheng100.settlement.chanpay.callback;

import com.yuanheng100.settlement.chanpay.model.G60002Bean;

/**
 * Created by jlqian on 2016/11/28.
 */
public interface IAsynAuthenticationCallbackListener
{
    void authenticate(G60002Bean g60002Bean);
}
