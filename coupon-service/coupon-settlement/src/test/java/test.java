import java.math.BigDecimal;

/**
 * @author sllxgmm
 * @version 1.0
 * @desc <h1> test </h1>
 * @date 2020/3/16 21:51
 * @email 1558078764@qq.com
 */
public class test {
    public static void main(String[] args) {
        System.out.println(retain2Decimals(BigDecimal.valueOf(2223.45644)));
    }

    static double retain2Decimals(BigDecimal value) {

        return value.setScale(
                2, BigDecimal.ROUND_HALF_UP
        ).doubleValue();
    }
}
