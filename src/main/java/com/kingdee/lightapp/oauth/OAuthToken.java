package com.kingdee.lightapp.oauth;

import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

public  class OAuthToken
{
  private String token;
  private String tokenSecret;
  private transient SecretKeySpec secretKeySpec;
  String[] responseStr = null;

  
  public OAuthToken() {
  }

  public OAuthToken(String token, String tokenSecret) {
    this.token = token;
    this.tokenSecret = tokenSecret;
  }

	public  OAuthToken(JSONObject reponse) {
    this.token = reponse.getString("oauth_token");
    this.tokenSecret = reponse.getString("oauth_token_secret");
  }

	public  OAuthToken(String string) {
    this.responseStr = string.split("&");
    this.token = getParameter("oauth_token");
    this.tokenSecret = getParameter("oauth_token_secret");
  }

  public String getToken() {
    return this.token;
  }

  public String getTokenSecret() {
    return this.tokenSecret;
  }

  void setSecretKeySpec(SecretKeySpec secretKeySpec) {
    this.secretKeySpec = secretKeySpec;
  }

  SecretKeySpec getSecretKeySpec() {
    return this.secretKeySpec;
  }

  public String getParameter(String parameter) {
    String value = null;
    for (String str : this.responseStr) {
      if (str.startsWith(parameter + '=')) {
        value = str.split("=")[1].trim();
        break;
      }
    }
    return value;
  }

  public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (!(o instanceof OAuthToken)) {
      return false;
    }
    OAuthToken that = (OAuthToken)o;

    if (this.secretKeySpec != null ? !this.secretKeySpec.equals(that.secretKeySpec) : 
      that.secretKeySpec != null)
      return false;
    if (!this.token.equals(that.token))
      return false;
    if (!this.tokenSecret.equals(that.tokenSecret)) {
      return false;
    }
    return true;
  }

  public int hashCode()
  {
    int result = this.token.hashCode();
    result = 31 * result + this.tokenSecret.hashCode();
    result = 31 * result + (
      this.secretKeySpec != null ? this.secretKeySpec.hashCode() : 0);
    return result;
  }

  public String toString()
  {
    return "OAuthToken{token='" + this.token + '\'' + ", tokenSecret='" + 
      this.tokenSecret + '\'' + ", secretKeySpec=" + this.secretKeySpec + '}';
  }
}