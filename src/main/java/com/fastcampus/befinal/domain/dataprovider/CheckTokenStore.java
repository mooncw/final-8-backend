package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.AuthInfo;

public interface CheckTokenStore {
    void store(AuthInfo.CheckTokenInfo info);

    void delete(AuthInfo.CheckTokenInfo info);
}
