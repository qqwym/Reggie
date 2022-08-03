package com.itheima.reggie.bean.utilBean;

import com.itheima.reggie.bean.Setmeal;
import com.itheima.reggie.bean.SetmealDish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetmealDto extends Setmeal {
    private String categoryName;
    private List<SetmealDish> setmealDishes;

}
