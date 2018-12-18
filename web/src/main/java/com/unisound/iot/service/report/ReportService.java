package com.unisound.iot.service.report;

import com.unisound.iot.dao.mapper.source2.report.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Created by yingwuluohan on 2018/10/18.
 * @Company 北京云知声技术有限公司
 */
@Service
public class ReportService {

    @Autowired
    private ReportDao reportDao;


    public String findAllAppkey(){
        List<String> appkeyList = reportDao.findReportList();
        System.out.println( appkeyList );
        return "";
    }
}
