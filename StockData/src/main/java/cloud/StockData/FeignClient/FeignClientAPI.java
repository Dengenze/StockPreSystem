package cloud.StockData.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "feignClientAPI", url = "http://api.example.com")
public interface FeignClientAPI {
    @PostMapping("/data")
    public String sendData(@RequestBody String body);
}
