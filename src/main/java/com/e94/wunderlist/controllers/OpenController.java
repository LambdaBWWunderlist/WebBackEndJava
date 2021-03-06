package com.e94.wunderlist.controllers;

import com.e94.wunderlist.models.User;
import com.e94.wunderlist.models.UserMinimum;
import com.e94.wunderlist.models.UserRoles;
import com.e94.wunderlist.services.RoleService;
import com.e94.wunderlist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OpenController {

    @Autowired
    private UserService userService;

    /**
     * A method in this controller adds a new user to the application with the role User so needs access to Role Services to do this.
     */
    @Autowired
    private RoleService roleService;

    /**
     * This endpoint always anyone to create an account with the default role of USER. That role is hardcoded in this method.
     *
     * @param httpServletRequest the request that comes in for creating the new user
     * @param newminuser         A special minimum set of data that is needed to create a new user
     * @return The token access and other relevent data to token access. Status of CREATED. The location header to look up the new user.
     * @throws URISyntaxException we create some URIs during this method. If anything goes wrong with that creation, an exception is thrown.
     */

    @PostMapping(value = "/register", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addSelf(HttpServletRequest httpServletRequest, @Valid @RequestBody UserMinimum newminuser) throws URISyntaxException
    {
        // Create the user
        User newuser = new User();

        newuser.setUsername(newminuser.getUsername());
        newuser.setPassword(newminuser.getPassword());
        newuser.setEmail(newminuser.getEmail());

        if (newminuser.getUsername().toLowerCase().contains("admin")){
            List<UserRoles> newRoles = new ArrayList<>();
            newRoles.add(new UserRoles(newuser, roleService.findByName("admin")));
            newuser.setRoles(newRoles);
        } else {
            List<UserRoles> newRoles = new ArrayList<>();
            newRoles.add(new UserRoles(newuser, roleService.findByName("user")));
            newuser.setRoles(newRoles);
        }


        newuser = userService.save(newuser);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/users/user/{userId}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);


        RestTemplate restTemplate = new RestTemplate();
        String requestURI = "http://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/login"; // LOCAL
//        String requestURI = "http://" + httpServletRequest.getServerName() + "/login"; // DEPLOY

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(acceptableMediaTypes);
        headers.setBasicAuth(System.getenv("OAUTHCLIENTID"),
                System.getenv("OAUTHCLIENTSECRET"));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type",
                "password");
        map.add("scope",
                "read write trust");
        map.add("username",
                newminuser.getUsername());
        map.add("password",
                newminuser.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String theToken = restTemplate.postForObject(requestURI, request, String.class);

        return new ResponseEntity<>(theToken, responseHeaders, HttpStatus.CREATED);
    }


    @ApiIgnore
    @GetMapping("favicon.ico")
    public void returnNoFavicon() {
    }

}
