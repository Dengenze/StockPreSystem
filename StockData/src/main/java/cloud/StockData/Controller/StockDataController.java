package cloud.StockData.Controller;

import CommonResponse.CommonResponse;
import Dto.Stock;
import Dto.StockData;
import cloud.DengSequrity;
import cloud.StockData.Service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class StockDataController {
    @Resource
    StockService stockService;
    @GetMapping("StockData/getStockDataBySymbol")
    public CommonResponse<List<StockData>> getStockDataBySymbol(HttpServletRequest request,
                                                                @RequestParam("symbol")String symbol

    )
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<List<StockData>>(402,"权限不足",null,null);
        }
        //返回查询结果
        return new CommonResponse<>(200,"查询成功" , stockService.getStockBySymbol(symbol),null);
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

}
