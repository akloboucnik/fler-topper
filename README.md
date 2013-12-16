# fler-topper

Little worker for my wife to top goods on fler.cz (czech etsy.com variant)

## Usage

## Notes

* login
    * POST /uzivatel/prihlaseni, {username: "", pwd: "", btnLogin: ""}
    * save all cookies
* parse all products
    * GET /moje-zbozi -> parse `_sid`, `_uid`, `_checksum` and `_dummy` (`_dummy` is `Date.getTime()`)
    * POST /moje-zbozi?route=itemlist, {:page: <page_no> :type "visible"}
        * all IDs from .productlist .productitem[data-id]
        * from result parse the number of pages
            * `#itemsGrid > center:nth-child(3) table td div:last-child center` content
* top
    * choose one ID pseudo randomly (where to save state?)
    * issue GET to /moje-zbozi?id=<chosen_id>&a=top

## License

Copyright © 2013

Distributed under the MIT License.

