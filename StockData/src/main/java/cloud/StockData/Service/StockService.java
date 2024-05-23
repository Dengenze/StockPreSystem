package cloud.StockData.Service;

import Dto.Stock;
import Dto.StockDataPerDay;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface StockService extends IService<Stock> {
    List<StockDataPerDay> getStockBySymbol(String symbol);

    int deleteAllStock();

    int insertStock(String tscode, String symbol, String name, String area, String industry, String listdate);
}
