# WildPark API
A RESTful API for WildPark built using Java Springboot

### JWT Enabled
All requests made to this API will require an `Authorization` header.
```json
{
  "Authorization": "Bearer <token>"
}
```

### Versioned
This API is designed with included versioning. All requests are to specify the API version in the URI.

**Available versions**:
- API v1: `/api/v1/**`

### Resources
#### **Users**
Fields: id, email, password, birthDate, firstname, lastname, contactNumber, gender, street, municipaity, province, country, zipCode
| Method | URI | Description |
| ------ | --- | ----------- |
| **GET** | `/api/v1/users` | Fetch all users. | 
| **POST** | `/api/v1/users` | Create new user. | 
| **GET** | `/api/v1/users/:id` | Fetch user with id specified by `:id`. |
| **PUT** | `/api/v1/users/:id` | Update user with id specified by `:id`. |
| **DELETE** | `/api/v1/users/:id` | Delete user with id specified by `:id`. |

### Public End-Points
These endpoints do not need to be authorized.
| Method | URI | Description |
| ------ | --- | ----------- |
| **POST** | `/api/v1/auth/register` | requires request body with `email` & `password` |
| **POST** | `/api/v1/auth/authenticate` | requires request body with `email` & `password`. |

### Dependencies
See `pom.xml` file for more details.
- Spring Web
- Spring JPA
- Spring OAuth2 Client
- MySQL Connector
- jjwt-api
- jjwt-impl
- jjwt-jackson
