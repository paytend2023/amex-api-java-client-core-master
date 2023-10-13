package com.paytend.models.trans.req;

import cn.hutool.core.date.DateUtil;
import lombok.Builder;

import java.util.Date;
import java.util.Objects;

/**
 * @author gudongyang
 */
public class AuthorizationFactory {

    private AuthorizationConfig config;

    public AuthorizationFactory(AuthorizationConfig config) {
        this.config = config;
    }

    @Builder
    public static class AuthorizationConfig {
        Authorization.AuthorizationBuilder authorizationBuilder;
        PointOfServiceData.PointOfServiceDataBuilder pointOfServiceDataBuilder;
        //can null
        SecureAuthenticationSafeKey.SecureAuthenticationSafeKeyBuilder secureAuthenticationSafeKeyBuilder;
        CardAcceptorIdentification.CardAcceptorIdentificationBuilder cardAcceptorIdentificationBuilder;
        CardAcceptorDetail.CardAcceptorDetailBuilder cardAcceptorDetailBuilder;
        CardNotPresentData.CardNotPresentDataBuilder cardNotPresentDataBuilder;
        AcptEnvData.AcptEnvDataBuilder acptEnvDataBuilder;
        NatlUseData.NatlUseDataBuilder natlUseDataBuilder;
    }


    public Authorization create() {
        Authorization.AuthorizationBuilder authorizationBuilder = config.authorizationBuilder;
        if (Objects.nonNull(config.cardAcceptorDetailBuilder)) {
            authorizationBuilder.CardAcceptorIdentification(config.cardAcceptorIdentificationBuilder.build());
        }
        if (Objects.nonNull(config.cardAcceptorDetailBuilder)) {
            authorizationBuilder.CardAcceptorDetail(config.cardAcceptorDetailBuilder.build());

        }
        if (Objects.nonNull(config.cardNotPresentDataBuilder)) {
            AdditionalDataNational additionalDataNational = AdditionalDataNational.builder()
                    .CardNotPresentData(config.cardNotPresentDataBuilder.build())
                    .build();
            authorizationBuilder.AdditionalDataNational(additionalDataNational);
        }
        if (Objects.nonNull(config.pointOfServiceDataBuilder)) {
            authorizationBuilder.PointOfServiceData(config.pointOfServiceDataBuilder.build());
        }
        if (Objects.nonNull(config.acptEnvDataBuilder)) {
            authorizationBuilder.AcptEnvData(config.acptEnvDataBuilder.build());
        }

        if (config.secureAuthenticationSafeKeyBuilder != null) {
            authorizationBuilder.SecureAuthenticationSafeKey(config.secureAuthenticationSafeKeyBuilder.build());
        }

        if (config.natlUseDataBuilder != null) {
            authorizationBuilder.NatlUseData(config.natlUseDataBuilder.build());
        }

        buildTransTs(authorizationBuilder);
        return authorizationBuilder.build();
    }

    private void buildTransTs(Authorization.AuthorizationBuilder authorizationBuilder) {
        //YYMMDDhhmmss
        authorizationBuilder.TransTs(DateUtil.format(new Date(), "yyyyMMddHHmmss").substring(2));
    }

}
