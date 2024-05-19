package cloud.PythonConnection.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PreClient", url = "http://api.tushare.pro")
public interface APIClient {
    @PostMapping(value = "/", consumes = "application/json")
    String sendData(@RequestBody String body);
}
