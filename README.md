# WunderList - Java Backend

### Documentation v1
Below is an overall view of the database tables. Note that ROLES and USERROLES are explicity used for authorization and should be ignored by the front end.

![Image of Database Layout](wunderlistmodel.png)

### Endpoints & Model Information

You can test endpoints straight from your browser by using the interactive app
[Here](https://wlist-java.herokuapp.com/swagger-ui.html)

### Authentication
A bearer token is required to access endpoints. A token can be acquired for NEW USERS by making a POST request at:
```http request
http://wlist-java.herokuapp.com/register
```
POST Request Shape:
```json
{
  "username": "myUsername",
  "password": "myPassword",
  "email": "myEmail@email.com"
}
```
On success, the generated response should look like this:
```json
{
    "access_token": "7cbc862b-b82d-4546-bde1-b9a58b21dc76",
    "token_type": "bearer",
    "scope": "read trust write"
}
```
You may now use the newly acquired token to access all endpoints that don't require an Admin status. To make and ADMIN account, simply include the word admin in your username:
```json
  "username": "admin_name",
  "password": "myPassword",
  "email": "myEmail@email.com"
```
#### For Existing Users
To log a user in and generate their token, you will have to make a POST request here:
```http request
http://wlist-java.herokuapp.com/login
```
This requires some headers on the request. An example is given below on how to handle this.
```javascript
axios.post("http://wlist-java.herokuapp.com/login", `grant_type=password&username=${this.state.username}&password=${this.state.password}` , {
    headers: {
        Authorization: `Basic ${btoa('lambda-client:lambda-secret')}`,
        "Content-Type": "application/x-www-form-urlencoded"
    }
})
    .then( res => {
        localStorage.setItem("token", res.data.access_token);
})
```
Everything should be left how it is except how you want to handle the response on .then, and the USERNAME and PASSWORD. This should be the same username and password when you created an account using the register endpoint.



