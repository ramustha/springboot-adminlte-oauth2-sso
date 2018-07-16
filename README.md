# Spring Boot + Admin LTE + Spring Security OAuth2 

This demo app consists of following three components:

* [Authorization](authorization) ... OAuth2 [Authorization Server](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security-oauth2-authorization-server)
* [Resource](resource) ... OAuth2 [Resource Server](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security-oauth2-resource-server). Provides REST API.
* [UI](ui) ... Web UI using [SSO](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security-oauth2-single-sign-on) based on OAuth2

### Stack
* Spring-boot 1.5.12.RELEASE
* Postgres
* Hibernate
* Thymeleaf
* Admin LTE 2.3.3


### Authorization Code Flow

* UI -> Authorization
* UI (Authorize) -> Resource 

### Resource Owner Password Credentials Flow

Get an Access Token

``` console
$ curl -XPOST -u news:news_secret localhost:9999/auth/oauth/token -d grant_type=password -d username=reader -d password=reader
{
     "access_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzE3MjI3NjgsInVzZXJfbmFtZSI6InJlYWRlciIsImF1dGhvcml0aWVzIjpbIm5ld3NfcmVhZCJdLCJqdGkiOiI3MWU3NDg4NS04ZTM1LTRlODktOWE3NS03ODUyOTgxYzk3YmQiLCJjbGllbnRfaWQiOiJuZXdzIiwic2NvcGUiOlsibmV3cyJdfQ.Q7Fh0rYSQCCjSzQ9LSXg__o6tKCgFdB-KERHLbKIuUW55lwedoUPjSnUz7zehNMm0Ayjbe2HmdDh9n0_eJFjwCTxqe1wIUXIJV-Od7IDKR2bYSMZXAv7YZrS1OsBbarskuwXHPAW7Jrx2zS3Ym8VYj3ihOI7LahULcjNbNWVaxcMHofD6lyYeBkpC5b_AMiSFJymvo2fqonQjwkwpm-nqAwrSYeaifseA8zX9969VCkoScOGf9p6IdeqNlsvwh1u6rn_3VedH8ayEDK22Y4z88bBRtCwZ6vEEr7bOXIhPBHwojOdE0Jj40epBXAbx939vYE5rGAuf3gXFM4JCyE-jQ",
     "token_type":"bearer",
     "refresh_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJyZWFkZXIiLCJzY29wZSI6WyJuZXdzIl0sImF0aSI6IjcxZTc0ODg1LThlMzUtNGU4OS05YTc1LTc4NTI5ODFjOTdiZCIsImV4cCI6MTUzNDMxMTE2OCwiYXV0aG9yaXRpZXMiOlsibmV3c19yZWFkIl0sImp0aSI6ImVmNWJmN2VmLTdmZGYtNDczNi05ZjEzLWQ1Y2ZmMzY3OGUzOCIsImNsaWVudF9pZCI6Im5ld3MifQ.iRmW3X-U3AFbuk23waMTZXDYAw-sO_CUUWx-iCyn84i84Jjh5v8GHrwqVkVwdno4x76YP3UTIdS3O_6x4XjFQ3ZcCFmSiWu47qjgqjPEbDwCkJ8fatLHmJDu1Cytny9cs9P0_B5t5uO1kNj4u3UVqurmQGbRzi-Yso-JrAwb65AwwT7ayopSN7c5RTtkSuKYdtD3SGVhwBlSP_ZJa5C5Z1OxfKFJb3tK6ImP1uOi7QHbyic7wXf2-dxXuUsrZ_6gLbWyTd_5cYBD67sS0g4tDfg_9awID5AGQYR0SoVhDUDT-F6olaSqy9GmY5bqfRHKH8_qnhmlLn5L5QC43sArBA",
     "expires_in":3599,
     "scope":"news",
     "jti":"71e74885-8e35-4e89-9a75-7852981c97bd"
  }
```

Get Resources

``` console
$ curl -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzE3MjI3NjgsInVzZXJfbmFtZSI6InJlYWRlciIsImF1dGhvcml0aWVzIjpbIm5ld3NfcmVhZCJdLCJqdGkiOiI3MWU3NDg4NS04ZTM1LTRlODktOWE3NS03ODUyOTgxYzk3YmQiLCJjbGllbnRfaWQiOiJuZXdzIiwic2NvcGUiOlsibmV3cyJdfQ.Q7Fh0rYSQCCjSzQ9LSXg__o6tKCgFdB-KERHLbKIuUW55lwedoUPjSnUz7zehNMm0Ayjbe2HmdDh9n0_eJFjwCTxqe1wIUXIJV-Od7IDKR2bYSMZXAv7YZrS1OsBbarskuwXHPAW7Jrx2zS3Ym8VYj3ihOI7LahULcjNbNWVaxcMHofD6lyYeBkpC5b_AMiSFJymvo2fqonQjwkwpm-nqAwrSYeaifseA8zX9969VCkoScOGf9p6IdeqNlsvwh1u6rn_3VedH8ayEDK22Y4z88bBRtCwZ6vEEr7bOXIhPBHwojOdE0Jj40epBXAbx939vYE5rGAuf3gXFM4JCyE-jQ' localhost:7777/api/latest
```

### How Does it Work?

* Create database news_demo port
* mvn spring-boot:run


|Path                         | Description
| --------------------------- | ---------------------
| http://localhost:9999/auth  | Authorization Server
| http://localhost:7777/api   | Resource Server
| http://localhost:8080/      | UI
| http://localhost:8081/      | UI (Admin LTE) 


![image](https://github.com/ramustha/spring-adminlte-oauth2-sso/blob/master/ss/ss1.png?raw=true)
![image](https://github.com/ramustha/spring-adminlte-oauth2-sso/blob/master/ss/ss2.png?raw=true)
![image](https://github.com/ramustha/spring-adminlte-oauth2-sso/blob/master/ss/ss3.png?raw=true)
![image](https://github.com/ramustha/spring-adminlte-oauth2-sso/blob/master/ss/ss4.png?raw=true)
