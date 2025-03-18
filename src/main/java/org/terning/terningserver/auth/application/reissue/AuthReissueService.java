package org.terning.terningserver.auth.application.reissue;

import org.terning.terningserver.auth.dto.response.AccessTokenGetResponseDto;

public interface AuthReissueService {
    AccessTokenGetResponseDto reissueToken(String refreshToken);
}
