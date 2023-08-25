Spring Security with JWT authentication and authorization

-Additional Maven Dependence:
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.2</version>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId> <!-- or jjwt-gson if Gson is preferred -->
    <version>0.11.1</version>
    <scope>runtime</scope>
</dependency>

-Main Flow
1.User authentication: When a user logs in to the system, the server verifies the user’s credentials, such as their 
    username and password. If the credentials are correct, the server generates a JWT for the user and returns it to
    the client.

2.JWT creation: The server creates a JWT by encoding the user’s identity information and other necessary data, such as 
    expiration time, into a JSON object. The JSON object is then signed using a secret key or public/private key pair.

3.Token storage: The client stores the JWT locally, usually in a cookie or local storage.

4.Authorization: For each subsequent request, the client sends the JWT in the request header. The server verifies the 
    JWT’s signature and decodes its contents to extract the user’s identity information and other details. Based on 
    this information, the server can then authorize the user to access certain resources or perform certain actions.

5.Token validation: The server can also validate the JWT to ensure that it has not been tampered with and has not 
    expired. If the token is invalid, the server can reject the request or request the user to log in again.

-Implementation Flow
1.Create Enum Role class to defined user's roles

2.Create User Entity class
    Note: Better to implement UserDetails interface and override the methods
    a.Add Role class field in User Entity class to specify a user's role
    b.Override the getAuthority method which retrieve the role of user as a collection of SimpleAuthority objects
    c.(optional) Add other fields corresponds to the implemented UserDetails interface (e.g. isEnable, isNonExpired...)
    d.Override the methods corresponds to the implemented UserDetails interface to enable additional 
      security functionalities     

3.Create User Repository and add method to extract user by email/name

4.Create UserDetailsServiceImpl class which implements the UserDetailsService interface to retrieve user in DB.

5.Create SecurityConfig class with @Configuration and @EnableWebSecurity annotations which provides default security 
    a.configuration to our application.Default security activates both HTTP security filters and the security filter 
        chain and applies basic authentication to our endpoints.

6.Create JwtUtils class for generating, parsing and validating JWT token
    -Generating Token:
        a.create key from user-defined secret key
        b.retrieve username for setting the token's subject
        c.calculate the expiration time
        d.create token by setting claims,subject,issued and expiration date, signature.
    -Parsing Token:
        extract token form header and exclude the prefix "Bearer "
    -Validating Token:
        parse the token with set Signing Key
        catch specific exceptions:
                                MalformedJwtException
                                ExpiredJwtException
                                UnsupportedJwtException
                                IllegalArgumentException

3.Create JwtAuthenticationFilter class which extends OncePerRequestFilter to validate JWT
    -Override doFilterInternal method:
        a.extract authorization token from header.
        b.validate the token by extracting username and expiration date from token
        c.load the user by extracted username(may throw exception and handled by security)
        d.on successful user retrieval, create new UsernamePasswordAuthenticationToken object 
            with the user's details, credentials and authorities
        e.set the UsernamePasswordAuthenticationToken object's details with new WebAuthenticationDetailsSource object
        f.set the UsernamePasswordAuthenticationToken object in SecurityContextHolder's context
        g.continue to execute other filters

4.Create JwtAccessDeniedHandler class which implements AccessDeniedHandler that handles exceptions from authenticated 
    request but do not have enough authority. 
    -Note: can throw customized exception instead of the UsernameNotFoundException which would masked 
            by AuthenticationException

5.Create JwtAuthenticationEntryPoint class which implements AuthenticationEntryPoint that handles exceptions from 
    unauthenticated request

6.Add Configuration Beans and inject entities required for Spring Security Configuration in SecurityConfig class. 
    a.UserDetailsServiceImpl (injection)
    b.PasswordEncoder(Bean)
    c.Authentication Provider(Bean) (DaoAuthenticationProvider with UserDetailService and PasswordEncoder set)
    d.Authentication Manager(Bean)
    e.JwtAuthenticationEntryPoint(injection)
    f.JwtAccessDenied(injection)

7.Create AuthController class and add endpoints into it for registration and login activity.
    a.Use @PreAuthorize("hasRole('ROLE_XXX') or hasRole('ROLE_XXX')") annotation for role-based authorization
    -Login:
        a.Create new UsernamePasswordAuthenticationToken object with login info in request
        b.Create new AuthenticationManager object and user the authenticate() method for authenticating the user
          with the created UsernamePasswordAuthenticationToken object

8.Create models for RegisterRequest, AuthenticationRequest and AuthenticationResponse

API Endpoints
Public for user authentication, base url: localhost:8080/api/auth
1. localhost:8080/api/auth/register
Method: POST

Request Body:
@NotBlank
String firstName;
@NotBlank
String lastName;
@NotBlank
String password;
@Email
String email;
@NotBlank
String role; ("user" or "admin)

Response: Status Code - 201
Response Body:
"jwtToken": String
"type": "Bearer",
"refreshToken": String,
"userId": Long,
"username": String,
"email": String,
"roles": String array ("ROLE_USER" or "ROLE_ADMIN)

2. localhost:8080/api/auth/login
Method: POST

Request Body:  
String userNameEmail; (format: "firstName"(one_empty_space)"lastName":"password")

Response: Status Code - 200
Response Body:
"jwtToken": String
"type": "Bearer",
"refreshToken": String,
"userId": Long,
"username": String,
"email": String,
"roles": String array ("ROLE_USER" or "ROLE_ADMIN)


3. localhost:8080/api/auth/refreshtoken
Method: POST
Request Body:
@NotBlank
String refreshToken;

Response: Status Code - 200
Response Body:
"jwtToken": String
"type": "Bearer",
"refreshToken": String,
"userId": Long,
"username": String,
"email": String,
"roles": String array ("ROLE_USER" or "ROLE_ADMIN)

Role Based Authentication
Response Code - 401 Unauthorized (unverified user did not send request with authorization header or bearer token)
Response Code - 403 Forbidden (Role criteria not satisfied, access denied, verified user send request with access token)
