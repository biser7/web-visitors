package com.webvisitors.controller;

import com.webvisitors.service.VisitorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(VisitorController.class)
@ContextConfiguration(classes = {VisitorController.class})
public class VisitorControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    VisitorService visitorService;

    @Test
    public void whenCallGetVisitorsStatisticShouldResponseOk() throws Exception {
        this.mockMvc
                .perform(get("/report/visitors"))
                .andDo(print())
                .andExpect(model().attribute("visitors", hasSize(0)))
                .andExpect(view().name("report"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCallRunReadCsvJobShouldResponseOk() throws Exception {
        this.mockMvc
                .perform(get("/report/read-csv"))
                .andDo(print())
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCallRemoveAllRecordsShouldResponseOk() throws Exception {
        this.mockMvc
                .perform(delete("/report/remove-data"))
                .andDo(print())
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }
}
