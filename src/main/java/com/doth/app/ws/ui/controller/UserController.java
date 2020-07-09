package com.doth.app.ws.ui.controller;

import com.doth.app.ws.service.AddressService;
import com.doth.app.ws.service.UserService;
import com.doth.app.ws.shared.dto.AddressDto;
import com.doth.app.ws.shared.dto.UserDto;
import com.doth.app.ws.ui.model.request.PasswordRequestRequestModel;
import com.doth.app.ws.ui.model.request.UserDetailRequestModel;
import com.doth.app.ws.ui.model.response.AddressesRest;
import com.doth.app.ws.ui.model.response.OperationStatusModel;
import com.doth.app.ws.ui.model.response.UserRest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @GetMapping(path = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE}
    )
    public UserRest getUser(@PathVariable String id){
        UserDto userDto = userService.getUserByUserId(id);
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE}
    )
    public List<UserRest> getUsers(
            @RequestParam (value = "page", defaultValue = "0")int page,
            @RequestParam(value = "limit", defaultValue = "25")int limit){

        if(page > 0) page-=1;
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> userDtos = userService.getUsers(page, limit);

        for(UserDto userDto: userDtos){
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(userDto, userRest);
            returnValue.add(userRest);
        }

        return returnValue;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public UserRest createUser(@RequestBody UserDetailRequestModel user){
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto createUser = userService.createUser(userDto);
        UserRest returnValue = modelMapper.map(createUser, UserRest.class);
        return returnValue;
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public UserRest updateUser(@RequestBody UserDetailRequestModel user, @PathVariable String id){
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(user, userDto);
        UserDto updateUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updateUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(
            path = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel deleteUser(@PathVariable String id){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping(path = "/{id}/addresses",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE, "application/hal+json"}
    )
    public CollectionModel<AddressesRest> getUserAddresses(@PathVariable String id){
       List<AddressesRest> returnValue = new ArrayList<>();
       List<AddressDto> addressesDto = addressService.getAddresses(id);

        if(addressesDto != null && !addressesDto.isEmpty()){
           Type listType = new TypeToken<List<AddressesRest>>(){}.getType();
           returnValue = new ModelMapper().map(addressesDto, listType);

           for(AddressesRest addressRest: returnValue){
               Link addressLink = linkTo(methodOn(UserController.class).getUserAddres(id, addressRest.getAddressId())).withSelfRel();
               Link userLink = linkTo(UserController.class).slash(id).withRel("user");
               addressRest.add(addressLink);
               addressRest.add(userLink);
           }
        }
        return new CollectionModel<>(returnValue);
    }

    @GetMapping(path = "/{userId}/addresses/{addressId}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE}
    )
    public EntityModel<AddressesRest> getUserAddres(@PathVariable String userId, @PathVariable String addressId){
        AddressDto addressesDto = addressService.getAddress(addressId);
        ModelMapper mapper = new ModelMapper();

        Link addressLink = linkTo(methodOn(UserController.class).getUserAddres(userId, addressId)).withSelfRel();
        Link addressesLink = linkTo(methodOn(UserController.class).getUserAddresses(userId)).withRel("addresses");
        Link userLink = linkTo(UserController.class).slash(userId).withRel("user");
        AddressesRest returnValue = mapper.map(addressesDto, AddressesRest.class);
        returnValue.add(addressLink);
        returnValue.add(addressesLink);
        returnValue.add(userLink);
        return new EntityModel<>(returnValue);
    }

    @PostMapping(
            path = "/request-paasword-reset",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel paaswordReset(@RequestBody PasswordRequestRequestModel passwordRequestRequestModel){
        OperationStatusModel returnValue = new OperationStatusModel();
        boolean operationResult = userService.requestPasswordReset(passwordRequestRequestModel.getEmail());
        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationName.ERROR.name());
        if(operationResult){
            returnValue.setOperationResult(RequestOperationName.SUCCESS.name());
        }
        return returnValue;
    }
}
