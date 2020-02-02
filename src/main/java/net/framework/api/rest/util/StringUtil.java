package net.framework.api.rest.util;

/**
 * 文字列操作のユーティリティクラス。
 */
public class StringUtil {

    /**
     * 文字列の桁数チェックを実施します.
     * @param String target
     * @param int max
     * @return boolean
     */
    public static boolean isOverSpecificLength(String target, int max) {
        if (target.length() > max) {
            return true;
        }
        return false;
    }

    /**
     * 文字列の長さが指定された長さであることを検査します.
     * @param String target
     * @param int max
     * @return boolean
     */
    public static boolean isNotEqualToSpecificLength(String target, int length) {
        if (target.length() != length) {
            return true;
        }
        return false;
    }

    /**
     * 文字列の長さが指定された長さであることを検査します。
     * String以外のインスタンスである場合、全てfalseを返却します。
     * @param subject 被検査対象
     * @param length 調査したい文字列の長さ
     * @return 判定結果
     */
    public static <T> boolean isParticularLength(T subject, int length) {
        // 文字列の場合、intにパースして長さ比較
        if(subject.getClass() == String.class && ((String)subject).length() == length) {
            return true;
        }

        return false;
    }

    /**
     * 文字列が空もしくはnullであることを確認します。
     * @param target 対象文字列
     * @return boolean 判定結果
     */
    public static boolean isNullOrBlank(String target) {
        return target == null || target == "";
    }

    /**
     * nullの場合空文字に変換します。
     * @param s 対象文字列
     * @return 変換後文字列
     */
    public static String convertNullToEmpty(String s) {
        return s == null ? "" : s;
    }

}
