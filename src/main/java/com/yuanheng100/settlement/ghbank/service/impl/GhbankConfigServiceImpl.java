package com.yuanheng100.settlement.ghbank.service.impl;

import com.yuanheng100.settlement.ghbank.service.IGhbankConfigService;
import com.yuanheng100.util.ConfigFile;

public class GhbankConfigServiceImpl implements IGhbankConfigService
{

    @Override
    public String getPostFormUrl()
    {
        return ConfigFile.getProperty("ghbank.url");
    }

}
