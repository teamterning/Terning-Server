package org.terning.terningserver.domain.enums;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Color {

    RED(0, "ED4E54"),
    ORANGE1(1, "EE7647"),
    ORANGE2(2, "5397F3"),
    YELLOW(3, "F5E660"),
    GREEN1(4, "C4E953"),
    GREEN2(5, "84D558"),
    BLUE1(6, "45D0CC"),
    BLUE2(7, "4AA9F2"),
    PURPLE(8, "9B64E2"),
    PINK(9, "F260AC");

    private final int key;
    private final String value;
}
