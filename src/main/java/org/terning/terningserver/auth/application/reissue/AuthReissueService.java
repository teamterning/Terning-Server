package org.terning.terningserver.auth.application.reissue;

import org.terning.terningserver.dto.auth.response.AccessTokenGetResponseDto;

public interface AuthReissueService {
    AccessTokenGetResponseDto reissueToken(String refreshToken);
}
