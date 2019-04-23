package com.example.tcpApp.serviceTests;

import com.example.tcpApp.models.PrivateChannel;
import com.example.tcpApp.repositories.PrivateChannelRepository;
import com.example.tcpApp.services.PrivateChannelService;
import com.example.tcpApp.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrivateChannelServiceTests {

    @MockBean
    PrivateChannelRepository privateChannelRepository;

    @Autowired
    PrivateChannelService privateChannelService;

    @MockBean
    UserService userService;

    @Test
    public void testCreate() {
        // Given
        PrivateChannel expected = new PrivateChannel();
        expected.setId(5L);
        expected.setChannelName("test");
        when(privateChannelRepository.save(expected))
                .thenReturn(expected);

        // When
        PrivateChannel actual = privateChannelService.create(expected);

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testFindById() {
        // Given
        PrivateChannel expected = new PrivateChannel();
        expected.setId(1L);
        expected.setChannelName("test");
        when(privateChannelRepository.getOne(1L))
                .thenReturn(expected);
        // When
        PrivateChannel actual = privateChannelService.findById(1L);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFindByPrivateChannelName() {
        PrivateChannel expected = new PrivateChannel();
        expected.setId(1L);
        expected.setChannelName("test");
        when(privateChannelRepository.findByChannelName("test"))
                .thenReturn(expected);

        // When
        PrivateChannel actual = privateChannelService.findByPrivateChannelName("test");

        // Then
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void findAllTest() {
        // Given
        PrivateChannel privateChannel1 = new PrivateChannel();
        privateChannel1.setId(1L);
        PrivateChannel privateChannel2 = new PrivateChannel();
        privateChannel2.setId(2L);
        Iterable<PrivateChannel> expected = new ArrayList<>();
        ((ArrayList<PrivateChannel>) expected).add(privateChannel1);
        ((ArrayList<PrivateChannel>) expected).add(privateChannel2);
        when(privateChannelRepository.findAll())
                .thenReturn((List<PrivateChannel>) expected);

        // When
        Iterable<PrivateChannel> actual = privateChannelService.findAll();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteTest() {
        // Given
        PrivateChannel privateChannel = new PrivateChannel();
        privateChannel.setId(1L);
        when(privateChannelRepository.getOne(1L))
                .thenReturn(privateChannel);

        // When
        Boolean actual = privateChannelService.delete(1L);

        // Then
        verify(privateChannelRepository, times(1)).deleteById(1L);
        Assert.assertTrue(actual);
    }

}
