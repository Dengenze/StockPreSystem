package cloud.PythonConnection.Controller;

import CommonResponse.CommonResponse;
import Dto.Alg;
import ReturnSpecial.SimpleStock;
import TuShareUsed.GrossClassForAPI.API_Return;
import TuShareUsed.GrossClassForAPI.DataAnlysis;
import TuShareUsed.Pre.*;
import TuShareUsed.GrossClassForAPI.TuShareJson;
import cloud.JwtTokenUtil;
import cloud.PythonConnection.FeignClient.APIClient;
import cloud.PythonConnection.FeignClient.PythonClient;
import cloud.PythonConnection.Service.ServiceForAlg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
public class PreController {
    private static final List<String> VALID_ALG_VALUES = Arrays.asList("DSFormer", "itransformer", "patchTST");
    @Resource
    APIClient APIClient;

    @Resource
    PythonClient pythonClient;

    @Resource
    ServiceForAlg serviceForAlg;

    @PostMapping("PythonConnection/startPre")
    public CommonResponse<List<SimpleStock>> sendDataToPython(HttpServletRequest request,
                                   @RequestParam("ts_code")String ts_code,
                                   @RequestParam("alg")String alg) throws JsonProcessingException {
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
        LocalDate LastDate = currentDate;//处理真正的最后一天(排除休市的干扰)

        for (int i = items.size() - 1; i >= 0; i--) {//倒叙访问
            Object[] item = items.get(i);
            SimpleStock simpleStock = new SimpleStock();
            LocalDate TradeDate = LocalDate.parse((String) item[0], formatter);
            simpleStock.setTradeDate(TradeDate);
            simpleStock.setOpen((Double) item[1]);
            simpleStock.setHigh((Double) item[2]);
            simpleStock.setLow((Double) item[3]);
            simpleStock.setClose((Double) item[4]);
            LastDate=TradeDate;//处理真正的最后一天(排除休市的干扰)
            simpleStockList.add(simpleStock);//包装好了预测前的全部数据
        }
        //现在LastDate里面存放的就是最后一天的日期了；
        LastDate=LastDate.plusDays(1);//下一天即预测开始
        //注意！这一句代码修改还没有更新到服务器上，记得下次顺便更新了

        //把items包装进PostToPython里面
        PostToPython postToPython = new PostToPython();
        alg = changetoURL(alg);

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


    @PostMapping("PythonConnection/UploadAlg")
    public CommonResponse<String> UploadAlg(HttpServletRequest request,
                                            @RequestParam("algname")String algname,
                                            @RequestParam("alggrade")String alggrade)
    {
        //写Alg表部分
        // 从请求头获取token
        String token = request.getHeader("Authorization");
        // 通过JwtTokenUtil工具类获取当前用户的账号
        String account = JwtTokenUtil.getUsername(token);

        if(!serviceForAlg.lambdaQuery().eq(Alg::getAlgname,algname).list().isEmpty())
        {
            return new CommonResponse<String>(400,"算法名重复",null,null);
        }

        // 获取当前日期和一年以前的日期
        LocalDate currentDate = LocalDate.now();

        Alg alg = new Alg();
        alg.setAccount(account).setAlggrade(alggrade).setIfpass("No").setAlgdate(currentDate).setAlgname(algname);
        boolean b = serviceForAlg.saveOrUpdate(alg);
        if (b)
        {
            return new CommonResponse<String>(200,"提交成功",null,null);
        }
        else
        {
            return new CommonResponse<String>(400,"提交失败",null,null);
        }
    }


    private void unzip(File zipFile, String destDir) throws IOException {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile.toPath()));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(new File(destDir), zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }


    public static String changetoURL(String alg) {
        if (!VALID_ALG_VALUES.contains(alg)) {
            Random random = new Random();
            alg = VALID_ALG_VALUES.get(random.nextInt(VALID_ALG_VALUES.size()));
        }
        return alg;
    }
}
