package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.AuthInfo;

public interface CheckTokenReader {
    boolean exists(AuthInfo.CheckTokenInfo info);

    String find(AuthInfo.CheckTokenInfo info);
}
