package cloud.PythonConnection.Controller;

import CommonResponse.CommonResponse;
import Dto.*;
import ReturnSpecial.StrBack;
import TuShareUsed.GrossClassForAPI.API_Return;
import TuShareUsed.GrossClassForAPI.DataAnlysis;
import TuShareUsed.GrossClassForAPI.TuShareJson;
import TuShareUsed.Pre.PostToPython;
import TuShareUsed.Pre.Pre_params;
import cloud.JwtTokenUtil;
import cloud.PythonConnection.FeignClient.PythonClientStr;
import cloud.PythonConnection.Service.UserServiceForStr;
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
import java.util.List;

@RestController
public class StrController {
    @Resource
    cloud.PythonConnection.FeignClient.APIClient APIClient;

    @Resource
    PythonClientStr pythonClient;

    @Resource
    UserServiceForStr serviceForStr;

    @PostMapping("PythonConnection/startStr")
    public CommonResponse<StrBack> sendDataToPython(HttpServletRequest request,
                                                              @RequestParam("ts_code")String ts_code,
                                                              @RequestParam("str")String str) throws JsonProcessingException {
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
        //先填写Graph的日期
        List<GraphFromStr> GraphFromStrList = new ArrayList<>();//返回类
        for (int i = items.size() - 1; i >= 0; i--) {//倒叙访问
            Object[] item = items.get(i);
            GraphFromStr graphFromStr = new GraphFromStr();
            LocalDate TradeDate = LocalDate.parse((String) item[0], formatter);
            graphFromStr.setData(TradeDate);
            GraphFromStrList.add(graphFromStr);
        }

        //把items包装进PostToPython里面
        PostToPython postToPython = new PostToPython();

        postToPython.setUrl(str).setData(items);//注意，这里"url"实际上是算法名字（统一目录下）
        //PostToPython转化为json
        String postToPythonJson = objectMapper.writeValueAsString(postToPython);

        System.out.println(postToPythonJson);
        //接下来发这个postToPythonJson给python，然后慢慢处理传递回来的参数
        String PythonReturnJson = pythonClient.sendData(postToPythonJson);
        StrPythonUsed strPythonUsed = objectMapper.readValue(PythonReturnJson, StrPythonUsed.class);//返回Json转化为类
        //处理返回的图像参数
        List<List<Double>> OrgGraph = strPythonUsed.getGraph();//未处理过的图像数据
        for (int i = OrgGraph.get(0).size() - 1; i >= 0; i--) {
            GraphFromStr graphFromStr = GraphFromStrList.get(i);
            graphFromStr.setBenchmark(OrgGraph.get(0).get(i));
            graphFromStr.setStrategy(OrgGraph.get(1).get(i));
        }
        //处理返回的信息参数
        InfoFromStr infoFromStr = new InfoFromStr();
        List<Double> info = strPythonUsed.getInfo();
        infoFromStr.setIncome(info.get(0));
        infoFromStr.setAnnualizedIncome(info.get(1));
        infoFromStr.setBenchmarkReturns(info.get(2));
        infoFromStr.setAlpha(info.get(3));
        infoFromStr.setBeta(info.get(4));
        infoFromStr.setSharpe(info.get(5));
        infoFromStr.setSortino(info.get(6));
        infoFromStr.setInformationRatio(info.get(7));
        infoFromStr.setAlgorithmVolatility(info.get(8));
        infoFromStr.setBenchmarkVolatility(info.get(9));
        infoFromStr.setMaxRollback(info.get(10));
        //组装返回类
        StrBack strBack = new StrBack();
        strBack.setGraph(GraphFromStrList).setInfo(infoFromStr);

        return new CommonResponse<StrBack>(200,"",strBack,null);
    }




    @PostMapping("PythonConnection/UploadStr")
    public CommonResponse<String> UploadAlg(HttpServletRequest request,
                                            @RequestParam("strname")String strname,
                                            @RequestParam("strgrade")String strgrade)
    {
        //写Str表部分
        // 从请求头获取token
        String token = request.getHeader("Authorization");
        // 通过JwtTokenUtil工具类获取当前用户的账号
        String account = JwtTokenUtil.getUsername(token);

        if(!serviceForStr.lambdaQuery().eq(Str::getStrname,strname).list().isEmpty())
        {
            return new CommonResponse<String>(400,"策略名重复",null,null);
        }

        // 获取当前日期和一年以前的日期
        LocalDate currentDate = LocalDate.now();

        Str str = new Str();
        str.setAccount(account).setStrgrade(strgrade).setIfpass("No").setStrdate(currentDate).setStrname(strname);
        boolean b = serviceForStr.saveOrUpdate(str);
        if (b)
        {
            return new CommonResponse<String>(200,"提交成功",null,null);
        }
        else
        {
            return new CommonResponse<String>(400,"提交失败",null,null);
        }
    }
}
