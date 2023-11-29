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

### Public End-Points
These endpoints do not need to be authorized.
| Method | URI | Description |
| ------ | --- | ----------- |
| **POST** | `/api/v1/auth/register` | Create an account. Requires request body with `email` & `password` |
| **POST** | `/api/v1/auth/authenticate` | Authenticate credentials. Requires request body with `firstname`, `lastname`, `email` & `password`. |

### Resources
#### **Accounts**
| Method | URI | Description |
| ------ | --- | ----------- |
| **GET** | `/api/v1/accounts/` | Fetch all users. |
| **GET** | `/api/v1/accounts/{accountId}` | Fetch user with id specified by `accountId`. |
| **PUT** | `/api/v1/accounts/{accountId}` | Update user with id specified by `accountId`. <br /><br />**Restrictions**: <br />Account Owner, Admin |
| **DELETE** | `/api/v1/accounts/{accountId}` | Delete user with id specified by `accountId`. <br /><br />**Restrictions**: <br />Account Owner, Admin |
| **GET** | `/api/v1/accounts/{accountId}/vehicles/` | Fetch vehicles owned by account with id specified by `accountId`. <br /><br />**Restrictions**: <br />Account Owner, Admin |
| **GET** | `/api/v1/accounts/{accountId}/organizations/` | Fetch organizations of account with id specified by `accountId`. <br /><br />**Restrictions**: <br />Account Owner, Admin |
| **GET** | `/api/v1/accounts/{accountId}/bookings/` | Fetch bookings made by account with id specified by `accountId`. <br /><br />**Restrictions**: <br />Account Owner, Admin |
| **GET** | `/api/v1/accounts/{accountId}/payments/` | Fetch payments made by account with id specified by `accountId`. <br /><br />**Restrictions**: <br />Account Owner, Admin |

#### **Bookings**
| Method | URI | Description |
| ------ | --- | ----------- |
| **POST** | `/api/v1/bookings/` | Add a new booking. |
| **GET** | `/api/v1/bookings/{bookingId}` | Fetch booking with id specified by `bookingId`. <br /><br />**Restrictions**: <br />Booking Owner, Admin |
| **PUT** | `/api/v1/bookings/{bookingId}` | Update booking with id specified by `bookingId`. <br /><br />**Restrictions**: <br />Booking Owner, Admin |
| **DELETE** | `/api/v1/bookings/{bookingId}` | Delete booking with id specified by `bookingId`. <br /><br />**Restrictions**: <br />Booking Owner, Admin |

#### **Organizations**
| Method | URI | Description |
| ------ | --- | ----------- |
| **GET** | `/api/v1/organizations/` | Fetch all organizations. |
| **POST** | `/api/v1/organizations/` | Create an organization. Requires request body with `name`, `latitude`, & `longitude`. |
| **GET** | `/api/v1/organizations/{organizationId}` | Fetch organization with id specified by `organizationId`. |
| **PUT** | `/api/v1/organizations/{organizationId}` | Update organization with id specified by `organizationId`. <br /><br />**Restrictions**: <br />Organization Owner, Admin |
| **DELETE** | `/api/v1/organizations/{organizationId}` | Delete organization with id specified by `organizationId`. <br /><br />**Restrictions**: <br />Organization Owner, Admin |

### Dependencies
See `pom.xml` file for more details.
- Spring Web
- Spring JPA
- Spring OAuth2 Client
- MySQL Connector
- jjwt-api
- jjwt-impl
- jjwt-jackson
