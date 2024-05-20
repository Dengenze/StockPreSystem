package ReturnSpecial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SimpleStock {
     String close;
     String high;
     String low;
     String open;
     LocalDate tradeDate;
}
