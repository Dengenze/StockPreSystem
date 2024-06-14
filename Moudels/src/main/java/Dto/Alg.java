package Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Alg {
    @TableId(type= IdType.AUTO)
    private int algid;
    private String account;
    private String algname;
    private String alggrade;
    private String ifpass;
    private LocalDate algdate;

    String introduction;
}
