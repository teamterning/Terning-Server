package org.terning.terningserver.domain.enums;

public enum ProfileImage {
    BASIC("basic"),
    LUCKY("lucky"),
    SMART("smart"),
    GLASS("glass"),
    CALENDAR("calendar");

    private final String value;

    ProfileImage(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static ProfileImage fromValue(String value){
        for(ProfileImage image : values()){
            if(image.value.equalsIgnoreCase(value)){
                return image;
            }
        }
        throw new IllegalArgumentException("Invalid profile image: " + value);
    }
}
