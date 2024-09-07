package org.terning.terningserver.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProfileImage {
    BASIC("basic"),
    LUCKY("lucky"),
    SMART("smart"),
    GLASS("glass"),
    CALENDAR("calendar");

    private final String value;

    public static ProfileImage fromValue(String value){
        for(ProfileImage image : values()){
            if(image.value.equalsIgnoreCase(value)){
                return image;
            }
        }
        throw new IllegalArgumentException("Invalid profile image: " + value);
    }
}
