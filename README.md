UrlShortener
============

REST-service that provides the URL shortening/expanding functionality like [bit.ly](https://bitly.com/).

#REST interface

##GET /shorten
Shortens specified URL.  
**Parameters**:   
*url*: URL-encoded long url  
**Example**: 
/shorten?url=https%3A%2F%2Fgoogle.ru%2Fsearch%3Fq%3Durl%2Bshortener  
**Return code**: 200 if OK, 50X on error

##GET /  
Expands shortened URL.  
**Path**: if not equals “/shorten” - will be treated as a short URL  
**Effect**: redirection to original URL  
**Return code**: 301, Location="original_url"

