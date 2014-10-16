package services;

import idgeneration.IdGenerator;
import idgeneration.LongIdToStringConverter;
import storages.dto.UserLink;
import storages.repositories.UserLinksRepository;

import java.sql.SQLException;

public class ShortenerService {

    public ShortenerService(IdGenerator idGenerator, LongIdToStringConverter longToStringConverter, UserLinksRepository uriRepository, String prefix) {
        this.idGenerator = idGenerator;
        this.longToStringConverter = longToStringConverter;
        this.uriRepository = uriRepository;
        this.prefix = prefix;
    }

    public String shortenAndStore(String originalUri, String userId) throws SQLException {
        long id = idGenerator.next();
        String shortLinkId = longToStringConverter.convert(id);
        uriRepository.add(new UserLink(originalUri, shortLinkId, userId));

        return prefix + shortLinkId;
    }

    private IdGenerator idGenerator;
    private LongIdToStringConverter longToStringConverter;
    private UserLinksRepository uriRepository;
    private final String prefix;
}
