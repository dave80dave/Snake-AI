package at.orter.snakebackend;

import at.orter.snakebackend.controller.StatusController;
import at.orter.snakebackend.response.StatusResponse;
import at.orter.snakebackend.service.StatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(StatusController.class)
public class StatusControllerTest {
    @Autowired
    private MockMvc mvcMock;

    @MockitoBean
    private StatusService statusService;

    @Test
    void statusReturnsMessage() throws Exception {
        when(statusService.getStatus())
                .thenReturn(new StatusResponse("Snake ist Started on Backend!",3));

        mvcMock.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Snake ist Started on Backend!"))
                .andExpect(jsonPath("$.availableActions")
                        .value(3));
    }
}
