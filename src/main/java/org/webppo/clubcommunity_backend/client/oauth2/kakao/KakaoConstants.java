package org.webppo.clubcommunity_backend.client.oauth2.kakao;

public class KakaoConstants {
    public static final String KAKAO_REGISTRATION_ID = "kakao";
    public static final String KAKAO_REDIRECT_URI = "/login/oauth2/code/kakao";
    public static final String IOS_STATE = "ios";
    public static final String KAKAO_ACCOUNT = "kakao_account";
    public static final String KAKAO_PROFILE = "profile";
    public static final String KAKAO_ID = "id";
    public static final String KAKAO_NICKNAME = "nickname";
    public static final String KAKAO_PROFILE_IMAGE_URL = "profile_image_url";
    public static final String KAKAO_UNLINK_URL = "https://kapi.kakao.com/v1/user/unlink";
    public static final String KAKAO_UNLINK_REQUEST_BODY_FORMAT = "target_id_type=user_id&target_id=%s";
}

