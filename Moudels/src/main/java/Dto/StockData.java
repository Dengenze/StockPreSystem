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
    private LocalDateTime tradeDate;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Float preClose;
    private Float change;
    private Float pctChg;
    private Float vol;
    private Float amount;
}
