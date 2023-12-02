package com.project.soohofit.common.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.project.soohofit.common.BaseUtils;
import com.project.soohofit.common.config.security.repository.UserOauth2Repository;
import com.project.soohofit.common.config.security.repository.UserSecurityRepository;
import com.project.soohofit.user.domain.User;
import com.project.soohofit.user.domain.UserOauth;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static org.springframework.data.util.Optionals.ifPresentOrElse;

@Service
@RequiredArgsConstructor
@Transactional
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserSecurityRepository userSecurityRepository;
    private final UserOauth2Repository userOauth2Repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // Oauth Role 조회
        Iterator<? extends GrantedAuthority> iterator = (oAuth2User.getAuthorities()).iterator();
        String oauthRole = iterator.next().getAuthority();

        if (provider.equals("kakao")) {
            JSONObject jsonObject = null;

            try {

                jsonObject = BaseUtils.getJsonObject(oAuth2User.getAttributes().get("kakao_account"));

                if (!BaseUtils.isEmpty(jsonObject)) {

                    String kakaoId = String.valueOf(oAuth2User.getAttributes().get("id")); // 카카오 ID
                    String email = String.valueOf(jsonObject.get("email"));                // 카카오 email
                    String nickName = String.valueOf(jsonObject.get("profile"));            // 카카오 닉네임

                    findOrSave(kakaoId, email, nickName, provider, oauthRole);

                }

            } catch (Exception e) {
                throw new RuntimeException("json 파싱 에러");
            }

        }
        else if (provider.equals("google")) {
            Map<String, Object> attributes = oAuth2User.getAttributes();

            String googleId = (String) attributes.get("sub");
            String email = (String) attributes.get("email");
            String name = (String) attributes.get("name");

            findOrSave(googleId, email, name, provider, oauthRole);

        }
        else if (provider.equals("naver")) {

        }

        return oAuth2User;
    }

    /**
     * SNS 로그인 기존회원이 존재하지 않으면 자동 회원가입
     *
     * @param id
     * @param email
     * @param name
     * @param provider
     * @param oauthRole
     */
    private void findOrSave(String id, String email, String name, String provider, String oauthRole) {

        ifPresentOrElse(userSecurityRepository.findByUserId(id),
                user -> user.setRole(oauthRole),
                () -> {
                    // 사용자 저장
                    userSecurityRepository.save(User.builder()
                            .userId(id)
                            .userNm(name)
                            .email(email)
                            .role(oauthRole)
                            .build()
                    );
                    // 사용자 SNS 저장
                    userOauth2Repository.save(UserOauth.builder()
                            .userId(id)
                            .provider(provider)
                            .providerId(id)
                            .build()
                    );
                }
        );

    }


}
