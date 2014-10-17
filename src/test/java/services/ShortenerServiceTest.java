package services;


import idgeneration.IdGenerator;
import idgeneration.LongIdToStringConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import storages.DatabaseException;
import storages.dto.UserLink;
import storages.repositories.UserLinksRepository;

import static org.mockito.Mockito.*;

public class ShortenerServiceTest {

    @Before
    public void setUp() {
        generatorMock = mock(IdGenerator.class);
        converterMock = mock(LongIdToStringConverter.class);
        repositoryMock = mock(UserLinksRepository.class);
    }

    @Test
    public void storeNewLinkTest() throws DatabaseException {
        when(generatorMock.next()).thenReturn(1L);
        when(converterMock.convert(1L)).thenReturn(linkId);

        ShortenerService shorener = new ShortenerService(generatorMock, converterMock, repositoryMock, prefix);
        shorener.shortenAndStore(originalUri, "1");

        verify(converterMock).convert(Matchers.eq(1L));
        verify(repositoryMock).add(Matchers.argThat(new UserLinkMatches(originalUri, linkId, "1")));
    }

    class UserLinkMatches extends ArgumentMatcher<UserLink> {
        UserLinkMatches(String expectedOriginalUri, String expectedLinkId, String expectedUserId) {
            this.expectedOriginalUri = expectedOriginalUri;
            this.expectedLinkId = expectedLinkId;
            this.expectedUserId = expectedUserId;
        }

        @Override
        public boolean matches(Object o) {
            UserLink addedLink = (UserLink)o;
            return addedLink.getOriginalUri() == expectedOriginalUri &&
                    addedLink.getShortLinkId() == expectedLinkId &&
                    addedLink.getUserId() == expectedUserId;
        }

        private String expectedOriginalUri;
        private String expectedLinkId;
        private String expectedUserId;
    }

    private static final String originalUri = "https%3A%2F%2Fgoogle.ru%2Fsearch";
    private static final String linkId = "LINK_ID";
    private static final String prefix = "http://shorturl.com/";
    private IdGenerator generatorMock;
    private LongIdToStringConverter converterMock;
    private UserLinksRepository repositoryMock;
}
