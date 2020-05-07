package com.base.emoji;

import com.vdurmont.emoji.EmojiParser;

/**
 * Descripiton: 思路可以使用过滤器对前端传入参数进行过滤处理(表情处理)
 */
public class EmojiConvertUtils {


    public static void main(String[] args) {
        String sourceEmoji = "\uD83D\uDE1D";
        //前端表情转换为数据库可存储的字符串
        String emojiCode = toAliases(sourceEmoji);
        System.out.println(emojiCode);
        //还原
        String emoji = toUnicode(emojiCode);
        System.out.println(emoji);
    }


    /**
     * Description: 表情转换为字符串
     *
     * @param sourceEmoji
     * @return
     */
    public static String toUnicode(String sourceEmoji) {
        return EmojiParser.parseToUnicode(sourceEmoji);
    }

    /**
     * Description: 还原字符串为表情
     *
     * @param sourceUnicode
     * @return
     */
    public static String toAliases(String sourceUnicode) {
        return EmojiParser.parseToAliases(sourceUnicode);
    }
}
