package cloud.PythonConnection.Controller;

import CommonResponse.CommonResponse;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RestController
public class PreController {
    @Resource
    APIClient APIClient;

    @Resource
    PythonClient pythonClient;

    @PostMapping("PythonConnection/test1")
    public String sendDataToPython(HttpServletRequest request,
                                   @RequestParam("ts_code")String ts_code,
                                   @RequestParam("alg")String alg) throws JsonProcessingException {
        // 获取当前日期和一年以前的日期
        LocalDate currentDate = LocalDate.now().minusDays(1);//API接口只能稳定的输出前一天的数据
        LocalDate oneYearAgoDate = currentDate.minusYears(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String currentDateString = currentDate.format(formatter);
        String oneYearAgoDateString = oneYearAgoDate.format(formatter);

        //设置传入API的各个参数（需要ts_code）
        Pre_params params = new Pre_params();
        //构建传递给哦API的param字段,注意这里为了测试方便改了时间
        params.setTs_code(ts_code).setStart_date("20240415").setEnd_date(currentDateString);
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
        //把items包装进PostToPython里面
        PostToPython postToPython = new PostToPython();
        postToPython.setUrl(alg).setData(items);
        //PostToPython转化为json
        String postToPythonJson = objectMapper.writeValueAsString(postToPython);

        System.out.println(postToPythonJson);
        //接下来发这个postToPythonJson给python，然后慢慢处理传递回来的参数



        return postToPythonJson;
    }






}
