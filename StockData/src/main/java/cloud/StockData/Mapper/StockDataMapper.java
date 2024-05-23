package cloud.StockData.Mapper;

import Dto.Stock;
import Dto.StockDataPerDay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface StockDataMapper extends BaseMapper<Stock> {

    //查找某一股票的全部数据
    @Select("SELECT * FROM stockdata WHERE symbol = #{symbol}")
    public List<StockDataPerDay> getStockBySymbol(String symbol);

    //删除所有股票信息
    @Delete("delete from stock")
    public int deleteAllStock();

    //插入股票信息
    @Insert("insert into stock(symbol, tscode, name, area, industry, listdate) values(#{symbol}, #{tscode}, #{name}, #{area}, #{industry}, #{listdate})")
    public int insertStock(@Param("symbol")String symbol, @Param("tscode")String tscode, @Param("name")String name, @Param("area")String area, @Param("industry")String industry, @Param("listdate") Date listdate);
}
