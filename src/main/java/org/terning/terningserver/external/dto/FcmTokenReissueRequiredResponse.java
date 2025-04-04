package org.terning.terningserver.external.dto;

public record FcmTokenReissueRequiredResponse(boolean reissueRequired) {
    public static FcmTokenReissueRequiredResponse of(boolean reissueRequired) {
        return new FcmTokenReissueRequiredResponse(reissueRequired);
    }
}
