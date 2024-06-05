package cloud.PythonConnection.Service.Iml;

import Dto.Alg;
import cloud.PythonConnection.Mapper.MapperForAlg;
import cloud.PythonConnection.Service.ServiceForAlg;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ServicesForAlgIml extends ServiceImpl<MapperForAlg, Alg> implements ServiceForAlg {
}
