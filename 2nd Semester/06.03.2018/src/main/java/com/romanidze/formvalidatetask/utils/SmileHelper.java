package com.romanidze.formvalidatetask.utils;

import emoji4j.EmojiUtils;

/**
 * 09.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class SmileHelper {

    public String getEmojiView(String original) {
        return EmojiUtils.emojify(original);
    }

}
