package com.yuanheng100.settlement.ghbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jlqian on 2017/5/14.
 */
@Controller
@RequestMapping("/ghbank/test1")
public class TestIndexController
{

    @RequestMapping("/testIndex")
    public String testIndex()
    {
        return "ghbank/test1/testIndex";
    }

}
