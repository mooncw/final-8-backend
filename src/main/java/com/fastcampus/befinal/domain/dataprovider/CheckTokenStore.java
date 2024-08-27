package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.AuthInfo;

public interface CheckTokenStore {
    void store(AuthInfo.CheckIdTokenInfo info);

    void store(AuthInfo.CheckCertificationNumberTokenInfo info);
}
