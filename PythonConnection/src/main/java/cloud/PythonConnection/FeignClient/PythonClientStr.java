package cloud.PythonConnection.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PythonClientStr", url = "http://47.115.207.110:8886")
public interface PythonClientStr {
    @PostMapping(value = "/", consumes = "application/json")
    String sendData(@RequestBody String body);
}
