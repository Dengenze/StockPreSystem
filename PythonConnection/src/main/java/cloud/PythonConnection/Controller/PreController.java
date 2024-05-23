package cloud.PythonConnection.Controller;

import CommonResponse.CommonResponse;
import Dto.news;
import ReturnSpecial.SimpleStock;
import TuShareUsed.GrossClassForAPI.API_Return;
import TuShareUsed.GrossClassForAPI.DataAnlysis;
import TuShareUsed.Pre.*;
import TuShareUsed.GrossClassForAPI.TuShareJson;
import cloud.PythonConnection.FeignClient.APIClient;
import cloud.PythonConnection.FeignClient.PythonClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PreController {
    @Resource
    APIClient APIClient;

    @Resource
    PythonClient pythonClient;

    @PostMapping("PythonConnection/startPre")
    public CommonResponse<List<SimpleStock>> sendDataToPython(HttpServletRequest request,
                                   @RequestParam("ts_code")String ts_code,
                                   @RequestParam("alg")String alg) throws JsonProcessingException {
        // 获取当前日期和一年以前的日期
        LocalDate currentDate = LocalDate.now().minusDays(1);//API接口只能稳定的输出前一天的数据
        LocalDate halfYearAgoDate = currentDate.minusMonths(6);//一年的数据太卡了，改成半年
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

//      String currentDateString = currentDate.format(formatter);
//      String halfYearAgoDateString = halfYearAgoDate.format(formatter);
        String currentDateString ="20240517";
        String halfYearAgoDateString="20231120";
        //python端存在一定的问题（输入天数似乎不能改变？），先写死

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
        LocalDate LastDate = currentDate;//处理真正的最后一天(排除休市的干扰)

        for (int i = items.size() - 1; i >= 0; i--) {//倒叙访问
            Object[] item = items.get(i);
            SimpleStock simpleStock = new SimpleStock();
            LocalDate TradeDate = LocalDate.parse((String) item[0], formatter);
            simpleStock.setTradeDate(TradeDate);
            simpleStock.setOpen((Double) item[1]);
            simpleStock.setHigh((Double) item[1]);
            simpleStock.setLow((Double) item[3]);
            simpleStock.setClose((Double) item[4]);
            LastDate=TradeDate;//处理真正的最后一天(排除休市的干扰)
            simpleStockList.add(simpleStock);//包装好了预测前的全部数据
        }
        //现在LastDate里面存放的就是最后一天的日期了；




        //把items包装进PostToPython里面
        PostToPython postToPython = new PostToPython();
        postToPython.setUrl(alg).setData(items);//注意，这里"url"实际上是算法名字（统一目录下）
        //PostToPython转化为json
        String postToPythonJson = objectMapper.writeValueAsString(postToPython);

        System.out.println(postToPythonJson);
        //接下来发这个postToPythonJson给python，然后慢慢处理传递回来的参数
        String PythonReturnJson = pythonClient.sendData(postToPythonJson);

        double[][] array = objectMapper.readValue(PythonReturnJson, double[][].class);

        // 遍历二维数组并处理每个元素
        int dateI=2;//日期处理的计数器
        for (int i = 0; i < array.length; i++) {
            SimpleStock simpleStock = new SimpleStock();
            simpleStock.setTradeDate(LastDate.plusDays(i));//简单的在真正的最后一天上加一（不处理休市日了）
            simpleStock.setOpen(array[i][0]);//当前行的第一个是open
            simpleStock.setHigh(array[i][1]);//当前行的第一个是high
            simpleStock.setLow(array[i][2]);//当前行的第一个是low
            simpleStock.setClose(array[i][3]);//当前行的第一个是close
            simpleStockList.add(simpleStock);
        }

        return new CommonResponse<>(200,"",simpleStockList,null);
    }






}
