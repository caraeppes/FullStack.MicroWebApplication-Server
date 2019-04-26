package com.example.tcpApp.controllers;

import com.example.tcpApp.models.Channel;
import com.example.tcpApp.services.ChannelService;
import com.example.tcpApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    private Channel defaultChannel;

    @Autowired
    private UserService userService;

    private ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping("/default")
    public ResponseEntity<Channel> createDefaultChannel() {
        for (Channel channel: findAll().getBody()) {
            if (channel.getChannelName().equals("Main Channel")) {
                return new ResponseEntity<>(channel, HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(channelService.create(defaultChannel), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel) {
        return new ResponseEntity<>(channelService.create(channel), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Channel>> findAll(){
        return new ResponseEntity<>(channelService.findByIsPrivate(false), HttpStatus.OK);
    }

    @GetMapping("/private")
    public ResponseEntity<Iterable<Channel>> findAllPrivate(){
        return new ResponseEntity<>(channelService.findByIsPrivate(true), HttpStatus.OK);
    }

    @GetMapping("/private/{username}")
    public ResponseEntity<Iterable<Channel>> findAllPMsOfAUser(@PathVariable String username){
        return new ResponseEntity<>(channelService.findAllPMsOfAUser(userService.findByUsername(username)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Channel> findById(@PathVariable Long id){
        return new ResponseEntity<>(channelService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<Channel> findByName(@PathVariable String name){
        return new ResponseEntity<>(channelService.findByChannelName(name), HttpStatus.OK);
    }

    @PutMapping("/{id}/update/")
    public ResponseEntity<Channel> updateChannel(@PathVariable Long id, @RequestParam String channelName) {
        return new ResponseEntity<>(channelService.updateChannel(id, channelName), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return new ResponseEntity<>(channelService.delete(id), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Boolean> deleteAll(){
        return new ResponseEntity<>(channelService.deleteAll(), HttpStatus.NOT_FOUND);
    }
}


