package cloud.PythonConnection.Controller;

import CommonResponse.CommonResponse;
import ReturnSpecial.MarketInfo;
import ReturnSpecial.SimpleStock;
import TuShareUsed.GrossClassForAPI.API_Return;
import TuShareUsed.GrossClassForAPI.DataAnlysis;
import TuShareUsed.GrossClassForAPI.TuShareJson;
import TuShareUsed.Pre.Pre_params;
import cloud.PythonConnection.FeignClient.PythonClient;
import cloud.PythonConnection.Service.ServiceForAlg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MarketIndex {
    @Resource
    cloud.PythonConnection.FeignClient.APIClient APIClient;

    @Resource
    PythonClient pythonClient;

    @Resource
    ServiceForAlg serviceForAlg;

    @PostMapping("PythonConnection/GetMarketIndex")
    public CommonResponse<MarketInfo> GetMarketIndex(@RequestParam("id")String id) throws JsonProcessingException {
        String ts_code;
        if(id.equals("1"))
        {
            ts_code="000488.SZ";
        }
        else if(id.equals("2"))
        {
            ts_code="000425.SZ";
        }
        else if(id.equals("3"))
        {
            ts_code="000426.SZ";
        }
        else if(id.equals("4"))
        {
            ts_code="000428.SZ";
        }
        else
        {
            return new CommonResponse<MarketInfo>(400,"错误",null,"");
        }

        // 获取当前日期和一年以前的日期
        LocalDate currentDate = LocalDate.now().minusDays(1);//API接口只能稳定的输出前一天的数据
        LocalDate halfYearAgoDate = currentDate.minusMonths(6);//一年的数据太卡了，改成半年
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String currentDateString = currentDate.format(formatter);
        String halfYearAgoDateString = halfYearAgoDate.format(formatter);

        //设置传入API的各个参数（需要ts_code）
        Pre_params params = new Pre_params();
        //构建传递给API的param字段
        params.setTs_code(ts_code).setStart_date(halfYearAgoDateString).setEnd_date(currentDateString);
        //构建传递给API的json
        TuShareJson<Pre_params> tuShareJson = new TuShareJson<>();
        tuShareJson.setApi_name("daily").setParams(params).setFields("trade_date,open,high,low,close,vol,amount");
        //将输入类转化为JSON
        String JsonToSend;
        ObjectMapper objectMapper = new ObjectMapper();
        JsonToSend= objectMapper.writeValueAsString(tuShareJson);

        System.out.println(JsonToSend);

        //向API发送请求，并接住返回的信息
        API_Return getDailyParamsPreUseReturn = objectMapper.readValue(APIClient.sendData(JsonToSend), API_Return.class);
        DataAnlysis dataAnlysis = getDailyParamsPreUseReturn.getData();//取出data内数据
        ArrayList<Object[]> items = dataAnlysis.getItems();//取出data内items信息
        //先构建返回类的前半部分
        MarketInfo marketInfo = new MarketInfo();
        List<SimpleStock> simpleStockList = new ArrayList<>();//返回类

        double avgVolume = 0, avgAmount = 0;
        int count = 0;

        for (int i = items.size() - 1; i >= 0; i--) {//倒叙访问
            Object[] item = items.get(i);
            SimpleStock simpleStock = new SimpleStock();
            LocalDate TradeDate = LocalDate.parse((String) item[0], formatter);
            simpleStock.setTradeDate(TradeDate);
            simpleStock.setOpen((Double) item[1]);
            simpleStock.setHigh((Double) item[2]);
            simpleStock.setLow((Double) item[3]);
            simpleStock.setClose((Double) item[4]);
            //处理真正的最后一天(排除休市的干扰)
            simpleStockList.add(simpleStock);//包装好了预测前的全部数据
            avgVolume+=(Double) item[5];
            avgAmount+=(Double) item[6];
            count++;
        }
        //现在LastDate里面存放的就是最后一天的日期了；
        avgVolume /= count;
        avgAmount /= count;

        marketInfo.setVolume(avgVolume).setAmount(avgAmount).setSimpleStocks(simpleStockList);

        //设置传入API的各个参数（需要ts_code）
        Pre_params params1 = new Pre_params();
        //构建传递给API的param字段
        params1.setTs_code(ts_code);
        //构建传递给API的json
        TuShareJson<Pre_params> tuShareJson1 = new TuShareJson<>();
        tuShareJson1.setApi_name("stock_basic").setParams(params).setFields("ts_code,symbol,name");
        //将输入类转化为JSON
        String JsonToSend1;
        ObjectMapper objectMapper1 = new ObjectMapper();
        JsonToSend1= objectMapper1.writeValueAsString(tuShareJson1);

        System.out.println(JsonToSend1);

        //向API发送请求，并接住返回的信息
        API_Return getDailyParamsPreUseReturn1 = objectMapper1.readValue(APIClient.sendData(JsonToSend1), API_Return.class);
        DataAnlysis dataAnlysis1 = getDailyParamsPreUseReturn1.getData();//取出data内数据
        ArrayList<Object[]> items1 = dataAnlysis1.getItems();//取出data内items信息

        marketInfo.setTsCode((String) items1.get(0)[0]).setSymbol((String) items1.get(0)[1]).setName((String) items1.get(0)[2]);

        //设置传入API的各个参数（需要ts_code）
        Pre_params params2 = new Pre_params();
        //构建传递给API的param字段
        params2.setTs_code(ts_code).setStart_date(halfYearAgoDateString).setEnd_date(currentDateString);
        //构建传递给API的json
        TuShareJson<Pre_params> tuShareJson2 = new TuShareJson<>();
        tuShareJson2.setApi_name("daily_basic").setParams(params).setFields("turnover_rate,pe,total_mv,total_share");
        //将输入类转化为JSON
        String JsonToSend2;
        ObjectMapper objectMapper2 = new ObjectMapper();
        JsonToSend2= objectMapper2.writeValueAsString(tuShareJson2);

        System.out.println(JsonToSend2);

        //向API发送请求，并接住返回的信息
        API_Return getDailyParamsPreUseReturn2 = objectMapper2.readValue(APIClient.sendData(JsonToSend2), API_Return.class);
        DataAnlysis dataAnlysis2 = getDailyParamsPreUseReturn2.getData();//取出data内数据
        ArrayList<Object[]> items2 = dataAnlysis2.getItems();//取出data内items信息

        Double avgTurnoverRate = 0.0, avgPe = 0.0, avgTotalValue = 0.0, avgTotalShares = 0.0;
        int count2 = 0;

        for (int i = items2.size() - 1; i >= 0; i--) {//倒叙访问
            Object[] item = items2.get(i);
            if(item[0] != null) avgTurnoverRate += (Double) item[0];
            if(item[1] != null) avgPe += (Double) item[1];
            if(item[2] != null) avgTotalValue += (Double) item[2];
            if(item[3] != null) avgTotalShares += (Double) item[3];
            count2++;
        }

        avgTurnoverRate /= count2;
        avgPe /= count2;
        avgTotalValue /= count2;

        marketInfo.setTurnoverRate(avgTurnoverRate).setPe(avgPe).setTotalValue(avgTotalValue).setTotalShares(avgTotalShares);

        return new CommonResponse<>(200,"",marketInfo,null);
    }
}
