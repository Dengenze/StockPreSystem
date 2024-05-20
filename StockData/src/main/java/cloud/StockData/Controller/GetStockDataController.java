package cloud.StockData.Controller;

import CommonResponse.CommonResponse;
import TuShareUsed.GrossClassForAPI.API_Return;
import TuShareUsed.GrossClassForAPI.TuShareJson;
import cloud.StockData.FeignClient.TuShareAPIClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class GetStockDataController {
    @Resource
    TuShareAPIClient TuShareAPIClient;


    @PostMapping ("StockData/UpDataTableStock")
    public CommonResponse<String> UpDataTableStock() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();//类与JSON互相转化的工具
        TuShareJson<Object> tuShareJson = new TuShareJson<>();//建立传递类（这里不需要传递参数）
        //设置传入的参数
        tuShareJson.setApi_name("stock_basic").setParams(null).setFields("symbol,ts_code,name,area,industry,list_date");
        String json = objectMapper.writeValueAsString(tuShareJson);//转化为json
        String databack = TuShareAPIClient.sendData(json);//发送请求，返回值保存进databack里面；
        System.out.println(json);

        //把返回值转化为JSON
        API_Return apiReturn = objectMapper.readValue(databack, API_Return.class);


        return new CommonResponse<String>(200,"",databack,null);
    }



}
