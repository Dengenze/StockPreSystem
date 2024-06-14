package cloud.PythonConnection.Controller;

import CommonResponse.CommonResponse;
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
    public CommonResponse<List<SimpleStock>> GetMarketIndex(@RequestParam("id")String id) throws JsonProcessingException {
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
            return new CommonResponse<List<SimpleStock>>(400,"错误",null,"");
        }

        // 获取当前日期和一年以前的日期
        LocalDate currentDate = LocalDate.now().minusDays(1);//API接口只能稳定的输出前一天的数据
        LocalDate halfYearAgoDate = currentDate.minusMonths(6);//一年的数据太卡了，改成半年
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String currentDateString = currentDate.format(formatter);
        String halfYearAgoDateString = halfYearAgoDate.format(formatter);

        //设置传入API的各个参数（需要ts_code）
        Pre_params params = new Pre_params();
        //构建传递给哦API的param字段
        params.setTs_code(ts_code).setStart_date(halfYearAgoDateString).setEnd_date(currentDateString);
        //构建传递给API的json
        TuShareJson<Pre_params> tuShareJson = new TuShareJson<>();
        tuShareJson.setApi_name("daily").setParams(params).setFields("trade_date,open,high,low,close");
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
        List<SimpleStock> simpleStockList = new ArrayList<>();//返回类

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
        }
        //现在LastDate里面存放的就是最后一天的日期了；
        return new CommonResponse<>(200,"",simpleStockList,null);
    }
}
