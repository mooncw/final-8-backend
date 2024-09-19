package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.AuthInfo;

public interface CheckTokenStore {
    void store(AuthInfo.CheckTokenInfo info);

    void store(AuthInfo.CheckTokenInfo info, String userId);

    void delete(AuthInfo.CheckTokenInfo info);
}
