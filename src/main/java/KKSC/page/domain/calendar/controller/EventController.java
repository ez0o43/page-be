package KKSC.page.domain.calendar.controller;

import KKSC.page.domain.calendar.repoAndService.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
}
