package com.example.tcpApp.controllers;

import com.example.tcpApp.models.PrivateChannel;
import com.example.tcpApp.models.User;
import com.example.tcpApp.services.PrivateChannelService;
import com.example.tcpApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/privatechannels")
public class PrivateChannelController {

    private PrivateChannelService privateChannelService;
    private UserService userService;

    @Autowired
    public PrivateChannelController(PrivateChannelService privateChannelService, UserService userService) {
        this.privateChannelService = privateChannelService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<PrivateChannel> createPrivateChannel(@RequestBody PrivateChannel channel) {
        return new ResponseEntity<>(privateChannelService.create(channel), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<PrivateChannel>> findAll(){
        return new ResponseEntity<>(privateChannelService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrivateChannel> findById(@PathVariable Long id){
        return new ResponseEntity<>(privateChannelService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/getByUser/{username}")
    public ResponseEntity<Iterable<PrivateChannel>> findAllByUser(@PathVariable String username) {
        return new ResponseEntity<>(privateChannelService.findAllByUser(userService.findByUsername(username)), HttpStatus.OK);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<PrivateChannel> findByName(@PathVariable String name){
        return new ResponseEntity<>(privateChannelService.findByPrivateChannelName(name), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return new ResponseEntity<>(privateChannelService.delete(id), HttpStatus.NOT_FOUND);
    }

}


