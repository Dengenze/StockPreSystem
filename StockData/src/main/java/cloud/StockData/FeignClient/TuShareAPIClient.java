package cloud.StockData.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "TuShareAPIClient", url = "http://api.tushare.pro")
public interface TuShareAPIClient {
    @PostMapping(value = "/", consumes = "application/json")
    String sendData(@RequestBody String body);
}
