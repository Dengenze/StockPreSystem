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
public class toplist {
    private LocalDate tradedate;
    private String tscode;
    private String name;
    private Double close;
    private Double pct_change;
    private Double turnover_rate;
    private Double amount;
    private String reason;
}
