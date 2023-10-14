package com.cbim.epc.supply.common.utils;

public class BinaryConverterUtil {

    /**
     * 将十进制数转换为二进制数并获取指定位的值
     *
     * @param decimal 十进制数
     * @param bit     指定位的位置，最右边的位置为1
     * @return int    指定位的值
     */
    public static boolean getBitValue(int decimal, int bit) {
        if (decimal < 0 || bit <= 0) {
            throw new IllegalArgumentException("Decimal and bit arguments must be non-negative.");
        }
        // 将十进制数转换为二进制字符串并翻转
        String binaryString = new StringBuilder(Integer.toBinaryString(decimal)).reverse().toString();
        // 如果指定位小于二进制字符串长度，则返回指定位的值，否则返回0
        return bit <= binaryString.length() && binaryString.charAt(bit - 1) - '0' == 1;
    }

    /**
     * @return -1 代表目标位等于目标值，不需要修改
     */
    public static int changeBitValue(int decimal, int bit, Character target) {
        if (decimal < 0 || bit <= 0) {
            throw new IllegalArgumentException("Decimal and bit arguments must be non-negative.");
        }
        // 将十进制数转换为二进制字符串并翻转
        StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(decimal)).reverse();
        // 如果指定位小于二进制字符串长度，则补全
        int length = binaryString.length();
        if (length < bit) {
            for (int i = 1; i <= bit - length; i++) {
                if (i == bit - length) {
                    binaryString.append(target);
                } else {
                    binaryString.append('0');
                }
            }
            String string = binaryString.reverse().toString();
            return binaryToDecimal(string);
        }
        // 替换
        if (binaryString.charAt(bit - 1) != target) {
            char[] charArray = binaryString.toString().toCharArray();
            charArray[bit - 1] = target;
            // 翻转回开始的顺序，转为十进制
            String string = new StringBuilder(new String(charArray)).reverse().toString();
            return binaryToDecimal(string);
        }
        return -1;
    }

    private static int binaryToDecimal(String str) {
        int decimal = 0;
        int power = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '1') {
                decimal += Math.pow(2, power);
            }
            power++;
        }
        return decimal;
    }
}
