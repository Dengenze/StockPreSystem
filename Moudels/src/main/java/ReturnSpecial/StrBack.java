package ReturnSpecial;

import Dto.GraphFromStr;
import Dto.InfoFromStr;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StrBack {
    private InfoFromStr info;
    private List<GraphFromStr> graph;
}
