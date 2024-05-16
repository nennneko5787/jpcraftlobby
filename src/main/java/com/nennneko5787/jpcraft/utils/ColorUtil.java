package com.nennneko5787.jpcraft.utils;

public class ColorUtil {
    /**
     * 文字列内のカラーコード候補（&a）を、カラーコード（§a）に置き換えする
     * @param source 置き換え元の文字列
     * @return 置き換え後の文字列
     */
    public static String replaceColorCode(String source) {
        if (source == null) return null;
        return replaceWebColorCode(source)
                .replaceAll("&([0-9a-fk-orA-FK-OR])", "\u00A7$1");
    }

    /**
     * Webカラーコード（#99AABBなど）を、カラーコードに置き換えする
     * @param source 置き換え元の文字列
     * @return 置き換え後の文字列
     */
    private static String replaceWebColorCode(String source) {
        return source
                .replaceAll(
                        "#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])",
                        "\u00A7x\u00A7$1\u00A7$2\u00A7$3\u00A7$4\u00A7$5\u00A7$6")
                .replaceAll(
                        "#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])",
                        "\u00A7x\u00A7$1\u00A7$1\u00A7$2\u00A7$2\u00A7$3\u00A7$3");
    }
}
