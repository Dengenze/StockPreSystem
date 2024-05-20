package cloud.StockData.Controller;

import CommonResponse.CommonResponse;
import Dto.Stock;
import Dto.majornews;
import Dto.news;
import Dto.toplist;
import TuShareUsed.GrossClassForAPI.API_Return;
import TuShareUsed.GrossClassForAPI.DataAnlysis;
import TuShareUsed.GrossClassForAPI.TuShareJson;
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

        LocalDate currentDate = LocalDate.now();//API接口只能稳定的输出前一天的数据
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

}
