package com.mikro.blog.payload;

public class JWTAuthResponse {
   private String accessToken;
   private String tokenType = "Bearer";
   private String getAccessToken() {
      return accessToken;
   }
    private void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private String getTokenType() {
        return tokenType;
    }

    private void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public JWTAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
