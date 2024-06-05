### API Specification for User Registration
1. Send registration request with payload in json format to url:http://localhost:8080/api/auth/register
2. Payload contains information of User Entity for registration and the user and registers as multiples roles including 
    a normal user: ROLE_USER, an admin: ROLE_ADMIN and a moderator: ROLE_MODERATOR (listed in Enum class ERole)
3. A normal user can be further separated into three different types: default parent Entity User, subclass Entity Student
    or subclass Entity Teacher.
4. The type of the user need to be included in the request payload with attribute "type" and corresponding value 
5. The request body should be in the following format:

        {
           Section 1
            "type":"type_value"
            (
                "DEFAULT_USER" for Parent User Class,
                "STUDENT" for Student Subclass of User Class,
                "TEACHER" for Teacher Subclass of User Class
            )
   
            Section 2: attribute and value paris for default User Entity
            Note for "roles" attribute:
            "roles":{"roleName1":"roleName_value_1", "roleName2":"roleName_value_2", ...},
            specifies the roles for current registering user
            (
                null or empty for ROLE_USER,
                "admin" for ROLE_ADMIN,
                "mod" for ROLE_MODERATOR
            )
   
            Section 3:attribute and value paris for subclass Entity of User, not required for default User Entity
        }
   
7. The request will be processed and a verification email will be sent to the user's email address upon successful 
    registration and set the user's "active" attribute to 1(activated).
    (can manually sent to url:http://localhost:8080/api/auth/register/verifyRegistration with the user's generated token
    in DB for testing purpose)
8. If the user can not get the verification email, an email with the new token can be resent to the user by requesting
    the url:http://localhost:8080/api/auth/register/resendVerificationToken with the "oldToken" passed as a request 
    parameter
9. End of Registration flow of user registration

### API Specification for User Login
1. Send registration request with payload in json format to url:http://localhost:8080/api/auth/login
2. The payload should follow the LoginRequestModel Entity in format:

        {
            "email":"email_value",
            "oldPassword":"oldPassword_value"
        }
   
4. The response has the body follows the JwtResponseModel Entity which specifies the following attribute-value pairs:

        {
            id(user id)
            username(username)
            email(user email)
            roles(user's roles)
            type(type of user)
            token(user's Jwt token for authentication)
        }
6. User should specify the returned Jwt token in the Header of subsequent requests to access authorized resources
    For the Header field of request, set key to "Authorization"; set value to "Bearer JwtToken"
    (NOTE:A white space between Bearer and JwtToken)
7. End of Registration flow of user login
   
### API Specification of Role Based Authorization
1. The login and register resources have public access
2. All other resources have private access and are secured by the role of the requesting user
3. A user can only access his/her granted resources after login by adding Jwt Token in each request's Header field:
    User should specify the returned Jwt token in the Header of subsequent requests to access authorized resources
    For the Header field of request, set key to "Authorization"; set value to "Bearer JwtToken"
    (NOTE:A white space between Bearer and JwtToken)
4. The following Listed the Resources Each User Role have access to:
   
        {
            ROLE_ADMIN: All Resources
            ROLE_MODERATOR: Email Resources
            ROLE_USER: Email Resources
        }
   
6. End of  Role Based Authorization Specification
