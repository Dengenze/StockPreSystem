package cloud.StockData.Controller;

import cloud.StockData.FeignClient.FeignClientAPI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class GetStockDataController {
    @Resource
    FeignClientAPI feignClientAPI;


    @PostMapping("StockData/feigntest")
    public void feignTest()
    {
       

    }



}
