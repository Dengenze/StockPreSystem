package cloud.StockData.Controller;

import CommonResponse.CommonResponse;
import Dto.*;
import TuShareUsed.GrossClassForAPI.API_Return;
import TuShareUsed.GrossClassForAPI.DataAnlysis;
import TuShareUsed.GrossClassForAPI.TuShareJson;
import TuShareUsed.Pre.Pre_params;
import TuShareUsed.StockNewsParams;
import TuShareUsed.TopListParams;
import cloud.StockData.FeignClient.TuShareAPIClient;
import cloud.StockData.Service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class GetStockDataController {
    @Resource
    TuShareAPIClient TuShareAPIClient;
    @Resource
    StockService stockService;

    @PostMapping ("StockData/UpDataTableStock")
    public CommonResponse<String> UpDataTableStock() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();//类与JSON互相转化的工具
        TuShareJson<Object> tuShareJson = new TuShareJson<>();//建立传递类（这里不需要传递参数）
        //设置传入的参数
        tuShareJson.setApi_name("stock_basic").setParams(null).setFields("symbol,ts_code,name,area,industry,list_date");
        String json = objectMapper.writeValueAsString(tuShareJson);//转化为json
        String databack = TuShareAPIClient.sendData(json);//发送请求，返回值保存进databack里面；

        //把返回值转化为类
        API_Return apiReturn = objectMapper.readValue(databack, API_Return.class);
        DataAnlysis data = apiReturn.getData();//取出数据字段（其他的不要了）
        ArrayList<Object[]> items = data.getItems();//数据字段里面只有items字段是有用的

        //下面开始遍历处理这个Object[]
        int count = 0; // 计数器，用于记录已经遍历的元素个数，我只存100只股票
        // 创建 DateTimeFormatter 对象，指定解析格式为YYYYMMDD
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        stockService.remove(null);//清理数据库
        for (Object[] item : items) {
            if(count>100)
            {
                break;
            }
            Stock stock = new Stock();//每一行都是要存储的一个stock对象
            String element1 = String.valueOf(item[0]);//处理tscode
            stock.setTscode(element1);
            String element2=String.valueOf(item[1]);//处理symbol
            stock.setSymbol(element2);
            String element3=String.valueOf(item[2]);//处理name
            stock.setName(element3);
            String element4=String.valueOf(item[3]);//处理area
            stock.setArea(element4);
            String element5=String.valueOf(item[4]);//处理industry
            stock.setIndustry(element5);
            String element6=String.valueOf(item[5]);//处理date
            LocalDate date = LocalDate.parse(element6, formatter);//转化格式
            stock.setListdate(date);
            //写入数据库
            stockService.save(stock);
            count++;
        }

        return new CommonResponse<String>(200,"",null,null);
    }

    @GetMapping("StockData/getTopList")
    public CommonResponse<List<toplist>> getTopList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();//类与JSON互相转化的工具
        System.out.println("start");
        LocalDate currentDate = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String currentDateString = currentDate.format(formatter);

        TuShareJson<Object> tuShareJson = new TuShareJson<>();//建立传递类
        TopListParams topListParams = new TopListParams();//设置传递params
        topListParams.setTrade_date(currentDateString);

        //设置其他传入的参数
        tuShareJson.setApi_name("top_list").setParams(topListParams).setFields("trade_date,ts_code,name,close,pct_change,turnover_rate,amount,reason");
        String json = objectMapper.writeValueAsString(tuShareJson);//转化为json
        String databack = TuShareAPIClient.sendData(json);//发送请求，返回值保存进databack里面；
        API_Return apiReturn = objectMapper.readValue(databack, API_Return.class);
        DataAnlysis data = apiReturn.getData();//取出数据字段（其他的不要了）
        ArrayList<Object[]> items = data.getItems();//数据字段里面只有items字段是有用的

        List<toplist> toplists = new ArrayList<>();

        for (Object[] item : items) {
            toplist toplist = new toplist();
            String element1=String.valueOf(item[0]);//处理date
            LocalDate date = LocalDate.parse(element1, formatter);//转化格式
            toplist.setTradedate(date);
            toplist.setTscode((String) item[1]);
            toplist.setName((String) item[2]);
            toplist.setClose((Double) item[3]);
            toplist.setPct_change((Double) item[4]);
            toplist.setTurnover_rate((Double) item[5]);
            toplist.setAmount((Double) item[6]);
            toplist.setReason((String) item[7]);
            System.out.println(toplist);
            toplists.add(toplist);
        }
        return new CommonResponse<List<toplist>>(200,"",toplists,null);
    }

    @GetMapping("StockData/getMajorNews")
    public CommonResponse<List<majornews>> getmajornews() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();//类与JSON互相转化的工具

        TuShareJson<Object> tuShareJson = new TuShareJson<>();//建立传递类
        //设置其他传入的参数
        tuShareJson.setApi_name("major_news").setParams(null).setFields("title,pub_time,src");
        String json = objectMapper.writeValueAsString(tuShareJson);//转化为json
        String databack = TuShareAPIClient.sendData(json);//发送请求，返回值保存进databack里面；
        API_Return apiReturn = objectMapper.readValue(databack, API_Return.class);
        DataAnlysis data = apiReturn.getData();//取出数据字段（其他的不要了）
        ArrayList<Object[]> items = data.getItems();//数据字段里面只有items字段是有用的
        //处理时间数据
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<majornews> majornewsList = new ArrayList<>();
        int i=0;
        for (Object[] item : items) {
            if(i>20)
            {
                break;
            }
            majornews news = new majornews();
            news.setTitle((String) item[0]);
            news.setSrc((String) item[2]);
            LocalDateTime dateTime = LocalDateTime.parse((String) item[1], formatter);
            news.setPub_time(dateTime);
            majornewsList.add(news);
            i++;
        }
        return new CommonResponse<List<majornews>>(200,"",majornewsList,null);
    }

    @GetMapping("StockData/getStockNews")
    public CommonResponse<List<news>> getStockNews(HttpServletRequest request,
                                                        @RequestParam("symbol")String symbol) throws JsonProcessingException {
        List<Stock> list = stockService.lambdaQuery().eq(Stock::getSymbol, symbol).list();
        String ts_code = list.get(0).getTscode();//获取ts_code

        ObjectMapper objectMapper = new ObjectMapper();//类与JSON互相转化的工具

        TuShareJson<Object> tuShareJson = new TuShareJson<>();//建立传递类
        StockNewsParams stockNewsParams = new StockNewsParams();//设置params
        stockNewsParams.setTs_code(ts_code);
        //设置其他传入的参数
        tuShareJson.setApi_name("anns_d").setParams(stockNewsParams).setFields("ann_date,ts_code,name,title,url,rec_time");
        String json = objectMapper.writeValueAsString(tuShareJson);//转化为json
        String databack = TuShareAPIClient.sendData(json);//发送请求，返回值保存进databack里面；
        API_Return apiReturn = objectMapper.readValue(databack, API_Return.class);
        DataAnlysis data = apiReturn.getData();//取出数据字段（其他的不要了）
        ArrayList<Object[]> items = data.getItems();//数据字段里面只有items字段是有用的
        //处理时间数据
        DateTimeFormatter rec_time_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter ann_date_formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        List<news> StockNews = new ArrayList<>();
        int i=0;
        for (Object[] item : items) {
            if(i>20)
            {
                break;
            }
            news news = new news();
            news.setTscode((String) item[1]);
            news.setName((String) item[2]);
            news.setTitle((String) item[3]);
            LocalDate dateTime = LocalDate.parse((String) item[0], ann_date_formatter);
            news.setAnn_date(dateTime);
            LocalDateTime parse = LocalDateTime.parse((String) item[5], rec_time_formatter);
            news.setRec_time(parse);
            news.setUrl((String) item[4]);

            StockNews.add(news);
            i++;
        }
        return new CommonResponse<List<news>>(200,"",StockNews,null);
    }

    @GetMapping("StockData/getStockDataBySymbol")
    public CommonResponse<List<StockDataPerDay>> getStockDataBySymbol(HttpServletRequest request,
                                                                      @RequestParam("symbol")String symbol

    ) throws JsonProcessingException {
        //需要权限
//        if(!DengSequrity.DengSequrity(request,"User"))
//        {
//            return new CommonResponse<List<StockData>>(402,"权限不足",null,null);
//        }


        List<Stock> list = stockService.lambdaQuery().eq(Stock::getSymbol, symbol).list();
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

        List<StockDataPerDay> stockDataPerDayList = new ArrayList<>();
        for (Object[] item : items1) {
            StockDataPerDay stockDataPerDay = new StockDataPerDay();
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
            for (int i = 0; i < items2.size(); i++) {
                Object[] item2 = items2.get(i);
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

        return new CommonResponse<>(200,"",stockDataPerDayList,null);

    }

    }
