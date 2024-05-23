package cloud.StockData.Controller;

import CommonResponse.CommonResponse;
import Dto.Stock;
import Dto.StockData;
import Dto.StockDataPerDay;
import cloud.DengSequrity;
import cloud.StockData.Service.StockService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import lombok.Data;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class StockDataController {
    @Resource
    StockService stockService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RestTemplate restTemplate;


    @GetMapping("StockData/getStockDataBySymbol")
    //这个接口同名了
    public CommonResponse<List<StockDataPerDay>> getStockDataBySymbol(HttpServletRequest request,
                                                                      @RequestParam("symbol")String symbol

    )
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<List<StockDataPerDay>>(402,"权限不足",null,null);
        }

        //从redis中获取数据
        String dataString = stringRedisTemplate.opsForValue().get(symbol + ".SZ");

        //去除首尾的中括号
//        dataString = dataString.substring(1,dataString.length()-1);
        dataString = "\"stockDataList\":" + dataString;

        //首尾加上大括号
        dataString = "{" + dataString + "}";

//        System.out.println("111" + dataString);

        //字符串转为json对象
        JSONObject jsonObject = JSONObject.parseObject(dataString);

        // jsonObject转为list
        List<StockDataPerDay> stockDataList = JSON.parseObject(jsonObject.getString("stockDataList"), new TypeReference<List<StockData>>(){});


//        List<StockData> stockDataList = JSON.parseObject(dataString, new TypeReference<List<StockData>>(){});

        //如果数据为空，返回查询失败
        if(dataString == null)
        {
            return new CommonResponse<List<StockDataPerDay>>(400,"查询失败",null,null);
        }

        return new CommonResponse<List<StockDataPerDay>>(200,"查询成功", stockDataList,null);
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

        //删除redis所有数据
        stringRedisTemplate.delete(Objects.requireNonNull(stringRedisTemplate.keys("*")));

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

            List<StockDataPerDay> stockDataList = new ArrayList<>();

            for(int j = 0; j < tuShareRetBody.data.items.size(); j++){
                List<String> item = tuShareRetBody.data.items.get(j);
                stockDataList.add(new StockDataPerDay().setSymbol(item.get(0)).setTradeDate(item.get(1)).setOpen(item.get(2)).setHigh(item.get(3)).setLow(item.get(4)).setClose(item.get(5)).setPreClose(item.get(6)).setChange(item.get(7)).setPctChg(item.get(8)).setVol(item.get(9)).setAmount(item.get(10)));
            }

            dataString = JSON.toJSONString(stockDataList);

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
