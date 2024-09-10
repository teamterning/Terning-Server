package org.terning.terningserver.domain.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static final Map<String, Color> colorMap =
            Arrays.stream(Color.values())
                    .collect(Collectors.toMap(Color::getName, color -> color));

    public String getColorValue() {
        return "#" + value;
    }

    public static Color findByName(String name) {
        return colorMap.get(name);
    }

}
