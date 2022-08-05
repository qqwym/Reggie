package com.itheima.reggie.bean.utilBean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListS {
    private String name;

    //解决精度缺失
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
}
