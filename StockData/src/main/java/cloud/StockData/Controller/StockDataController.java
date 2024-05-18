package cloud.StockData.Controller;

import CommonResponse.CommonResponse;
import Dto.Stock;
import Dto.StockData;
import cloud.DengSequrity;
import cloud.StockData.Service.StockService;
import com.alibaba.fastjson2.JSON;
import lombok.Data;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class StockDataController {
    @Resource
    StockService stockService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RestTemplate restTemplate;


    @GetMapping("StockData/getStockDataBySymbol")
    public CommonResponse<List<StockData>> getStockDataBySymbol(HttpServletRequest request,
                                                                @RequestParam("symbol")String symbol

    )
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<List<StockData>>(402,"权限不足",null,null);
        }
        //返回查询结果
        return new CommonResponse<>(200,"查询成功" , stockService.getStockBySymbol(symbol),null);
    }

    @GetMapping("StockData/getStockByName")
    public CommonResponse<List<Stock>> getStockByName(HttpServletRequest request,
                                                      @RequestParam("name")String name)
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<List<Stock>>(402,"权限不足",null,null);
        }
        List<Stock> list = stockService.lambdaQuery().like(Stock::getName, name).list();
        return new CommonResponse<>(200,"查询成功",list,null);
    }


    @GetMapping("StockData/getStockBySymbol")
    public CommonResponse<List<Stock>> getStockBySymbol(HttpServletRequest request,
                                                      @RequestParam("symbol")String symbol)
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<List<Stock>>(402,"权限不足",null,null);
        }
        List<Stock> list = stockService.lambdaQuery().eq(Stock::getSymbol, symbol).list();
        return new CommonResponse<>(200,"查询成功",list,null);
    }

    @GetMapping("StockData/getAllStock")
    public CommonResponse<List<Stock>> getAllStock(HttpServletRequest request)
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<List<Stock>>(402,"权限不足",null,null);
        }
        List<Stock> list = stockService.lambdaQuery().list();
        return new CommonResponse<>(200,"查询成功",list,null);
    }

    //获取000050之前每支股票数据到数据库
    @GetMapping("StockData/updateStock")
    public CommonResponse<String> testGet(){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        String dataString = "";
        String requestBody = "{\n" +
                "    \"api_name\": \"stock_basic\",\n" +
                "    \"token\": \"ecf89e8050ef23b2088a5241470de5eb3b5f05c371f578736b8eb587\",\n" +
                "    \"params\": {\"list_status\":\"L\"},\n" +
                "    \"fields\": \"symbol,ts_code,name,area,industry,list_date\"\n" +
                "}";

        HttpEntity<String> fromEntity = new HttpEntity<>(requestBody, httpHeaders);
        TuShareRetBody tuShareRetBody = restTemplate.postForObject("http://api.tushare.pro", fromEntity, TuShareRetBody.class);

        stockService.deleteAllStock();

        for(int i = 0; i < tuShareRetBody.data.items.size(); i++){
            List<String> item = tuShareRetBody.data.items.get(i);
            stockService.insertStock(item.get(0), item.get(1), item.get(2), item.get(3), item.get(4), item.get(5));
            if (item.get(1).equals("000050")) break;
        }

        return new CommonResponse<>(200,"查询成功",dataString,null);
    }

    //获取000050之前每支股票的近一年每日价格数据数据到redis
    @GetMapping("StockData/updateStockData")
    public CommonResponse<String> updateStockData() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        String dataString = "";

        String start_date, end_date;
        java.util.Date date = new java.util.Date();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(java.util.Calendar.YEAR, -1);
        //获取一年前的今天
        start_date = new java.text.SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        //获取今天
        end_date = new java.text.SimpleDateFormat("yyyyMMdd").format(date);

        for(int i = 1; i <= 50; i++){

            String ts_code = String.format("%06d.SZ", i);

            String requestBody = "{\n" +
                    "    \"api_name\": \"daily\",\n" +
                    "    \"token\": \"ecf89e8050ef23b2088a5241470de5eb3b5f05c371f578736b8eb587\",\n" +
                    "    \"params\": {\"ts_code\":\"" + ts_code + "\",\"start_date\":\"" + start_date + "\",\"end_date\":\"" + end_date + "\"},\n" +
                    "    \"fields\": \"ts_code,trade_date,open,high,low,close,pre_close,change,pct_chg,vol,amount\"\n" +
                    "}";

            HttpEntity<String> fromEntity = new HttpEntity<>(requestBody, httpHeaders);

            TuShareRetBody tuShareRetBody = restTemplate.postForObject("http://api.tushare.pro", fromEntity, TuShareRetBody.class);

            if (tuShareRetBody.data.items.isEmpty()) continue;

            dataString = JSON.toJSONString(tuShareRetBody.data.items);

            stringRedisTemplate.opsForValue().set(ts_code, dataString);

        }

        return new CommonResponse<>(200,"查询成功",dataString,null);
    }

}


class retData {
    public List<String> fields;
    public List<List<String>> items;
}
@Data
class TuShareRetBody {
    public String request_id;

    public int code;

    public String msg;

    public retData data;

}
