package cloud.StockData.Service.impl;

import Dto.StockData;
import cloud.StockData.Mapper.StockDataMapper;
import cloud.StockData.Service.StockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import Dto.Stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StockServiceImpl extends ServiceImpl<StockDataMapper,Stock> implements StockService {
    @Resource
    StockDataMapper stockDataMapper;

    public List<StockData> getStockBySymbol(String symbol)
    {
        return stockDataMapper.getStockBySymbol(symbol);
    }


    public int deleteAllStock()
    {
        return stockDataMapper.deleteAllStock();
    }

    public int insertStock( String tscode, String symbol,String name, String area, String industry, String listdate)
    {
        Date listdate1 = new Date();
        //yymmdd格式的listdate转为Date
        try {
            listdate1 = new SimpleDateFormat("yyyyMMdd").parse(listdate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return stockDataMapper.insertStock(symbol, tscode, name, area, industry, listdate1);
    }


}
