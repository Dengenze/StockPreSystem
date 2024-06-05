package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class InfoFromStr {
    double income;
    double AnnualizedIncome;
    double BenchmarkReturns;
    double Alpha;
    double Beta;
    double Sharpe;
    double Sortino;
    double InformationRatio;
    double AlgorithmVolatility;
    double BenchmarkVolatility;
    double MaxRollback;
}
