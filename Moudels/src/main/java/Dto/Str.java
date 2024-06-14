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
public class Str {
    @TableId(type= IdType.AUTO)
    private int strid;
    private String account;
    private String strname;
    private String strgrade;
    private String ifpass;
    private LocalDate strdate;
    String introduction;
}
