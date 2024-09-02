package org.terning.terningserver.domain.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Color {

    RED("red", "ED4E54"),
    ORANGE("orange", "F3A649"),
    LIGHT_GREEN("lightgreen", "C4E953"),
    MINT("mint", "45D0CC"),
    PURPLE("purple", "9B64E2"),
    CORAL("coral", "EE7647"),
    YELLOW("yellow", "F5E660"),
    GREEN("green", "84D558"),
    BLUE("blue", "4AA9F2"),
    PINK("pink", "F260AC");

    private final String name;
    private final String value;

    public String getColorValue() {
        return "#" + value;
    }
}
