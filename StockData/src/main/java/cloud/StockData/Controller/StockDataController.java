package cloud.StockData.Controller;

import CommonResponse.CommonResponse;
import Dto.Stock;
import Dto.StockData;
import Dto.TuShareRetBody;
import TuShareUsed.GrossClassForAPI.API_Return;
import TuShareUsed.GrossClassForAPI.DataAnlysis;
import TuShareUsed.GrossClassForAPI.TuShareJson;
import TuShareUsed.Pre.Pre_params;
import cloud.DengSequrity;
import cloud.StockData.Service.StockService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Resource
    cloud.StockData.FeignClient.TuShareAPIClient TuShareAPIClient;


    @GetMapping("StockData/getStockDataBySymbol")
    public CommonResponse<List<StockData>> getStockDataBySymbol(HttpServletRequest request,
                                                                @RequestParam("symbol")String symbol

    )
    {
//        //需要权限
//        if(!DengSequrity.DengSequrity(request,"User"))
//        {
//            return new CommonResponse<List<StockData>>(402,"权限不足",null,null);
//        }

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
        List<StockData> stockDataList = JSON.parseObject(jsonObject.getString("stockDataList"), new TypeReference<List<StockData>>(){});


//        List<StockData> stockDataList = JSON.parseObject(dataString, new TypeReference<List<StockData>>(){});

        //如果数据为空，返回查询失败
        if(dataString == null)
        {
            return new CommonResponse<List<StockData>>(400,"查询失败",null,null);
        }

        return new CommonResponse<List<StockData>>(200,"查询成功", stockDataList,null);
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
    public CommonResponse<String> updateStockData() throws JsonProcessingException {

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-Type", "application/json");
//        String dataString = "";
//
//        String start_date, end_date;
//        java.util.Date date = new java.util.Date();
//        java.util.Calendar calendar = java.util.Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(java.util.Calendar.YEAR, -1);
//        //获取一年前的今天
//        start_date = new java.text.SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
//        //获取今天
//        end_date = new java.text.SimpleDateFormat("yyyyMMdd").format(date);

        //删除redis所有数据
        stringRedisTemplate.delete(Objects.requireNonNull(stringRedisTemplate.keys("*")));

        for(int i = 1; i <= 50; i++){

            String symbol = String.format("%06d", i);

            List<Stock> list = stockService.lambdaQuery().eq(Stock::getSymbol, symbol).list();
            if (list.isEmpty()) continue;
            String ts_code = list.get(0).getTscode();//获取ts_code
            //下面基本上复制Pre那个数据获取的
            // 获取当前日期和一年以前的日期
            LocalDate currentDate = LocalDate.now().minusDays(1);//API接口只能稳定的输出前一天的数据
            LocalDate halfYearAgoDate = currentDate.minusMonths(12);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            String currentDateString = currentDate.format(formatter);
            String halfYearAgoDateString = halfYearAgoDate.format(formatter);

            //设置传入API的各个参数（需要ts_code）
            Pre_params params = new Pre_params();
            //构建传递给哦API的param字段
            params.setTs_code(ts_code).setStart_date(halfYearAgoDateString).setEnd_date(currentDateString);
            //构建传递给API的json
            TuShareJson<Pre_params> tuShareJson = new TuShareJson<>();
            tuShareJson.setApi_name("daily").setParams(params).setFields("ts_code,trade_date,open,high,low,close,pre_close,change,pct_chg,vol,amount");
            //将输入类转化为JSON
            String JsonToSend;
            ObjectMapper objectMapper = new ObjectMapper();
            JsonToSend= objectMapper.writeValueAsString(tuShareJson);

            //向API发送请求，并接住返回的信息
            API_Return getDailyParamsPreUseReturn1 = objectMapper.readValue(TuShareAPIClient.sendData(JsonToSend), API_Return.class);
            DataAnlysis dataAnlysis1 = getDailyParamsPreUseReturn1.getData();//取出data内数据
            ArrayList<Object[]> items1 = dataAnlysis1.getItems();//取出data内items信息

            //构建传递给API的第二个json（params不用改）
            TuShareJson<Pre_params> tuShareJson2 = new TuShareJson<>();
            tuShareJson2.setApi_name("daily_basic").setParams(params).setFields("trade_date,pe,total_share,total_mv,turnover_rate");
            //将输入类转化为JSON
            String JsonToSend2;
            JsonToSend2= objectMapper.writeValueAsString(tuShareJson2);

            //向API发送请求，并接住返回的信息
            API_Return getDailyParamsPreUseReturn2 = objectMapper.readValue(TuShareAPIClient.sendData(JsonToSend2), API_Return.class);
            DataAnlysis dataAnlysis2 = getDailyParamsPreUseReturn2.getData();//取出data内数据
            ArrayList<Object[]> items2 = dataAnlysis2.getItems();//取出data内items信息

            List<StockData> stockDataPerDayList = new ArrayList<>();
            for (Object[] item : items1) {
                StockData stockDataPerDay = new StockData();
                stockDataPerDay.setTscode((String) item[0]);
                LocalDate TradeDate = LocalDate.parse((String) item[1], formatter);
                stockDataPerDay.setTradeDate(TradeDate);
                stockDataPerDay.setOpen((Double) item[2]);
                stockDataPerDay.setHigh((Double) item[3]);
                stockDataPerDay.setLow((Double) item[4]);
                stockDataPerDay.setClose((Double) item[5]);
                stockDataPerDay.setPreClose((Double) item[6]);
                stockDataPerDay.setChange((Double) item[7]);
                stockDataPerDay.setPctChg((Double) item[8]);
                stockDataPerDay.setVol((Double) item[9]);
                stockDataPerDay.setAmount((Double) item[10]);
                for (int j = 0; j < items2.size(); j++) {
                    Object[] item2 = items2.get(j);
                    if(item2[0].equals(item[1]))//如果两个trade_date相等
                    {
                        stockDataPerDay.setPe((Double) item2[2]);
                        stockDataPerDay.setTotal_share((Double) item2[3]);
                        stockDataPerDay.setTotal_mv((Double) item2[4]);
                        stockDataPerDay.setTurnover_rate((Double) item2[1]);
                        break;
                    }
                }
                stockDataPerDayList.add(stockDataPerDay);
            }

//            String requestBody = "{\n" +
//                    "    \"api_name\": \"daily\",\n" +
//                    "    \"token\": \"ecf89e8050ef23b2088a5241470de5eb3b5f05c371f578736b8eb587\",\n" +
//                    "    \"params\": {\"ts_code\":\"" + ts_code + "\",\"start_date\":\"" + start_date + "\",\"end_date\":\"" + end_date + "\"},\n" +
//                    "    \"fields\": \"ts_code,trade_date,open,high,low,close,pre_close,change,pct_chg,vol,amount\"\n" +
//                    "}";
//
//            HttpEntity<String> fromEntity = new HttpEntity<>(requestBody, httpHeaders);
//
//            TuShareRetBody tuShareRetBody = restTemplate.postForObject("http://api.tushare.pro", fromEntity, TuShareRetBody.class);
//
//            if (tuShareRetBody.data.items.isEmpty()) continue;
//
//            dataString = JSON.toJSONString(tuShareRetBody.data.items);
//
//            List<StockData> stockDataList = new ArrayList<>();
//
//            for(int j = 0; j < tuShareRetBody.data.items.size(); j++){
//                List<String> item = tuShareRetBody.data.items.get(j);
//                stockDataList.add(new StockData().setSymbol(item.get(0)).setTradeDate(item.get(1)).setOpen(item.get(2)).setHigh(item.get(3)).setLow(item.get(4)).setClose(item.get(5)).setPreClose(item.get(6)).setChange(item.get(7)).setPctChg(item.get(8)).setVol(item.get(9)).setAmount(item.get(10)));
//            }
//
            String dataString = JSON.toJSONString(stockDataPerDayList);

            stringRedisTemplate.opsForValue().set(ts_code, dataString);

        }



        return new CommonResponse<>(200,"查询成功","",null);
    }

}


