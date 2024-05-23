package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StockDataPerDay {
    private String tscode;
    private LocalDate tradeDate;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double preClose;
    private Double change;
    private Double pctChg;
    private Double vol;
    private Double amount;
    private Double pe;
    private Double total_share;
    private Double total_mv;
    private Double turnover_rate;
}
