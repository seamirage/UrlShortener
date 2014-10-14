package services;

import idgeneration.IdGenerator;
import idgeneration.LongIdToStringConverter;
import storages.ShortUriRepository;

public class UriShorterServiceImpl implements UriShortenerService {
    @Override
    public String shortenAndStore(String originalUri, String userId) {
        long id = idGenerator.next();
        String shortUri = longToStringConverter.Convert(id);
        uriRepository.store(shortUri, originalUri, userId);

        return shortUri;
    }

    @Override
    public String getOriginalUri(String shortenedUri) {
        return uriRepository.getOriginalUri(shortenedUri);
    }

    private IdGenerator idGenerator;
    private LongIdToStringConverter longToStringConverter;
    private ShortUriRepository uriRepository;
}
