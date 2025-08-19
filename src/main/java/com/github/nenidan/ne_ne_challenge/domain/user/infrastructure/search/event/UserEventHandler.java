package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.event;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.event.UserDeletedEvent;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.event.UserSavedEvent;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.document.UserDocument;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.mapper.UserMapper;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.repository.UserDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventHandler {

    private final UserDocumentRepository repository;

    @Async
    @EventListener
    public void handle(UserSavedEvent event) {
        UserDocument doc = UserMapper.toDocument(event.getUser());
        repository.save(doc);
    }

    @Async
    @EventListener
    public void handle(UserDeletedEvent event) {
        repository.deleteById(event.getId());
    }
}
