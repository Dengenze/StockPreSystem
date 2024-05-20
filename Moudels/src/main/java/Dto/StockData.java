package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StockData {
    private String symbol;
    private String tradeDate;
    private String open;
    private String high;
    private String low;
    private String close;
    private String preClose;
    private String change;
    private String pctChg;
    private String vol;
    private String amount;
}
