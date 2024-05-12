package cloud.StockData.Mapper;

import Dto.Stock;
import Dto.StockData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockDataMapper extends BaseMapper<Stock> {

    //查找某一股票的全部数据
    @Select("SELECT * FROM stockdata WHERE symbol = #{symbol}")
    public List<StockData> getStockBySymbol(String symbol);
}
