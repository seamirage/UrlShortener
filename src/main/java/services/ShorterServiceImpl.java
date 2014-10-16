package services;

import idgeneration.IdGenerator;
import idgeneration.LongIdToStringConverter;
import storages.repositories.UserLinksRepository;

public class ShorterServiceImpl {

    public String shortenAndStore(String originalUri, String userId) {
        long id = idGenerator.next();
        String shortUri = longToStringConverter.convert(id);

        return shortUri;
    }

    private IdGenerator idGenerator;
    private LongIdToStringConverter longToStringConverter;
    private UserLinksRepository uriRepository;
}
