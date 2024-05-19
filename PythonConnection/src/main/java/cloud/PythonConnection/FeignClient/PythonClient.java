package cloud.PythonConnection.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PythonClient", url = "http://192.168.239.209:8887")
public interface PythonClient {
    @PostMapping(value = "/", consumes = "application/json")
    String sendData(@RequestBody String body);
}
