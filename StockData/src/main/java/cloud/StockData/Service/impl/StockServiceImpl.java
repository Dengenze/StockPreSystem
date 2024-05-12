package cloud.StockData.Service.impl;

import Dto.StockData;
import cloud.StockData.Mapper.StockDataMapper;
import cloud.StockData.Service.StockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import Dto.Stock;
import java.util.List;

@Service
public class StockServiceImpl extends ServiceImpl<StockDataMapper,Stock> implements StockService {
    @Resource
    StockDataMapper stockDataMapper;

    public List<StockData> getStockBySymbol(String symbol)
    {
        return stockDataMapper.getStockBySymbol(symbol);
    }
}
