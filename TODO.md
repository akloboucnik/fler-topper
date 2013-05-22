# TODO

## Steps

- find something like nokogiri
    - enlive-html? cgrand/enlive + swannodette/enlive-tutorial
- login
    - get cookies by fetching fler.cz - apparently, without JS ve just get fler_guest_data= cookie
    curl "http://www.fler.cz/uzivatel/prihlaseni"
        -H "Pragma: no-cache"
        -H "Origin: http://www.fler.cz"
        -H "Accept-Encoding: gzip,deflate,sdch"
        -H "Host: www.fler.cz"
        -H "Accept-Language: en,cs;q=0.8"
        -H "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31"
        -H "Content-Type: application/x-www-form-urlencoded"
        -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
        -H "Cache-Control: no-cache"
        -H "Referer: http://www.fler.cz/"
        -H "Cookie: SESS7fc0bf9bc4ecc290cc0c3b53b5e5b181=6nuqcvvg46ulf063tnbgd43bg6; fler_guest_data=%7B%22u2%22%3A%22CZ%22%2C%22u1%22%3A%22CZK%22%7D; guest_ident=1369288038519846280fe1634b860921ec9e5f70; __utma=146309475.1935694031.1369288038.1369288038.1369288038.1; __utmb=146309475.1.10.1369288038; __utmc=146309475; __utmz=146309475.1369288038.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=146309475.|5=Layout=3=1"
        -H "Connection: keep-alive"
        -H "Accept-Charset: UTF-8,*;q=0.5"
        --data "username=a.kloboucnik&pwd=******&btnLogin=&_redir_after_login=http%3A%2F%2Fwww.fler.cz%2F"

- parse web
- choose random item
- top it
- send a mail
- logout
- ad some pseudorandomness - persistence of last choices

