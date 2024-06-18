package ReturnSpecial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MarketInfo {

    //ts代码
    private String tsCode;

    //股票代码
    private String symbol;

    //股票名称
    private String name;

    //换手率
    private Double turnoverRate;

    //市盈率
    private Double pe;

    //成交量
    private Double volume;

    //成交额
    private Double amount;

    //总市值
    private Double totalValue;

    //总股本
    private Double totalShares;

    //价格
    List<SimpleStock> simpleStocks;

}
