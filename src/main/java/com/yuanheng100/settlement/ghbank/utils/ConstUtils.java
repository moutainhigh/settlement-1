package com.yuanheng100.settlement.ghbank.utils;

import java.util.HashMap;
import java.util.Map;

import com.yuanheng100.settlement.ghbank.consts.DFFlag;
import com.yuanheng100.settlement.ghbank.consts.IDType;
import com.yuanheng100.settlement.ghbank.consts.OperFlag;
import com.yuanheng100.settlement.ghbank.consts.ReturnStatus;
import com.yuanheng100.settlement.ghbank.consts.TrsType;

/**
 * Created by jlqian on 2017/4/19.
 */
public class ConstUtils
{
    private static Map<Short, TrsType> trsTypeMap;
    /**
     * 证件类型
     */
    private static Map<Short, IDType> idTypeMap;

    private static Map<String, ReturnStatus> returnStatusMap;

    private static Map<String, ReturnStatus> rsStatusMap;

    private static Map<Short, DFFlag> dfFlagMap;

    private static Map<Short, OperFlag> operFlagMap;

    static
    {
        TrsType[] values6 = TrsType.values();
        trsTypeMap = new HashMap<Short, TrsType>();
        for (TrsType trsType : values6)
        {
            trsTypeMap.put(trsType.getType(), trsType);
        }


        IDType[] values = IDType.values();
        idTypeMap = new HashMap<Short, IDType>();
        for (IDType idType : values)
        {
            idTypeMap.put(idType.getType(), idType);
        }

        ReturnStatus[] values1 = ReturnStatus.values();
        returnStatusMap = new HashMap<String, ReturnStatus>();
        for (ReturnStatus returnStatus : values1)
        {
            returnStatusMap.put(returnStatus.name(), returnStatus);
        }

        ReturnStatus[] values3 = ReturnStatus.values();
        rsStatusMap = new HashMap<String, ReturnStatus>();
        for (ReturnStatus rsStatus : values3)
        {
            rsStatusMap.put(rsStatus.name(), rsStatus);
        }

        DFFlag[] values4 = DFFlag.values();
        dfFlagMap = new HashMap<Short, DFFlag>();
        for (DFFlag dfFlag : values4)
        {
            dfFlagMap.put(dfFlag.getFlag(), dfFlag);
        }

        OperFlag[] values5 = OperFlag.values();
        operFlagMap = new HashMap<Short, OperFlag>();
        for (OperFlag operFlag : values5)
        {
            operFlagMap.put(operFlag.getFlag(), operFlag);
        }
    }

    /**
     * 获取证件类型
     *
     * @param type
     * @return
     */
    public static IDType getIDType(Integer type)
    {
        return idTypeMap.get(type);
    }

    /**
     * 获取返回状态
     *
     * @param status
     * @return
     */
    public static ReturnStatus getReturnStatus(String status)
    {
        return returnStatusMap.get(status);
    }

    /**
     * 投标优惠结果
     *
     * @param status
     * @return
     */
    public static ReturnStatus getRsStatus(String status)
    {
        return rsStatusMap.get(status);
    }

    /**
     * 还款类型
     *
     * @param flag
     * @return
     */
    public static DFFlag getDFFlag(Short flag)
    {
        return dfFlagMap.get(flag);
    }

    /**
     * 对账类型
     *
     * @param flag
     * @return
     */
    public static OperFlag getOperFlag(Short flag)
    {
        return operFlagMap.get(flag);
    }

    /**
     * 操作类型
     *
     * @param type
     * @return
     */
    public static TrsType getTrsType(Short type)
    {
        return trsTypeMap.get(type);
    }

}
