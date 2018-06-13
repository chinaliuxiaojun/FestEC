package com.lxj.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by lxj on 2018/6/11.
 */

public enum  EcIcons implements Icon {
    icon_scan('\ue7bb'),
    icon_ali_pay('\ue694');

    EcIcons(char character) {
        this.character = character;
    }

    private char character;
    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
