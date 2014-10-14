package services;

import idgeneration.IdGenerator;
import idgeneration.LongIdToStringConverter;
import storages.UserLinksRepository;

import java.sql.SQLException;

public class UriShorterServiceImpl implements UriShortenerService {
    @Override
    public String shortenAndStore(String originalUri, String userId) {
        long id = idGenerator.next();
        String shortUri = longToStringConverter.convert(id);

        return shortUri;
    }

    @Override
    public String getOriginalUri(String shortenedUri) throws SQLException {
        return uriRepository.getOriginalUri(shortenedUri);
    }

    private IdGenerator idGenerator;
    private LongIdToStringConverter longToStringConverter;
    private UserLinksRepository uriRepository;
}
